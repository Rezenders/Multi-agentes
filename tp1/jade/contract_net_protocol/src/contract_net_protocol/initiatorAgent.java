package contract_net_protocol;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetInitiator;

public class initiatorAgent extends Agent {
	
	Map<String,Vector<Vector<String>>> proposals = new HashMap<String,Vector<Vector<String>>>();
	Vector<String> services = new Vector<String>();
	
	protected void setup(){
		services.add("book");
		services.add("bike");
		
        System.out.println("Starting initiator agent called " + getLocalName());
        
        Object[] args = getArguments();
      	if (args != null && args.length > 0) {
      		int nResponders = args.length;
      		System.out.println("Initiator "+ getLocalName() +" establishing contract net protocol with " +nResponders + " participants");
      		
      		Iterator it = services.iterator();
      		while(it.hasNext()) {
	      		String service = (String) it.next();
      			// Fill the CFP message
	      		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
	      		for (int i = 0; i < args.length; ++i) {
	      			msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
	      		}
	      		
	      		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
	      		msg.setReplyByDate(new Date(System.currentTimeMillis() + 2000));
	      		
	      		Vector<String> cnp = new Vector<String>();
	      		msg.setContent(service);
	      		
	      		System.out.println("[" + getLocalName()+"] Requesting "+msg.getContent());
      		
      		
      		addBehaviour(new ContractNetInitiator(this, msg) {
      			
      			protected void handlePropose(ACLMessage propose, Vector v) {
      				
      				Vector<String> content = new Vector<String>();
      				try {
						content = (Vector<String>) propose.getContentObject();
						String key = content.get(0);
						
						Vector<String> values = new Vector<String>();
						values.add(content.get(1));				
						values.add(String.valueOf(propose.getSender().getLocalName()));
												
						Vector<Vector<String>> aux = new Vector<Vector<String>>();
						if(proposals.get(key)==null) {
							aux.add(values);
						}else {
							aux = proposals.get(key);
							aux.add(values);
						}
											
						proposals.put(key, aux);						
						
					} catch (UnreadableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
      				
					System.out.println("Agent "+propose.getSender().getName()+" proposed "+content.get(0) + " for " + content.get(1));
				}
      			
      			protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Agent "+refuse.getSender().getName()+" refused");
				}
      			
      			protected void handleAllResponses(Vector responses, Vector acceptances) {
      				// Evaluate proposals.
					double bestProposal = 100001;
					AID bestProposer = null;
					ACLMessage accept = null;
					
					Enumeration e = responses.elements();
					while (e.hasMoreElements()) {
						ACLMessage msg = (ACLMessage) e.nextElement();
						if (msg.getPerformative() == ACLMessage.PROPOSE) {
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
							acceptances.addElement(reply);
							
							Vector<String> content = new Vector<String>();
							try {
								content = (Vector<String>) msg.getContentObject();
							} catch (UnreadableException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					
							double proposal = Double.valueOf(content.get(1));
							if (proposal < bestProposal) {
								bestProposal = proposal;
								bestProposer = msg.getSender();
								accept = reply;
							}
						}
					}
					// Accept the proposal of the best proposer
					if (accept != null) {
						System.out.println("Accepting proposal "+bestProposal+" from responder "+bestProposer.getLocalName());
						accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					}

				}

				
      		});
      		}
      		
      	}
        
    }
	
	class contract_net_protocol extends SimpleBehaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return true;
		}

	}
}





