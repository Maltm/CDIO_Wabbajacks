package camCom;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import robot.Movement;
import lejos.nxt.Button;
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
				
				switch(s) {
					case "Are you ready?":
						LCD.clear();
						LCD.drawString(s, 0, 0);
						Button.waitForAnyPress();
						out.writeBytes("yes yes\n");
						out.flush();
						break;
					case "Move forward":
						LCD.clear();
						LCD.drawString(s, 0, 0);
						Movement.forward();
						Button.waitForAnyPress();
						Movement.stop();
						out.writeBytes("Moved forward\n");
						out.flush();
						break;
					case "Move backward":
						LCD.clear();
						LCD.drawString(s, 0, 0);
						Movement.backward();
						Button.waitForAnyPress();
						Movement.stop();
						out.writeBytes("Moved backward\n");
						out.flush();
						break;
					default:
						break;
				}
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