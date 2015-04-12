package robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class movement {
	public void stop() {
		Motor.A.stop();
		Motor.B.stop();
	}
	
	public void forward(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.A.forward();
		Motor.B.forward();
		Button.waitForAnyPress();
		stop();
	}
	
	public void backward(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.A.backward();
		Motor.B.backward();
		Button.waitForAnyPress();
		stop();
	}
	
	public void hardLeft(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.A.forward();
		Motor.B.backward();
		Button.waitForAnyPress();
		stop();
	}
	
	public void hardRight(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.B.forward();
		Motor.A.backward();
		Button.waitForAnyPress();
		stop();
	}
	
	public void softLeft(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed((int)(i*0.33));
		Motor.A.forward();
		Motor.B.forward();
		Button.waitForAnyPress();
		stop();
	}
	
	public void softRight(int i) {
		Motor.A.setSpeed((int)(i*0.33));
		Motor.B.setSpeed(i);
		Motor.A.forward();
		Motor.B.forward();
		Button.waitForAnyPress();
		stop();
	}
	
}
