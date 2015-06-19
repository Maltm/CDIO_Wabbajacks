package comm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

//import robot.RobotControl;
import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

/*
 * Controls the connection FROM the brick TO the incoming connection.
 * 
 * @author Kristin Hansen
 */

public class RConn {
	private BufferedReader in;
	private DataOutputStream out;
	//private RobotControl rctrl;
	private boolean connected;
	
	/**
	 * Class constructor for RConnection.<br><br>
	 * 
	 * Will wait for a bluetooth connection to establish. When connection is terminated<br>
	 * the brick will wait for another connection to establish.<br><br>
	 * 
	 * The brick has to be paired with the connecting device BEFORE trying to establish<br>
	 * a connection. Best and easiest practice is to pair the computer with<br>
	 * the brick (search for the brick FROM the computer, and do the pairing<br>
	 * by using the pairing PIN configured on the brick. Default: 1 2 3 4).
	 */
	public RConn() {
		// Will wait for connection. When this connection is ended, a new connection can be established (hence the while-loop)
		while (true) {
			LCD.clear();
			
			this.connected = false;
			
			// Print a string on the display, indicating that RConn has been instantiated
			System.out.println("Waiting...");
			
			// Open a bluetooth connection and wait for a device to connect
			NXTConnection conn = Bluetooth.waitForConnection();
			
			// When a device has connected print its address on the display
			System.out.println("Connected to:\n" + conn.getAddress());
			
			// Open streams for communication
			this.in = new BufferedReader(new InputStreamReader(conn.openDataInputStream()));
			this.out = conn.openDataOutputStream();
			
			// If streams are open set connected to true
			if(this.in != null && this.out != null)	this.connected = true;
			
			try {
				// Buffer string
				String msg = null;
				
				while(this.connected) {
					// If the string is anything but NULL...
					if((msg = this.in.readLine()) != null) {
						// ... and NOT "END"
						if(msg.equals("END")) {
							this.connected = false;
							break;
						}
						
						/**
						 * TODO
						 * Put logic here - how to move etc.
						 */
						
						// Write the response back to the connected device
						this.out.writeBytes("Received: " + msg + "\n");
						this.out.flush();
						
						// Print the response on the display
						System.out.println("Reply:\n" + msg);
					}
					
					System.gc();
				}
			} catch (IOException e) {
				// TODO
			} finally {
				// End communication and close resources
				try {
					// Write an ending message to the connected device
					this.out.writeBytes("END\n");
					this.out.flush();
					
					// Close resources
					this.out.close();
					this.in.close();
					conn.close();
				} catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
			
			System.gc();
		}
	}
	
	public static void main(String[] args) {
		// Start connection
		new RConn();
	}
}