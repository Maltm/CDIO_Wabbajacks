package robot;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class TestClass {
	
	
	public static void main(String[] args) {
		movement move = new movement();
		
		final int FORSPEED = 400;
		
		LCD.drawString("Test", 0, 0);
		Button.waitForAnyPress();
		LCD.clear();
		move.forward(FORSPEED);
	}
}
