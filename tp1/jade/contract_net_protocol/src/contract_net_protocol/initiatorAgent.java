package contract_net_protocol;

import jade.core.Agent;

public class initiatorAgent extends Agent {

	protected void setup() 
    { 
        System.out.println("Hello World. ");
        System.out.println("My name is "+ getLocalName()); 
    }

}
