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

import java.text.SimpleDateFormat;
import java.util.Vector;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 *  Starts JADE main container with several agents
 * 
 *  @author jomi
 */

public class StartJade {

	private static final Logger log = Logger.getLogger(StartJade.class);

    ContainerController cc;
    
    public static void main(String[] args) throws Exception {
        StartJade s = new StartJade();
        s.startContainer();
        s.createAgents();         
    }

    void startContainer() {
        //Runtime rt= Runtime.instance();
        ProfileImpl p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true");
        
        cc = Runtime.instance().createMainContainer(p);
    }
    //  gerando log
    static{
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        System.setProperty("current.date.time", dateFormat.format(new Date(0)));
        
        
    }
    
    void createAgents() throws Exception {
    	Vector<String> participant_names = new Vector<String>();
    	
    	String initiator_name = "initiator";
    	
        String item_name = "book";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
            log.debug("Foram criados os seguintes Agentes Initiator Books"+participant_names);

        }
    	
    	item_name = "car";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
            log.debug("Foram criados os seguintes Agentes Initiator car"+participant_names);
        }
    	
    	item_name = "bike";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
            log.debug("Foram criados os seguintes Agentes Initiator bike"+participant_names);
        }
    	
    	item_name = "paper";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
            log.debug("Foram criados os seguintes Agentes Initiator paper"+participant_names);
        }
    	
    	item_name = "mustard";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
            log.debug("Foram criados os seguintes Agentes Initiator mustard"+participant_names);
        }
        
        
        
        for (int i=1; i<101; i++) {        
            AgentController ac = cc.createNewAgent(initiator_name+i, "contract_net_protocol.initiatorAgent", participant_names.toArray());
            ac.start();
            log.debug("Foram criados os seguintes Agentes Initiator initiator"+participant_names);
        }
		
        
    }
}
