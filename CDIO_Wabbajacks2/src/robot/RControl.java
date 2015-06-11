package robot;

import java.util.Timer;
import java.util.TimerTask;

import robot.utils.PrepareStatements;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

/*
 * Controls the robot overall - movement by command input, sensors etc.
 * 
 * @author Kristin Hansen
 */

public class RControl extends TimerTask {
	private String response;
	private int cmdsExecuted;
	private UltrasonicSensor sonar;
	private Thread exe;
	
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
		
		// Reset command counter
		cmdsExecuted = 0;
		
		// Instantiate sonar
		sonar = new UltrasonicSensor(SensorPort.S1);

		String[] cmdList = PrepareStatements.prepCmds(msg);
		
		try {
			for(; cmdsExecuted < cmdList.length; cmdsExecuted++) {
				// Execute command
				doMovement(cmdList[cmdsExecuted], cmdList[cmdsExecuted+1]);
				
				// We count an extra time to skip timeframe
				cmdsExecuted++;
			}
			
			// If we reach this part everything has completed successfully
			response = "COMPLETE";
			
		} catch(IllegalArgumentException e) {
			// If a command wasn't recognized prepare response with INCOMPLETE and add the remaining commands
			response = "INCOMPLETE<" + PrepareStatements.prepStr(cmdList, cmdsExecuted) + ">";
		} catch(Exception e) {
			// If an unknown error occurs prepare response with ERROR and add the remaining commands
			response = "ERROR<" + PrepareStatements.prepStr(cmdList, cmdsExecuted) + ">";
		}
		
		// We make sure nothing is moving before returning
		Movement.stop();
		
		return response;
	}

	/**
	 * Method which takes a command and executes it within the given timeframe.
	 * 
	 * @param cmd the command which the brick is supposed to perform.
	 * @param timeframe for how long is the command supposed to run. Unit is given in milliseconds.
	 */
	private void doMovement(String cmd, String timeframe) throws IllegalArgumentException {
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
				
				// Throw exception
				throw new IllegalArgumentException("command not regocnised");
		}
	}
	
	/**
	 * This runnable will keep track of whether or not the robot is too close to the wall.
	 */
	@Override
	public void run() {
		if(sonar.getDistance() < 8)
			stopCommands();
	}
	
	/**
	 * Will stop the 
	 */
	private void stopCommands() {
		// TODO Auto-generated method stub
		
	}
}
