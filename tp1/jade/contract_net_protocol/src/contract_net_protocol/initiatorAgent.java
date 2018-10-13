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
	
	Vector<String> services = new Vector<String>();
	
	protected void setup(){
		services.add("book");
		services.add("bike");
		services.add("car");
		services.add("paper");
		services.add("mustard");
		
        System.out.println("Starting initiator agent called " + getLocalName());
        
        Object[] args = getArguments();
      	if (args != null && args.length > 0) {
      		int nResponders = args.length;
//      		System.out.println("Initiator "+ getLocalName() +" establishing contract net protocol with " +nResponders + " participants");
      		
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
//					System.out.println("Agent "+propose.getSender().getLocalName()+" proposed "+content.get(0) + " for " + content.get(1));
				}
      			
      			protected void handleRefuse(ACLMessage refuse) {
//					System.out.println("Agent "+refuse.getSender().getName()+" refused");
				}
      			
      			protected void handleAllResponses(Vector responses, Vector acceptances) {
      				// Evaluate proposals.
					double bestProposal = 100001;
					AID bestProposer = null;
					ACLMessage accept = null;
					
					Enumeration e = responses.elements();
					String item_name = "";
					while (e.hasMoreElements()) {
						ACLMessage msg = (ACLMessage) e.nextElement();
						if (msg.getPerformative() == ACLMessage.PROPOSE) {
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
							acceptances.addElement(reply);
							
							Vector<String> content = new Vector<String>();
							try {
								content = (Vector<String>) msg.getContentObject();
								item_name = content.get(0);
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
						System.out.println("["+getLocalName()+"] Accepting proposal of "+item_name+" for " +bestProposal+" from responder "+bestProposer.getLocalName());
						accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					}

				}

				
      		});
      		}
      		
      	}
        
    }
	
}





