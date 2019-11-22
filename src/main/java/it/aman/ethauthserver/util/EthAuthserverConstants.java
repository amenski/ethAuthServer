package it.aman.ethauthserver.util;

/**
 * @author prg
 *
 */
public class EthAuthserverConstants {

//	public static final String 

	public enum LogConstants {
		PARAMETER_2("{} {}"),
		METHOD_START("executing method: "),
		METHOD_END("finished execution of: ");

		private String messageText;

		private LogConstants(String msg) {
			messageText = msg;
		}
		
		public String getMessageText(){
			return messageText;
		}
		
	}
	
	public static String getMethodStart(){
		return String.join(",", LogConstants.PARAMETER_2.getMessageText(), LogConstants.METHOD_START.getMessageText());
	}
	
	public static String getMethodEnd(){
		return String.join(",", LogConstants.PARAMETER_2.getMessageText(), LogConstants.METHOD_END.getMessageText());
	}
	
	private EthAuthserverConstants() {
		throw new IllegalStateException("Utility class.");
	}
}
