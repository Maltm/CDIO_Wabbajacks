package robot;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class SonarControl implements Runnable {
	private UltrasonicSensor sonar = new UltrasonicSensor(SensorPort.S1);
	private Thread cmdThread;
	private volatile boolean running;
	
	public SonarControl(Thread cmdThread) {
		this.cmdThread = cmdThread;
	}
	
	@Override
	public void run() {
		while(running) {
			if(sonar.getDistance() < 13)
				running = false;
		}
		
		cmdThread.interrupt();
	}
}
