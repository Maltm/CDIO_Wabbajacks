package camCom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class BTReceive {
	public static void main(String [] args) throws Exception {
		String connected = "Connected";
		String waiting = "Waiting...";
		String closing = "Closing...";
		
		while (true) {
			LCD.drawString(waiting,0,0);
			NXTConnection connection = Bluetooth.waitForConnection(); 
			LCD.clear();
			LCD.drawString(connected,0,0);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.openDataInputStream()));
			DataOutputStream out = connection.openDataOutputStream();
			
			String s = null;
			
			do {
				s = in.readLine();
				
				LCD.clear();
				LCD.drawString(s, 0, 0);
				
				out.writeBytes(s+"\n");
				out.flush();
			} while(!(s.equals("END")));
			
			in.close();
			out.close();
			
			LCD.clear();
			LCD.drawString(closing,0,0);
			
			connection.close();
			LCD.clear();
		}
	}
}