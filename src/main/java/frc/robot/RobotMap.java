/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.Claw;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	
	public static final int PID_MODE = 0;  //0 for closed loop 1 for cascaded closed loop

	// talon
	private static final int LEFT_TALON_1 = 1;
	private static final int LEFT_TALON_2 = 2;
	private static final int LEFT_TALON_3 = 3;
	private static final int RIGHT_TALON_1 = 12;
	private static final int RIGHT_TALON_2 = 11;//Victor SPX
	private static final int RIGHT_TALON_3 = 10;
	
	private static final int LIFT_TALON_1 = 4;
	private static final int LIFT_TALON_2 = 5;
	
	private static final int LEFT_CLAW_TALON = 9;
	private static final int RIGHT_CLAW_TALON = 8;
	
	private static final int FOUL_PREVENTION_APPARATUS_INT_R = 9;
	private static final int FOUL_PREVENTION_APPARATUS_INT_L = 8;

	// solenoids
	private static final int CLAW_UP_POSITION = 1;
	private static final int CLAW_DOWN_POSITION = 0;
	//private static final int GEAR_HIGH = -1;
	//private static final int GEAR_LOW = -1;
	private static final int CLAW_OPEN = 2;
	private static final int CLAW_CLOSED = 3;
	
	// sensors
//	private static final int FRONT_LEFT_DISTANCE_SENSOR = 0;
//	private static final int REAR_LEFT_DISTANCE_SENSOR = 1;
	
//	private static final int LINE_SENSOR = 5;

	public static TalonSRX left_drive_talon_1 = new TalonSRX(LEFT_TALON_1);
	public static TalonSRX left_drive_talon_2 = new TalonSRX(LEFT_TALON_2);
	public static TalonSRX left_drive_talon_3 = new TalonSRX(LEFT_TALON_3);
	public static TalonSRX right_drive_talon_1 = new TalonSRX(RIGHT_TALON_1);
	public static VictorSPX right_drive_talon_2 = new VictorSPX(RIGHT_TALON_2);
	public static TalonSRX right_drive_talon_3 = new TalonSRX(RIGHT_TALON_3);
	
	public static TalonSRX lift_talon_1 = new TalonSRX(LIFT_TALON_1);
	public static TalonSRX lift_talon_2 = new TalonSRX(LIFT_TALON_2);
	
	public static TalonSRX left_claw_talon = new TalonSRX(LEFT_CLAW_TALON);
	public static TalonSRX right_claw_talon = new TalonSRX(RIGHT_CLAW_TALON);
	
	public static PWM fpa_r = new PWM(FOUL_PREVENTION_APPARATUS_INT_R);
	public static PWM fpa_l = new PWM(FOUL_PREVENTION_APPARATUS_INT_L);

	public static DoubleSolenoid claw_position_solenoid = new DoubleSolenoid(CLAW_UP_POSITION, CLAW_DOWN_POSITION);
//	public static DoubleSolenoid gear_position_solenoid = new DoubleSolenoid(GEAR_HIGH, GEAR_LOW);
	public static DoubleSolenoid claw_open_solenoid = new DoubleSolenoid(CLAW_OPEN, CLAW_CLOSED);
//	public static AnalogInput front_left_distance_sensor = new AnalogInput(FRONT_LEFT_DISTANCE_SENSOR);
//	public static AnalogInput rear_left_distance_sensor = new AnalogInput(REAR_LEFT_DISTANCE_SENSOR);
	
//	public static AnalogInput line_sensor = new AnalogInput(LINE_SENSOR);
	
	public static I2C lidar = new I2C(I2C.Port.kMXP, 0xC4);
	
	public static final ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	
	public static void init() {
		left_drive_talon_2.follow(left_drive_talon_1);
		left_drive_talon_3.follow(left_drive_talon_1);
		right_drive_talon_2.follow(right_drive_talon_1);
		right_drive_talon_3.follow(right_drive_talon_1);
		
//		front_left_distance_sensor.setOversampleBits(16);
//		rear_left_distance_sensor.setOversampleBits(16);

		lift_talon_2.follow(lift_talon_1);
		
		Robot.claw.setPosition(Claw.Position.UP);
		Robot.claw.setOpen(false);
		
		LiveWindow.disableTelemetry(fpa_r);
		fpa_r.setBounds(20000, 20000, 10000, 0, 0);
		LiveWindow.disableTelemetry(fpa_l);
		fpa_l.setBounds(20000, 20000, 10000, 0, 0);
		Robot.fpa.setUp();
		

		left_drive_talon_1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,PID_MODE,0);
		right_drive_talon_1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,PID_MODE,0);
		
		lift_talon_1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, PID_MODE, 0);
		lift_talon_1.config_kP(0, 2, 0);
//		
//		left_talon_1.setSelectedSensorPosition(0,  0,  0);
//		right_talon_1.setSelectedSensorPosition(0,  0,  0);
//		
//		/* set closed loop gains in slot0 */
//		left_talon_1.config_kF(0, 0.34, 0);
//		left_talon_1.config_kP(0, 0.2, 0);
//		left_talon_1.config_kI(0, 0, 0);
//		left_talon_1.config_kD(0, 0, 0);
//		
//		/* set closed loop gains in slot0 */
//		right_talon_1.config_kF(0, 0.34, 0);
//		right_talon_1.config_kP(0, 0.2, 0);
//		right_talon_1.config_kI(0, 0, 0);
//		right_talon_1.config_kD(0, 0, 0);
		
//		
//		try {
//			UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//			camera.setResolution(800, 600);
//			camera.setFPS(15);
//		} 
//		catch (Exception e) {	
//		}
	}
}
