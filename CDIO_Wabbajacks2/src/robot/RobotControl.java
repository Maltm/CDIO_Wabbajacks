package robot;

/*
 * Controls the robot overall - movement by command input, sensors etc.
 * 
 * @author Kristin Hansen
 */

public class RobotControl extends Thread {
	private MoveControl cmd;
	private SonarControl sonar;
	
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
		// Instantiate a response string - default will be "ERROR"
		String response = "ERROR<unknown>";
		
		try {
			// Instantiate thread objects
			cmd = new MoveControl(msg);
			sonar = new SonarControl(cmd);
			
			// Start run-methods
			sonar.start();
			cmd.start();
			
			// Wait for "cmd"-thread to end
			cmd.join();
			
			// Check if cmd thread completed without interruption
			if(!sonar.isRunning()) {
				Movement.stop();
				
				response = "DNGCLOSE<" + cmd.getPreppedMsg() + ">";
				
				Movement.backward();
				Thread.sleep(1500);
			}
			
			// Get the computed response
			response = cmd.getResponse();
			
			// Stop any movement if some command didn't stop the motors
			Movement.stop();
		} catch (InterruptedException e) {
			response = "ERROR<interruptedException>";
		}
		
		return response;
	}
}