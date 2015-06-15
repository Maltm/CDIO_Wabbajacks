package robot;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class SonarControl extends Thread {
	private UltrasonicSensor sonar;		// Our sonar
	private Thread cmdThread;			// Which thread to interrupt if distance < DNG_CLOSE
	private volatile boolean running;	// Keep pinging if "running" is true
	
	// DEFAULT VALUES
	private final int DNG_CLOSE = 23;	// Defines the danger close threshold

	/**
	 * SonarControl class constructor. Takes a single parameter to determine which thread to interrupt.<br>
	 * The cmdThread will be interrupted if, and only if, the distance read by the sonar is greater than the {@link SonarControl#DNG_CLOSE} value.<br>
	 * 
	 * @param cmdThread The thread that has to be interrupted upon DANGER CLOSE encounter.
	 */
	public SonarControl(Thread cmdThread) {
		this.cmdThread = cmdThread;
		this.running = true;
	}
	
	/**
	 * Keeps running 'till the read distance by the sonar is greater than the {@link SonarControl#DNG_CLOSE} value.<br>
	 * 
	 */
	@Override
	public void run() {
		try {
			// Start sonar
			this.sonar = new UltrasonicSensor(SensorPort.S1);
			
			// Force switch (if not by default) to "continuous"-mode (keep pinging)
			this.sonar.continuous();
			
			// Minor fix (seems to work)
			Thread.sleep(100);
			
			// Keep track of distance - break if distance is "DNGCLOSE"
			while(this.running) {
				if(this.sonar.getDistance() < this.DNG_CLOSE) {
					this.running = false;
				}
			}
			
			// Stop the command thread and post "DNGCLOSE" message
			this.cmdThread.interrupt();
			
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// GETTERS
	public UltrasonicSensor getSonar() {
		return sonar;
	}

	public Thread getCmdThread() {
		return cmdThread;
	}

	public boolean isRunning() {
		return running;
	}
}
