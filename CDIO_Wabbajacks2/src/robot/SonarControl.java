package robot;

import java.util.TimerTask;

import lejos.nxt.UltrasonicSensor;

public class SonarControl extends TimerTask {
	private UltrasonicSensor sonar;
	
	public SonarControl(UltrasonicSensor sonar) {
		this.sonar = sonar;
	}
	
	@Override
	public void run() {
		if(sonar.getDistance() > 8) {
			Movement.stop();
		}
	}
}
