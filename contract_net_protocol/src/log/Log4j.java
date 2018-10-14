package log;
//import jade.util.Logger;
import org.apache.log4j.Logger;

public class Log4j {
	
	// create a logger object
	
	private static final Logger log = Logger.getLogger(Log4j.class);
	
	public static void main(String [] args){
		
	//	log.debug("Test Message");
		
		try {
			throw new Exception("New exception");
		} catch (Exception e){
			log.error(e,e);
		}
		
	}
	

}
