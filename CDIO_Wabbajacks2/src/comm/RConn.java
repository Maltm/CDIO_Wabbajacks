package comm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import robot.RControl;
import lejos.nxt.LCD;
import lejos.nxt.comm.*;

/*
 * Controls the connection FROM the brick TO the incoming connection.
 * 
 * @author Kristin Hansen
 */

public class RConn {
	private BufferedReader in;
	private DataOutputStream out;
	private RControl rctrl;
	
	/**
	 * Class constructor for RConnection.<br><br>
	 * Will wait for a bluetooth connection to establish. When connection is terminated<br>
	 * the brick will wait for another connection to establish.<br><br>
	 * 
	 * The brick has to be paired with the connecting device BEFORE trying to establish a connection.<br>
	 * Best and easiest practice is to pair the computer with the brick (search for the brick FROM the computer,<br>
	 * and do the pairing, by using the pairing PIN configured on the brick. Default: 1 2 3 4).
	 */
	public RConn() {
		while (true) {
			// Print a string on the display, indicating that RConn has been instantiated
			LCD.drawString("Waiting for device...", 0, 0);
			
			// Open a bluetooth connection and wait for a device to connect
			NXTConnection conn = Bluetooth.waitForConnection();
			
			// When a device has connected print its address on the display 
			LCD.clear();
			LCD.drawString("Connected to:\n" + conn.getAddress(), 0, 0);
			
			// Open streams for communication
			in = new BufferedReader(new InputStreamReader(conn.openDataInputStream()));
			out = conn.openDataOutputStream();
			
			try {
				// Print a "ready"-message on the display, indicating that the brick is ready for commands
				LCD.clear();
				LCD.drawString("Ready for commands.", 0, 0);
				
				// Write to the connected device that the brick is ready
				out.writeBytes("READY\n");
				out.flush();
				
				// Buffer string
				String msg = null;
				
				while(true) {
					// If the string is anything but NULL...
					if((msg = in.readLine()) != null) {
						// ... and NOT "END"
						if(msg.equals("END")) break;						
						
						// ... print received command
						// TODO If a series of commands are received don't print them all (maybe print the amount of commands?)
						LCD.clear();
						LCD.drawString("RConn received:\n" + msg, 0, 0);
						
						// ... send the command to the robot controller and prepare a response
						String response = rctrl.doCommand(msg);
						
						// Write the response back to the connected device
						out.writeBytes(response + "\n");
						out.flush();
						
						// Print the response on the display
						LCD.clear();
						LCD.drawString("RConn sending:\n" + response, 0, 0);
					}
				}
			} catch (IOException e) {
				// Print exception message
				System.out.println(e.getMessage());
			} finally {
				// End communication and close ressources
				try {
					// Write an ending message to the connected device
					out.writeBytes("END\n");
					out.flush();
					
					// Close resources
					out.close();
					in.close();
					conn.close();
				} catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public static void main(String[] args) {
		// Start connection
		new RConn();
	}
}