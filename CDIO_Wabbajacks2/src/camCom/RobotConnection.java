package camCom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.*;

public class RobotConnection {
	private String address;
	private DataInputStream in;
	private DataOutputStream out;
	
	// private final String SHEO_ADDRESS = "00:16:53:0A:71:1B";
	
	/**
	 * Class constructor.
	 */
	public RobotConnection(String address) {
		NXTConnection conn = Bluetooth.waitForConnection();
		
		in = conn.openDataInputStream();
		out = conn.openDataOutputStream();
		
		String s = null;
		
		try {
			while((s = in.readUTF()) != null) {
				LCD.drawString(s, 0, 0);
				
				Button.waitForAnyPress();
				
				out.writeBytes("Received: " + s);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				out.close();
				in.close();
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}