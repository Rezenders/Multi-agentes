package contract_net_protocol;
import org.apache.log4j.Logger;


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
import log.Log4j;


import java.io.IOException;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetResponder;

import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;

public class participantAgent extends Agent{
	
	private static final Logger log = Logger.getLogger(participantAgent.class);

	
	Map<String,Integer> items = new HashMap<String,Integer>();
	
	
	public void setup() {
		Object[] args = getArguments();
      	if (args != null && args.length > 0) {
      		for (int i = 0; i < args.length; ++i) {
      			items.put((String) args[i], 50);
      		}
      		
		System.out.println("Agent "+getLocalName()+" waiting for CFP...");
		log.debug("Agent "+getLocalName()+" waiting for CFP...");
		
		MessageTemplate msg = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP) );
		
		addBehaviour(new ContractNetResponder(this, msg) {
			
			@Override
			protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
//				System.out.println("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getLocalName()+". Service is "+cfp.getContent());
				log.debug("Agent "+getLocalName()+": CFP received from "+cfp.getSender().getLocalName()+". Service is "+cfp.getContent());

				
				if(items.get(cfp.getContent())!=null && items.get(cfp.getContent())>0) {
					
					
					Vector<String> content = new Vector<String>();
					content.add(cfp.getContent());
					content.add(String.valueOf(Math.random()*100));
					
					ACLMessage propose = cfp.createReply();
					propose.setPerformative(ACLMessage.PROPOSE);
					
					try {
						propose.setContentObject(content);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return propose;					
				}else {
					System.out.println("["+getLocalName()+"] Refused request for " + cfp.getContent());
					log.debug("["+getLocalName()+"] Refused request for " + cfp.getContent());

					throw new RefuseException("evaluation-failed");					
				}	
				
			}
			
			@Override
			protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
								
				Vector<String> content = new Vector<String>();
  				try {
					content = (Vector<String>) propose.getContentObject();
										
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				double price = Double.valueOf(content.get(1));
				String item_name = content.get(0);
				int item_qt = items.get(item_name);
				
				if (item_qt > 0) {
					items.put(item_name, item_qt -1);
					System.out.println("["+getLocalName() +"] When negotiating with "+cfp.getSender().getLocalName()+" I sold "+ item_name+ " for "+price);
					log.debug("["+getLocalName() +"] When negotiating with "+cfp.getSender().getLocalName()+" I sold "+ item_name+ " for "+price);
					ACLMessage inform = accept.createReply();
					inform.setPerformative(ACLMessage.INFORM);
					return inform;
				}
				else {
					System.out.println("Agent "+getLocalName()+": Action execution failed");
					log.debug("Agent "+getLocalName()+": Action execution failed");
					throw new FailureException("unexpected-error");
				}	
			}
			
		});
		
	}
	}

}
