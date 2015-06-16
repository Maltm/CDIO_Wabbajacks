package robot;

import lejos.nxt.LCD;
import robot.utils.PrepareStatements;

/**
 * MoveControl controls the movements of the robot with a given message input.
 * The input will be prepared for execution with the {@link PrepareStatements#prepCmds(String)} method.
 * If the thread is interrupted (occurs if an instantiated sonar encounters a DANGER CLOSE situation),
 * the current number of executed commands is saved and can then be used to prepare a response string with the {@link PrepareStatements#prepCmds(String)} method
 * by calling {@link #getPreppedMsg()}.
 * 
 * @author Kristin Hansen
 */

public class MoveControl extends Thread {
	private String msg;
	private String response;
	private String[] cmdList;
	private int cmdsExecuted;
	
	// DEFAULTS
	private static final long HARD_TURN_TIME_FRAME = 1200;
	
	/**
	 * MoveControl class constructor. Takes a single parameter (if there's no message, there's no commands to execute - nothing will be done).<br>
	 * The format of message must be "||CMD;;TIME FRAME||CMD;;TIME FRAME||etc...||".<br>
	 * The message MUST start with "||" and end with "||". The command and time frame must be separated by two semicolons ";;".
	 * 
	 * @param msg An unformatted string containing commands and their respective time frames.
	 */
	public MoveControl(String msg) {
		this.msg = msg;
		this.response = null;
		this.cmdsExecuted = 0;
	}
	
	/**
	 * Will prepare a command {@link PrepareStatements#prepStr(String[], int)} list from the given message and start executing this list of commands.<br>
	 * A response will be prepared and can be read upon completion (use Thread.join to get notice of when the thread is done working.)
	 */
	@Override
	public void run() {
		// Create command list from incoming message (will contain commands in even index numbers and their respective time frames in odd index numbers)
		this.cmdList = PrepareStatements.prepCmds(this.msg);
		
		// Iterate through command list
		for(; this.cmdsExecuted < this.cmdList.length; this.cmdsExecuted++) {
			try {
				// Parse current command and its time frame
				doMovement(this.cmdList[cmdsExecuted], this.cmdList[cmdsExecuted+1]);
			} catch (IllegalArgumentException e) {
				// Incomplete (unintelligible command)
				this.response = "INCOMPLETE<" + PrepareStatements.prepStr(cmdList, cmdsExecuted) + ">";
				break;
			} catch (InterruptedException e) {
				// Danger close error handling
				this.response = "DNGCLOSE<" + PrepareStatements.prepStr(cmdList, cmdsExecuted) + ">";
				break;
			} catch (Exception e) {
				// Unknown error handling
				this.response = "ERROR<" + e.getMessage() + ">";
				break;
			}
			
			// Skip time frame index
			this.cmdsExecuted++;
		}
		
		// Return complete-message if all commands have been executed
		if(this.cmdsExecuted == (this.cmdList.length))
			this.response = "COMPLETE";
	}
	
	/**
	 * Method used to move the robot through a given command and a time frame.<br>
	 * If the parsed command can't be recognized an IllegalArgumentException will be thrown and break the command loop.<br>
	 * 
	 * @param cmd The desired command to be executed.
	 * @param timeframe The desired time frame for the command.
	 * @throws InterruptedException Will be thrown if a command is being executed while the thread is sleeping.
	 * @throws IllegalArgumentException Will be thrown if an unintelligible command is parsed.
	 */
	private void doMovement(String cmd, String timeframe) throws InterruptedException, IllegalArgumentException {
		switch(cmd) {
			case "C_FW":
				// Do movement
				Movement.forward();
				
				// Note movement on display
				LCD.clear();
				LCD.drawString("FORWARD", 0, 0);
				
				// If time frame is equals 0, let the brick run for 1 minute straight or let the sensor stop the brick.
				// ... or do the movement within the time frame
				if(Long.parseLong(timeframe) <= 0) Thread.sleep(60000);
				else Thread.sleep(Long.parseLong(timeframe));
				
				// Stop movement
				Movement.stop();
				
				// Remove movement from display
				LCD.clear();
				
				// Minor fix
				Thread.sleep(100);
				break;
			case "C_BW":
				// Do movement
				Movement.backward();
				
				// Note movement on display
				LCD.clear();
				LCD.drawString("BACKWARDS", 0, 0);
				
				// Check if time frame is less than 1 miliseconds (in reality it won't move the brick, but we're just making sure that the brick won't move backwards infinitely)
				if(Long.parseLong(timeframe) < 1) throw new IllegalArgumentException("time frame for backwards cannot be less than 1");
					
				// Keep doing the movement within the time frame
				Thread.sleep(Long.parseLong(timeframe));
				
				// Stop movement
				Movement.stop();
				
				// Remove movement from display
				LCD.clear();
				
				// Minor fix
				Thread.sleep(100);
				break;
			case "C_HL":
				// Do movement
				Movement.hardLeft();
				
				// Note movement on display
				LCD.clear();
				LCD.drawString("HARDLEFT", 0, 0);
				
				// We force HARD_TURN time limit if the time frame is less or equal to the minimum time for a HARD_TURN
				if(Long.parseLong(timeframe) <= HARD_TURN_TIME_FRAME) Thread.sleep(HARD_TURN_TIME_FRAME);
				else Thread.sleep(Long.parseLong(timeframe));
				
				// Stop movement
				Movement.stop();
				
				// Remove movement from display
				LCD.clear();
				
				// Minor fix
				Thread.sleep(100);
				break;
			case "C_HR":
				// Do movement
				Movement.hardRight();
				
				// Note movement on display
				LCD.clear();
				LCD.drawString("HARDRIGHT", 0, 0);
				
				// We force HARD_TURN time limit if the time frame is less or equal to the minimum time for a HARD_TURN
				if(Long.parseLong(timeframe) <= HARD_TURN_TIME_FRAME) Thread.sleep(HARD_TURN_TIME_FRAME);
				else Thread.sleep(Long.parseLong(timeframe));
				
				// Stop movement
				Movement.stop();
				
				// Remove movement from display
				LCD.clear();
				
				// Minor fix
				Thread.sleep(100);
				break;
			default:
				LCD.clear();
				LCD.drawString("Command not recognised", 0, 0);
				Thread.sleep(1800);
				LCD.clear();
				
				// Throw exception
				throw new IllegalArgumentException("command not regocnised");
		}
	}
	
	// GETTERS
	public String getMsg() {
		return this.msg;
	}
	
	public String getPreppedMsg() {
		return PrepareStatements.prepStr(this.cmdList, this.cmdsExecuted);
	}
	
	public String getResponse() {
		return this.response;
	}
	
	public String[] getCmdList() {
		return this.cmdList;
	}

	public int getCmdsExecuted() {
		return this.cmdsExecuted;
	}
}
