package nxj;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class PCConnection {
	private String address;
	private DataOutputStream out;
	private DataInputStream in;
	
	// private final String SHEO_ADDRESS = "00:16:53:0A:71:1B";
	
	/**
	 * Class constructor. Will establish connection to a given address (MAC).
	 * 
	 * @param address Address to NXT brick (MAC format).
	 * @throws NXTCommException If any communication error occurs between PC and NXT brick.
	 * @throws IOException If in-/output stream error occurs.
	 */
	public PCConnection(String address) {
		this.address = address;
		System.out.println("Trying to establish connection...");
		System.out.println("Address:\t" + this.address);
		
		try {
			NXTComm ncom = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);		// Define communication
			
			NXTInfo ninf = new NXTInfo(NXTCommFactory.BLUETOOTH, "M8", this.address);	// What to connect to
			
			if(ncom.open(ninf)) {														// Open connection to defined brick
				System.out.println("Connected to " + this.address);						// Print info
				
				out = new DataOutputStream(ncom.getOutputStream());						// Open stream FROM the brick
				in = new DataInputStream(ncom.getInputStream());						// Open sream TO the brick
				
				out.writeBytes("y0000000");												// Test string (send to the brick)
				
				String s = null;														// Instantiate a new string (will hold data FROM the brick)
				
				while((s = in.readUTF()) != "END") {									// Keep reading FROM the brick 'till we receive "END"
					System.out.println(s);												// Print whatever we receive
				}
			}
		} catch (NXTCommException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				in.close();																// Closing ressources
				out.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		new PCConnection("00:16:53:0a:71:1b");
	}
}