package robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class TestClass {
	
	public TestClass(int action) {
		switch(action) {
			case 1:
				moveTest();
				break;
			case 2:
				ultraTest(new UltrasonicSensor(SensorPort.S1), new Movement());
				break;
			case 3:
				breakTest();
				break;
			case 4:
				test4();
				break;
			default:
				break;
		}
	}
	
	private void test4(){
		Movement.hardLeft();
	}
	
	private void breakTest(){
		Movement.forward();
		Button.waitForAnyPress();
		Movement.stop();
	}

	private void moveTest(){
		LCD.drawString("Test", 0, 0);
		LCD.drawString("Next: Forward", 0, 3);
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("Forward", 0, 0);
		LCD.drawString("Next: Backwards", 0, 3);
		Movement.forward();
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("Backwards", 0, 0);
		LCD.drawString("Next: SoftLeft", 0, 3);
		Movement.backward();
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("SoftLeft", 0, 0);
		LCD.drawString("Next: SoftRight", 0, 3);
		Movement.softLeft();
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("SoftRight", 0, 0);
		LCD.drawString("Next: HardLeft", 0, 3);
		Movement.softRight();
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("HardLeft", 0, 0);
		LCD.drawString("Next: HardRight", 0, 3);
		Movement.hardLeft();
		Button.waitForAnyPress();

		LCD.clear();
		LCD.drawString("HardRight", 0, 0);
		LCD.drawString("Next: Finished", 0, 3);
		LCD.drawString("Test", 6, 4);
		Movement.hardRight();
		Button.waitForAnyPress();
		
		Movement.stop();
	}
	
	private void ultraTest(UltrasonicSensor US, Movement move){
		Movement.forward();
		while(US.getDistance() > 20);
		Movement.stop();
//		Button.waitForAnyPress();
//		Movement.backward();
//		while(US.getDistance() < 20);
		
//		Movement.forward();
//		Button.waitForAnyPress();
//		Movement.stop();
//		Movement.hardRight();
//		Button.waitForAnyPress();
//		Movement.stop();
	}
}
