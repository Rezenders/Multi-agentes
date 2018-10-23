package jauction_env;

import jason.asSyntax.Atom;
import cartago.*;
import java.util.Vector;

public class JAuctionArtifact extends Artifact{

	Vector<String> participants = new Vector<String>();

	public void init(){
		defineObsProperty("running", "no");
	}

	@OPERATION public void start(String item, int price){
		if(getObsProperty("running").stringValue().equals("yes")){
			failed("This auction is already running, can't start again!");
		}

		defineObsProperty("item", item);
		defineObsProperty("price", price);
		getObsProperty("running").updateValue("yes");
	}

	@OPERATION public void add_participant(String name){
		if(!participants.contains(name)){
			participants.add(name);
		}
	}

	@OPERATION public void remove_participant(String name){
		if(participants.contains(name)){
			participants.remove(name);
		}
	}

	@OPERATION public void check_room(){
		if(participants.size()==1){
			defineObsProperty("winner",participants.get(0));
		} else if (participants.size()==0){
			defineObsProperty("draw");
		}
	}

	@OPERATION public void update_price(){
		int price = getObsProperty("price").intValue();
		getObsProperty("price").updateValue(price+1);
	}
}
