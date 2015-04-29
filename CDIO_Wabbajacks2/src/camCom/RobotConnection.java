package camCom;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class RobotConnection {
	private NXTComm ncom;
	private NXTInfo ninf;
	private String address;
	
	private final String SHEO_ADDRESS = "00:16:53:0A:71:1B";
	
	/**
	 * Class constructor.
	 */
	public RobotConnection(String address) {
		this.address = address;
		
		try {
			ncom = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			ninf = new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", SHEO_ADDRESS);
			
			if(ncom.open(ninf)) System.out.println("Connected");
			
			DataOutputStream out = (DataOutputStream) ncom.getOutputStream();
			DataInputStream in = (DataInputStream) ncom.getInputStream();
		} catch (NXTCommException e) {
			System.out.println("Communication error.");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new RobotConnection("rofl");
	}
}