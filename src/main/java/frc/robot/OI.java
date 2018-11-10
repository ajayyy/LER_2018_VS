/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.ArcadeGyroDriveCommand;
import frc.robot.commands.StraightGyroDriveCommand;
import frc.robot.commands.instant.ResetLiftPositionCommand;
import frc.robot.commands.instant.ToggleClawPositionCommand;
import frc.robot.commands.instant.IntakeOpenCommand;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.XBoxController;
import frc.robot.XBoxPOVButton;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
	
	// input number definitions
	final int L_JOY = 0;
	final int R_JOY = 1;
	final int XBOX = 2;

	// button number definitions
	
	// right joystick buttons
	final int STRAIGHT_GYRO = 1;
	final int ARCADE_GYRO = 2;
	final int CANCEL_AUTO = 2;

	// left joystick buttons
	final int RESET_ODOM = 2;
	final int DROP_OVERRIDE = 1;

	// xbox
	final int SCALE_HEIGHT = XBoxController.XBOX_Y;
	final int SCALE_LOWER_HEIGHT = XBoxController.XBOX_RB;
	final int SWITCH_HEIGHT = XBoxController.XBOX_X;
	final int GROUND_HEIGHT = XBoxController.XBOX_A;
	final int TOGGLE_CLAW_POSITION = XBoxController.XBOX_B;
	final int EJECT = XBoxController.XBOX_DPAD_UP_ANGLE;
	final int RESET_LIFT_POSITION = XBoxController.XBOX_START;
	final int HOLD_CLIMB_VOLTAGE = XBoxController.XBOX_BACK;
	final int TOGGLE_INTAKE_OPEN = XBoxController.XBOX_LB;
	final int SET_INTAKE_OPEN = XBoxController.XBOX_DPAD_LEFT_ANGLE;
	final int SET_INTAKE_CLOSE = XBoxController.XBOX_DPAD_RIGHT_ANGLE;

	// controller initialization
	public Joystick l_joy = new Joystick(L_JOY);
	public Joystick r_joy = new Joystick(R_JOY);
	public XBoxController xbox = new XBoxController(XBOX);

	// button initialization
	public Button straight_gyro_button = new JoystickButton(r_joy, STRAIGHT_GYRO);
	public Button arcade_gyro_button = new JoystickButton(r_joy, ARCADE_GYRO);
	public Button cancel_auto_button = new JoystickButton(l_joy, CANCEL_AUTO);
	public Button drop_override = new JoystickButton(l_joy, DROP_OVERRIDE);
	// public Button reset_odom_button = new JoystickButton(l_joy, RESET_ODOM);

	public XBoxButton eject_button = new XBoxButton(xbox, EJECT);
	public XBoxTrigger intake_trigger = new XBoxTrigger(xbox, false);
	public XBoxTrigger outtake_trigger = new XBoxTrigger(xbox, true);
	public XBoxButton scale_height_button = new XBoxButton(xbox, SCALE_HEIGHT);
	public XBoxButton scale_lower_height_button = new XBoxButton(xbox, SCALE_LOWER_HEIGHT);
	public XBoxButton switch_height_button = new XBoxButton(xbox, SWITCH_HEIGHT);
	public XBoxButton ground_height_button = new XBoxButton(xbox, GROUND_HEIGHT);
	public XBoxButton hold_climb_button = new XBoxButton(xbox, HOLD_CLIMB_VOLTAGE);
	public XBoxButton toggle_claw_position_button = new XBoxButton(xbox, TOGGLE_CLAW_POSITION);
	public XBoxButton reset_lift_position = new XBoxButton(xbox, RESET_LIFT_POSITION);
	public XBoxButton toggle_intake_open = new XBoxButton(xbox, TOGGLE_INTAKE_OPEN);
	public XBoxPOVButton set_intake_open = new XBoxPOVButton(xbox, SET_INTAKE_OPEN);
	public XBoxPOVButton set_intake_close = new XBoxPOVButton(xbox, SET_INTAKE_CLOSE);

	public void init() {
		straight_gyro_button.whileHeld(new StraightGyroDriveCommand());
		arcade_gyro_button.whileHeld(new ArcadeGyroDriveCommand());
		toggle_claw_position_button.toggleWhenPressed(new ToggleClawPositionCommand());
		reset_lift_position.whenPressed(new ResetLiftPositionCommand());
		toggle_intake_open.whenPressed(new IntakeOpenCommand());
		set_intake_open.whenPressed(new IntakeOpenCommand(true));
		set_intake_close.whenPressed(new IntakeOpenCommand(false));
		
//		CancelAuto cancel_auto = new CancelAuto();
//		cancel_auto.setRunWhenDisabled(true);
//		cancel_auto_button.whenPressed(cancel_auto);
	}
}
