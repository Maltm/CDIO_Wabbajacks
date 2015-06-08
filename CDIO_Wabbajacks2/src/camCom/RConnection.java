package camCom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import lejos.nxt.LCD;
import lejos.nxt.comm.*;

public class RConnection {
	private BufferedReader in;
	private DataOutputStream out;
	
	/**
	 * Class constructor for RConnection.<br><br>
	 * Will wait for a bluetooth connection to establish. When connection is established<br>
	 * a test will run where the LCD will print the numbers from 1 to 10 and then end the connection.
	 */
	public RConnection() {
		LCD.drawString("Waiting for device...", 0, 0);
		
		NXTConnection conn = Bluetooth.waitForConnection();
		
		LCD.drawString("Connected to:\n" + conn.getAddress(), 0, 0);
		
		in = new BufferedReader(new InputStreamReader(conn.openDataInputStream()));
		out = new DataOutputStream(conn.openDataOutputStream());
		
		try {
			LCD.drawString("Starting...\n", 0, 0);
			
			out.writeBytes("GO\n");
			
			String s = null;
			int i = 0;
			
			while(true) {
				if((s = in.readLine()) != null) {
					if(i > 10) break;
					if(s.equals("2345678876trfcvghi765456yu3ijhef")) break;
					
					LCD.drawString("Sending: " + i + "\n", 0, 0);
					
					out.writeBytes(i+"\n");
					
					i++;
				}
			}
			
			out.writeBytes("END\n");
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
	
	public static void main(String[] args) {
		new RConnection();
	}
}