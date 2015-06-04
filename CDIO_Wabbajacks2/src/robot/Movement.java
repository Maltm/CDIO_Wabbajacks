package robot;

import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;


public class Movement implements Runnable{
	private static int yes = 0;
	/**
	 * stop()
	 * stop the movement of both wheels
	 */
	public static void stop(){
//		Motor.A.stop();
//		Motor.B.stop();
		Thread T1 = new Thread(new Movement());
		Thread T2 = new Thread(new Movement());
		new Runnable(
			void run() {
				Motor.A.stop(true);
			}
		);
		new Runnable();
		yes = 3;
		T1.run();
		yes = 4;
		T2.run();
	}
	private static void stopA(){
		Motor.A.stop(true);
	}
	private static void stopB(){
		Motor.B.stop(true);
	}
	
	public static void forward(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public static void backward(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.A.backward();
		Motor.B.backward();
	}
	
	public static void hardLeft(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.A.forward();
		Motor.B.backward();
	}
	
	public static void hardRight(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed(i);
		Motor.B.forward();
		Motor.A.backward();
	}
	
	public static void softLeft(int i) {
		Motor.A.setSpeed(i);
		Motor.B.setSpeed((int)(i*0.33));
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public static void softRight(int i) {
		Motor.A.setSpeed((int)(i*0.33));
		Motor.B.setSpeed(i);
		Motor.A.forward();
		Motor.B.forward();
	}
	
	public static void corner(UltrasonicSensor US, int direction){
		
	}
	
	public static void turn90(boolean right){
		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();
		
		if(right){
			Motor.B.rotate(375);
		}
		else{
			Motor.A.rotate(375);
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		switch (yes) {
		default:
			break;
		case 1:
			break;
		case 2:
			break;
		case 3: 
			stopA();
			break;
		case 4:
			stopB();
			break;
		}
	}
}
