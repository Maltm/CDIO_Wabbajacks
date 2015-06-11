package robot;

import java.util.ArrayList;

import lejos.nxt.LCD;
import lejos.util.Delay;

/*
 * Controls the robot overall - movement by command input, sensors etc.
 * 
 * @author Kristin Hansen
 */

public class RControl {
	public RControl() {
		/* TODO
		 * This is the controller which the RConn parses commands to.
		 * 
		 * Should have some kind of switch-case.
		 * 
		 * Different commands could execute different moves.
		 * E.g.
		 * "CFW" received: COMMAND FORWARD		perform: Movement.forward
		 * "CBW" received: COMMAND BACKWARDS	perform: Movement.backward
		 * "CLF" received: COMMAND LEFT			perform: Movement.left
		 * etc. etc.
		 * 
		 * A message could consist of several commands with timeframes:
		 * "||C_FW;;2500||C_BW;;450||C_BW;;1000||C_LF;;400||C_RG;;3400||C_FW;;2150" could be made into an array by splitting
		 * 0 -> C_FW
		 * 1 -> C_BW
		 * 2 -> C_BW
		 * 3...
		 * 4...
		 * etc. etc.
		 * 
		 * The RobotControl should also:
		 *  a:	have contact with the us-sensor, and halt any and all commands currently being executed, if
		 *   i:		sensor's values become greater than 1 ("DANGER CLOSE")
		 *   ii:	sensor is unresponsive ("ERROR")
		 *  b:	be able to return a "COMPLETE"-, "INCOMPLETE<msg>"-, "DNGCLOSE"- OR "ERROR"-command
		 *   i:		"COMPLETE" if everything has been executed without any errors
		 *   ii:	"INCOMPLETE<msg>" if some commands couldn't be executed (msg = "||1st_cmd_not_exe||2nd_cmd_not_exe||etc.")
		 *   iii:	"DNGCLOSE" if US-sensor reacts to "danger close" walls
		 *   iiii:	"ERROR<msg>" if something went wrong, but couldn't be (msg = some error code or something alike)
		 *   
		 */
	}
	
	/**
	 * Takes a set of commands and executes them.<br><br>
	 * 
	 * The method will return whether or not the list of commands<br>
	 * were executed without any issues.<br><br>
	 * 
	 * List of return messages:<br>
	 * <ul>
	 * <li>"COMPLETE" - if everything has been executed without any errors</li>
	 * <li>"INCOMPLETE&lt;msg&gt;" - if some commands couldn't be executed, they'll be added to the "msg" (first command to fail and its trailing commands)</li>
	 * <li>"DNGCLOSE&lt;msg&gt;" - if the UltraSonic sensor reacts to "danger close" walls. The robot will halt and stop executing commands. These commands will be returned within the "msg".</li>
	 * <li>"ERROR&lt;msg&gt; - if something went wrong but coldn't be determined. "msg" will contain the remaining none-executed commands."</li>
	 * </ul>
	 * 
	 * @param msg
	 * @return
	 */
	public String doCommand(String msg) {
		String[] cmdList = prepCmds(msg);
		
		for(int i = 0; i < cmdList.length; i++) {
			doMovement(cmdList[i], cmdList[i+1]);
			i++;
		}
		
		// Stop moving
		Movement.stop();
		
		return "COMPLETE";
	}
	
	/**
	 * Method which takes a command and executes it within the given timeframe.
	 * 
	 * @param cmd the command which the brick is supposed to perform.
	 * @param timeframe for how long is the command supposed to run. Unit is given in milliseconds.
	 */
	private void doMovement(String cmd, String timeframe) {
		switch(cmd) {
			case "C_FW":
				Movement.forward();
				LCD.clear();
				LCD.drawString("FORWARD", 0, 0);
				Delay.msDelay(Long.parseLong(timeframe));
				Movement.stop();
				break;
			case "C_BW":
				Movement.backward();
				LCD.clear();
				LCD.drawString("BACKWARDS", 0, 0);
				Delay.msDelay(Long.parseLong(timeframe));
				Movement.stop();
				break;
			case "C_HL":
				Movement.hardLeft();
				LCD.clear();
				LCD.drawString("HARDLEFT", 0, 0);
				Delay.msDelay(Long.parseLong(timeframe));
				Movement.stop();
				break;
			case "C_HR":
				Movement.hardRight();
				LCD.clear();
				LCD.drawString("HARDRIGHT", 0, 0);
				Delay.msDelay(Long.parseLong(timeframe));
				Movement.stop();
				break;
			default:
				LCD.clear();
				LCD.drawString("Command not recognised", 0, 0);
				Delay.msDelay(1000);
		}
	}
	
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
	 * @return An array containing the command and its timeframe in the following array index.
	 */
	private String[] prepCmds(String msg) {
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
