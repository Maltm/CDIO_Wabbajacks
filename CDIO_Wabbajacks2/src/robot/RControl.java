package robot;

import robot.utils.PrepareStatements;
import lejos.nxt.LCD;
import lejos.util.Delay;

/*
 * Controls the robot overall - movement by command input, sensors etc.
 * 
 * @author Kristin Hansen
 */

public class RControl {
	private String response;
	private Thread cmd;
	
	/**
	 * RControl class constructor.
	 * 
	 */
	public RControl() {
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
	 * @param msg List of commands to be executed.
	 * @return Whether or not the series of commands were executed.
	 */
	public String doCommand(String msg) {
		// We start with setting the response string to ERROR - if nothing is done/executed (something went wrong) this will be returned.
		response = "ERROR<unknown>";
		
		// Start executing commands
		cmd = new Thread(new MControl(this, msg));
		
		Thread sonar = new Thread(new SonarControl(cmd));
		
		cmd.start();
		sonar.start();
		
		while(cmd.isAlive()) System.out.println("waiting...");
		
		// Stop any movement if some command didn't stop the motors
		Movement.stop();
		
		return response;
	}
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
