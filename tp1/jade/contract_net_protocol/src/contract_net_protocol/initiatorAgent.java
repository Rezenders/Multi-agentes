package contract_net_protocol;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetInitiator;

public class initiatorAgent extends Agent {
	
	Map<String,Vector<Vector<String>>> proposals = new HashMap<String,Vector<Vector<String>>>();
	Vector<String> services = new Vector<String>();
	
	protected void setup(){
		services.add("book");
        System.out.println("Starting initiator agent called " + getLocalName());
        
        Object[] args = getArguments();
      	if (args != null && args.length > 0) {
      		int nResponders = args.length;
      		System.out.println("Initiator "+ getLocalName() +" establishing contract net protocol with " +nResponders + " participants");
      		
      		// Fill the CFP message
      		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
      		for (int i = 0; i < args.length; ++i) {
      			msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
      		}
      		
      		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
      		msg.setReplyByDate(new Date(System.currentTimeMillis() + 2000));
      		
      		Vector<String> cnp = new Vector<String>();
      		msg.setContent(services.get(0));
      		
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
					
      				Iterator it = services.iterator();
      				while(it.hasNext()) {
      					Vector<Vector<String>> service_proposals = new Vector<Vector<String>>();
      					service_proposals = proposals.get(it.next()); //PROVAVELMENTE VAI PRECISAR DE UM if!=null dps
      					
      					Iterator it2 = service_proposals.iterator();
      					double best_proposal = 100001;
      					String best_proposer = "";
      					Vector<String> refused_proposers = new Vector<String>();
      					while(it2.hasNext()) {
      						Vector<String> proposal = new Vector<String>();
      						proposal = (Vector<String>) it2.next();
      						if(Double.valueOf(proposal.get(0)) < best_proposal) {
      							best_proposal = Double.valueOf(proposal.get(0));
      							best_proposer = proposal.get(1);
      						}else {
      							refused_proposers.add(proposal.get(1));
      						}
      						
      					}
      					System.out.println(best_proposal);
      					System.out.println(best_proposer);
      					
      					ACLMessage msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
      					msg.addReceiver(new AID((String) best_proposer, AID.ISLOCALNAME));
      					msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
      					msg.setReplyByDate(new Date(System.currentTimeMillis() + 2000));
      					msg.setContent("oi");
      					send(msg);
      					
      					
      				}

				}

				
      		});
      		
      	}
        
    }
	
	
}


