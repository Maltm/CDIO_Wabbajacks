package robot;

/*
 * Controls the robot overall - movement by command input, sensors etc.
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
		 * "C_FW" received: COMMAND FORWARD		perform: Movement.forward
		 * "C_BW" received: COMMAND BACKWARDS	perform: Movement.backward
		 * "C_LF" received: COMMAND LEFT		perform: Movement.left
		 * etc. etc.
		 * 
		 * A message could consist of several commands:
		 * "||C_FW||C_BW||C_BW||C_LF||C_RG||C_FW" could be made into an array by splitting
		 * 1 -> C_FW
		 * 2 -> C_BW
		 * 3 -> C_BW
		 * 4...
		 * 5...
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
	
	public String doCommand(String msg) {
		// TODO
		System.out.println("Command: " + msg);
		
		return "COMPLETE";
	}
}
