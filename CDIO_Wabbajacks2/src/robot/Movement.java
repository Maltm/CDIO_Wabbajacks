package robot;

import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.RegulatedMotor;

/**
 * Movement is a controller object which has all the method for controlling the
 * robots motors for movement.
 * All methods in Movement is STATIC and should be accessed in a STATIC way.
 * 
 * @author Anders Bækhøj
 */
public class Movement{
	private static int SPEED = 400;
	private static RegulatedMotor rightMotor = Motor.A;
	private static RegulatedMotor leftMotor = Motor.B;
	/**
	 * stop()
	 * stop the movement of both wheels instant
	 */
	public static void stop(){	
		leftMotor.stop(true);
		rightMotor.stop();
	}
	
	/**
	 * stopSoft()
	 * stop the movement of both wheels by turning of the motors rather then stopping them.
	 */
	public static void stopSoft(){
		leftMotor.stop(true);
		rightMotor.stop(true);
	}
	
	/**
	 * starts forward movement 
	 */
	public static void forward() {
		leftMotor.setAcceleration(600);
		rightMotor.setAcceleration(600);
		
		leftMotor.setSpeed(SPEED);
		rightMotor.setSpeed(SPEED);
		
		leftMotor.forward();
		rightMotor.forward();
	}
	
	/**
	 * start backwards movement
	 */
	public static void backward() {
		leftMotor.setAcceleration(600);
		rightMotor.setAcceleration(600);
		
		leftMotor.setSpeed(SPEED);
		rightMotor.setSpeed(SPEED);
		
		leftMotor.backward();
		rightMotor.backward();
	}
	
	/**
	 * turn left 90 degrees on the spot
	 */
	public static void hardLeft() {
		if(leftMotor.isMoving()) stop();
		
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		
		leftMotor.setSpeed(SPEED);
		rightMotor.setSpeed(SPEED);
		leftMotor.rotate(-180, true);
		rightMotor.rotate(180);
		stop();
	}
	
	/**
	 * turn right 90 degrees on the spot
	 */
	public static void hardRight() {
		if(leftMotor.isMoving()) stop();
		
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		
		leftMotor.setSpeed(SPEED);
		rightMotor.setSpeed(SPEED);
		leftMotor.rotate(180,  true);
		rightMotor.rotate(-180);
		stop();
	}
	
	/**
	 * turn left XX degrees while driving
	 * The more speed the harder the turn
	 */
	public static void softLeft() {
		leftMotor.setSpeed((int)(SPEED*0.33));
		rightMotor.setSpeed(SPEED);
		leftMotor.forward();
		rightMotor.forward();
	}
	
	/**
	 * turn right XX degrees while driving
	 * The more speed the harder the turn
	 */
	public static void softRight() {
		leftMotor.setSpeed(SPEED);
		rightMotor.setSpeed((int)(SPEED*0.33));
		leftMotor.forward();
		rightMotor.forward();
	}
	
	/**
	 * Turn left xx degrees while stationary
	 * @param angle
	 */
	public static void turnLeft(int degree) {
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		
		leftMotor.rotate(degree*2);
		rightMotor.rotate(-degree*2);
	}
	
	/**
	 * Turn right xx degrees while stationary
	 * @param angle
	 */
	public static void turnRight(int degree) {
		leftMotor.resetTachoCount();
		rightMotor.resetTachoCount();
		
		leftMotor.rotate(-degree*2);
		rightMotor.rotate(degree*2);
	}
	
	/**
	 * Robot takes a corner with the help of the front UltrasonicSensor
	 * @param US - UltrasonicSensor object
	 * @param direction - 0 going left, 1 going right else nothing
	 */
	public static void corner(UltrasonicSensor US, int direction){
		// TODO
	}
	
	/**
	 * Gets the speed which the robot runs its motors with
	 * Standard value is 400
	 * @return SPEED integer
	 */
	public static int getSPEED() {
		return SPEED;
	}

	/**
	 * Change the speed which the robot runs its motors with
	 * Standard value is 400
	 * @param speed integer
	 */
	public static void setSPEED(int speed) {
		SPEED = speed;
	}
	
	//TODO Consider putting up a Acceleration so the robot have a much softer start for forward movement and maybe set to fast acceleration again when it has to do turns.
}
