package robot.utils;

import java.util.ArrayList;

public class PrepareCommands {
	/**
	 * Takes a string and converts it into a list of commands and timeframes.<br>
	 * "||" is used as delimiter. ";;" is used to recognize the command from the timeframe.<br><br>
	 * 
	 * The string format must be:<br>
	 * "||cmd;;timeframe||cmd;;timeframe||etc...||"<br>
	 * The string must start with "||" and end with "||"<br><br>
	 * 
	 * The returned array is given as a command in the even index numbers<br>
	 * with the timeframe for the command in the following odd index number.<br>
	 * E.g.:
	 * 
	 * <ul>
	 * <li>[0] -> "C_FW", [1] -> "5000"</li>
	 * <li>[2] -> "C_HL", [3] -> "0"</li>
	 * </ul>
	 * 
	 * @param msg String containing the commands and their timeframes.
	 * @return 
	 * @return An array containing the command and its timeframe in the following array index.
	 */
	public static String[] prepCmds(String msg) {
		ArrayList<String> res = new ArrayList<String>();
		
		int start = 0;
		
		while(start < msg.lastIndexOf("||")) {
			int i = msg.indexOf(";;", start);
			int j = msg.indexOf("||", i);
			
			if(i != -1) {
				res.add(msg.substring((start+2), i));
				res.add(msg.substring((i+2), j));
				
				start = msg.indexOf("||", i);
			}
		}
		
		return res.toArray(new String[res.size()]);
	}

}
