package camCom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.*;

public class RobotConnection {
	private BufferedReader in;
	private DataOutputStream out;
	
	/**
	 * Class constructor.
	 */
	public RobotConnection() {
		LCD.drawString("Waiting for device...", 0, 0);
		
		NXTConnection conn = Bluetooth.waitForConnection();
		
		LCD.drawString("Connected to:\n" + conn.getAddress(), 0, 0);
		
		in = new BufferedReader(new InputStreamReader(conn.openDataInputStream()));
		out = new DataOutputStream(conn.openDataOutputStream());
		
		Button.waitForAnyPress();
		
		try {
			LCD.drawString("", 0, 0);
			
			out.writeBytes("GO\n");
			
			String s = null;
			int i = 0;
			
			while(true) {
				LCD.drawString(in.readLine(), 0, 0);
				
				Button.waitForAnyPress();
				
				if(i == 10) break;
				
				out.writeBytes(i+"\n");
				
				i++;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				out.writeBytes("END\n");
				out.close();
				in.close();
			} catch(IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		new RobotConnection();
	}
}