package robot;

import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;

public class movement {
	/**
	 * stop()
	 * stop the movement of both wheels
	 */
	public void stop() {
//		Motor.A.stop();
//		Motor.B.stop();
		Motor.A.stop(true);
		Motor.B.stop(true);
	}
	
	public void forward(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public void backward(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.A.backward();
		Motor.B.backward();
	}
	
	public void hardLeft(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.A.forward();
		Motor.B.backward();
	}
	
	public void hardRight(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.B.forward();
		Motor.A.backward();
	}
	
	public void softLeft(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed((int)(i*0.33));
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public void softRight(int i) {
		Motor.A.setSpeed((int)(i*0.33));
		Motor.B.setSpeed(i);
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public void corner(UltrasonicSensor US, int direction){
		
	}
	
	public void turn90(boolean right){
		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();
		
		if(right){
			Motor.B.rotate(375);
		}
		else{
			Motor.A.rotate(375);
		}
	}
}
