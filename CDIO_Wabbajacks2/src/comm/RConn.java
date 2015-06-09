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
			LCD.drawString("Waiting for device...", 0, 0);
			
			NXTConnection conn = Bluetooth.waitForConnection();
			
			LCD.clear();
			LCD.drawString("Connected to:\n" + conn.getAddress(), 0, 0);
			
			in = new BufferedReader(new InputStreamReader(conn.openDataInputStream()));
			out = conn.openDataOutputStream();
			
			try {
				LCD.clear();
				LCD.drawString("Ready for commands.", 0, 0);
				
				out.writeBytes("READY\n");
				out.flush();
				
				String msg = null;
				
				while(true) {
					if((msg = in.readLine()) != null) {
						if(msg.equals("END")) break;
						
						LCD.clear();
						LCD.drawString("RConn received:\n" + msg, 0, 0);
						
						String response = rctrl.doCommand(msg);
						
						out.writeBytes(response+"\n");
						out.flush();
						
						LCD.clear();
						LCD.drawString("RConn sending:\n" + response, 0, 0);
					}
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally {
				try {
					out.writeBytes("END\n");
					out.flush();
					
					out.close();
					in.close();
				} catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new RConn();
	}
}