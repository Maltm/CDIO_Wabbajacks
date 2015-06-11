package robot;

import lejos.nxt.LCD;
import robot.utils.PrepareStatements;

public class MControl implements Runnable {
	private RControl rctrl;
	private String msg;
	
	public MControl(RControl rctrl, String msg) {
		this.rctrl = rctrl;
		this.msg = msg;
	}
	
	@Override
	public void run() {
		String[] cmdList = PrepareStatements.prepCmds(msg);
		
		int i = 0;
		
		try {
			for(; i < cmdList.length; i++) {
				// Execute command
				switch(cmdList[i]) {
					case "C_FW":
						Movement.forward();
						LCD.clear();
						LCD.drawString("FORWARD", 0, 0);
						Thread.sleep(Long.parseLong(cmdList[i+1]));
						Movement.stop();
						break;
					case "C_BW":
						Movement.backward();
						LCD.clear();
						LCD.drawString("BACKWARDS", 0, 0);
						Thread.sleep(Long.parseLong(cmdList[i+1]));
						Movement.stop();
						break;
					case "C_HL":
						Movement.hardLeft();
						LCD.clear();
						LCD.drawString("HARDLEFT", 0, 0);
						Thread.sleep(Long.parseLong(cmdList[i+1]));
						Movement.stop();
						break;
					case "C_HR":
						Movement.hardRight();
						LCD.clear();
						LCD.drawString("HARDRIGHT", 0, 0);
						Thread.sleep(Long.parseLong(cmdList[i+1]));
						Movement.stop();
						break;
					default:
						LCD.clear();
						LCD.drawString("Command not recognised", 0, 0);
						Thread.sleep(Long.parseLong(cmdList[i+1]));
						
						// Throw exception
						throw new IllegalArgumentException("command not regocnised");
				}
				
				// We count an extra time to skip cmdList[i+1] which is the timeframe
				i++;
			}
			
			// If we reach this part everything has completed successfully
			rctrl.setResponse("COMPLETE");
			
		} catch(IllegalArgumentException e) {
			// If a command wasn't recognized prepare response with INCOMPLETE and add the remaining commands
			rctrl.setResponse("INCOMPLETE<" + PrepareStatements.prepStr(cmdList, i) + ">");
		} catch(InterruptedException e) {
			rctrl.setResponse("DNGCLOSE<" + PrepareStatements.prepStr(cmdList, i) + ">");
		} catch(Exception e) {
			// If an unknown error occurs prepare response with ERROR and add the remaining commands
			rctrl.setResponse("ERROR<" + PrepareStatements.prepStr(cmdList, i) + ">");
		}
	}
}
