package robot;

import org.jfree.util.WaitingImageObserver;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class TestClass {
	final static int FORSPEED = 400;

	public static void main(String[] args) {
		movement move = new movement();

		System.out.println(SensorPort.getInstance(1));

		UltrasonicSensor US = new UltrasonicSensor(SensorPort.S1);
		boolean ENTERdown = false;
		
		LCD.drawString("Test", 0, 0);
		Button.waitForAnyPress();
		
		while(true){
		move.turn90(true);
		Button.waitForAnyPress();
		
		move.turn90(false);
		Button.waitForAnyPress();
		}
//		ultraTest(US, move);
//				moveTest(move);

	}

	private static void moveTest(movement move){
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
	
	private static void ultraTest(UltrasonicSensor US, movement move){
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
