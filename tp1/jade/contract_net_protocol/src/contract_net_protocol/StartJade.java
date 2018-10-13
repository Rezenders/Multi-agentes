package contract_net_protocol;

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
    
    void createAgents() throws Exception {
    	Vector<String> participant_names = new Vector<String>();
    	
    	String initiator_name = "initiator";
    	
        String item_name = "book";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
        }
    	
    	item_name = "car";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
        }
    	
    	item_name = "bike";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
        }
    	
    	item_name = "paper";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
        }
    	
    	item_name = "mustard";
    	for (int i=1; i<11; i++) {
    		String participant_name = "participant_"+item_name;
            AgentController ac = cc.createNewAgent(participant_name+i, "contract_net_protocol.participantAgent",  new Object[] { item_name });
            participant_names.add(participant_name+i);
            ac.start();
        }
        
        
        
        for (int i=1; i<101; i++) {        
            AgentController ac = cc.createNewAgent(initiator_name+i, "contract_net_protocol.initiatorAgent", participant_names.toArray());
            ac.start();
        }
        
    }
}
