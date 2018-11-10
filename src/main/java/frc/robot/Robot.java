/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.SetGear;
import frc.robot.commands.autonomous.AutoCommandGroup;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.FPA;
import frc.robot.subsystems.Gyro;
import frc.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {
	public static final double AUTO_TIMEOUT = 5;
	public static final Drivetrain drivetrain = new Drivetrain();
	public static final Gyro gyro = new Gyro();
	public static final Lift lift = new Lift();
	public static final Claw claw = new Claw();
	public static final FPA fpa = new FPA();
	public static OI oi;
	public static Field field = new Field("LLL");
	
	public static AutoCommandGroup autonomous_command_group;

	public static enum AutonomousTarget {
		SWITCH, SCALE, AUTO_LINE, AUTO_LINE_SIMPLE, SWITCH_SIMPLE, VAULT, MOTION_PROFILE, NOTHING
	}

	public static SendableChooser<Integer> position_chooser = new SendableChooser<Integer>();
	public static SendableChooser<AutonomousTarget> autonomous_target_chooser = new SendableChooser<AutonomousTarget>();
	public static SendableChooser<AutonomousTarget> secondary_autonomous_target_chooser = new SendableChooser<AutonomousTarget>();
	
	public void robotPeriodic() {
		SmartDashboard.putNumber("Current Gyro Angle", gyro.getAngle());
		SmartDashboard.putNumber("Current Absolute Gyro Angle", gyro.getAbsoluteAngle());
		SmartDashboard.putBoolean("Is Close Left", field.isCloseSwitchLeft());
		SmartDashboard.putBoolean("Is Scale Left", field.isScaleLeft());
		SmartDashboard.putBoolean("Is Far Left", field.isFarSwitchLeft());
		SmartDashboard.putNumber("Left Encoder", Robot.drivetrain.getLeftEncoderPosition());
		SmartDashboard.putNumber("Right Encoder", Robot.drivetrain.getRightEncoderPosition());
		SmartDashboard.putNumber("Lift Encoder", Robot.lift.getPosition());
		SmartDashboard.putBoolean("Claw Open", claw.getOpen());
	}

	public void enabledInit() {
		field = new Field(DriverStation.getInstance().getGameSpecificMessage());
		new SetGear(SetGear.Position.LOW).start();
		RobotMap.gyro.reset();
	}

	@Override
	public void robotInit() {
		oi = new OI();
		oi.init();
		RobotMap.init();
		gyro.calibrate();
		fpa.setUp();
    	Robot.lift.resetPosition();
    	
    	position_chooser.addDefault("Select", -1);
		position_chooser.addObject("Left", 0);
		position_chooser.addObject("Center", 1);
		position_chooser.addObject("Right", 2);
		SmartDashboard.putData("Position", position_chooser);
		
		autonomous_target_chooser.addDefault("Nothing", AutonomousTarget.NOTHING);
		autonomous_target_chooser.addObject("Scale", AutonomousTarget.SCALE);
		autonomous_target_chooser.addObject("Switch", AutonomousTarget.SWITCH);
		autonomous_target_chooser.addObject("Auto Line", AutonomousTarget.AUTO_LINE);
		autonomous_target_chooser.addObject("Auto Line Simple", AutonomousTarget.AUTO_LINE_SIMPLE);
		autonomous_target_chooser.addObject("Switch Simple", AutonomousTarget.SWITCH_SIMPLE);

		SmartDashboard.putData("Target", autonomous_target_chooser);
		
		secondary_autonomous_target_chooser.addDefault("Nothing", AutonomousTarget.NOTHING);
		secondary_autonomous_target_chooser.addObject("Scale", AutonomousTarget.SCALE);
		secondary_autonomous_target_chooser.addObject("Switch", AutonomousTarget.SWITCH);
		secondary_autonomous_target_chooser.addObject("Vault", AutonomousTarget.VAULT);

		SmartDashboard.putData("Secondary Target", secondary_autonomous_target_chooser);
		
		SmartDashboard.putBoolean("Only do close side", false);
	}

	@Override
	public void disabledInit() {
		fpa.setUp();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		robotPeriodic();
	}

	@Override
	public void autonomousInit() {
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */
		enabledInit();
		autonomous_command_group = new AutoCommandGroup();
		autonomous_command_group.start();
		//count=0;
		//drivetrain.resetEncoderPosition(); //MR WOOD
	}

	//private int count=0;

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		robotPeriodic();
//		System.out.println(drivetrain.getLeftEncoderPosition()+","+drivetrain.getRightEncoderPosition());
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
//		if (autonomous_command_group != null) {
//			autonomous_command_group.cancel();
//		}
		fpa.setDown();
		enabledInit();
		//count=0;
		//drivetrain.resetEncoderPosition(); //MR WOOD
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		robotPeriodic();
		
		//Debug output used to fix issues with the elevator encoder
//		System.out.println(RobotMap.lift_talon_1.getSelectedSensorPosition(0) + "\t" + Lift.GROUND_HEIGHT);
//		System.out.println(RobotMap.right_drive_talon_1.getSelectedSensorPosition(0));
		//if (count++%10==0) {
		//	System.out.println(count*0.02+","+drivetrain.getLeftEncoderPosition()+","+drivetrain.getRightEncoderPosition());
		//}
	}

	@Override
	public void testPeriodic() {
	}
}
