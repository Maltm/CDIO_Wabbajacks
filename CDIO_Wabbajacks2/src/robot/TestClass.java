package robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class TestClass {
	final int FORSPEED = 400;
	final Movement robotMovement = new Movement();
	
	public TestClass(int action) {
		switch(action) {
			case 1:
				moveTest(robotMovement);
				break;
			case 2:
				ultraTest(new UltrasonicSensor(SensorPort.S1), new Movement());
				break;
			default:
				break;
		}
	}

	private void moveTest(Movement move){
		LCD.drawString("Test", 0, 0);
		LCD.drawString("Next: Forward", 0, 3);
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("Forward", 0, 0);
		LCD.drawString("Next: Backwards", 0, 3);
		move.forward(FORSPEED);
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("Backwards", 0, 0);
		LCD.drawString("Next: SoftLeft", 0, 3);
		move.backward(FORSPEED);
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("SoftLeft", 0, 0);
		LCD.drawString("Next: SoftRight", 0, 3);
		move.softLeft(FORSPEED);
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("SoftRight", 0, 0);
		LCD.drawString("Next: HardLeft", 0, 3);
		move.softRight(FORSPEED);
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("HardLeft", 0, 0);
		LCD.drawString("Next: HardRight", 0, 3);
		move.hardLeft(FORSPEED);
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("HardRight", 0, 0);
		LCD.drawString("Next: Finished", 0, 3);
		LCD.drawString("Test", 6, 4);
		move.hardRight(FORSPEED);
		Button.waitForAnyPress();
		
		move.stop();
	}
	
	private void ultraTest(UltrasonicSensor US, Movement move){
		move.forward(FORSPEED);
		while(US.getDistance() > 20);
		move.stop();
//		Button.waitForAnyPress();
//		move.backward(FORSPEED);
//		while(US.getDistance() < 20);
		
//		move.forward(FORSPEED);
//		Button.waitForAnyPress();
//		move.stop();
//		move.hardRight(FORSPEED);
//		Button.waitForAnyPress();
//		move.stop();
	}
}
