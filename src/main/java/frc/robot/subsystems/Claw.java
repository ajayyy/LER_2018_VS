package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.ClawCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Claw extends Subsystem {
	Position current_position;
	boolean open = false;

	public enum Position {
		UP(DoubleSolenoid.Value.kForward),
		DOWN(DoubleSolenoid.Value.kReverse),
		OFF(DoubleSolenoid.Value.kOff);

		private DoubleSolenoid.Value value;    

		private Position(DoubleSolenoid.Value value) {
			this.value = value;
		}

		public DoubleSolenoid.Value getValue() {
			return value;
		}
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new ClawCommand());
	}

	public void setSpeed(double speed) {
		RobotMap.left_claw_talon.set(ControlMode.PercentOutput, speed);
		RobotMap.right_claw_talon.set(ControlMode.PercentOutput, -speed);
	}
	
	public void setLeftSpeed(double speed) {
		RobotMap.left_claw_talon.set(ControlMode.PercentOutput, speed);
	}
	public void setRightSpeed(double speed) {
		RobotMap.right_claw_talon.set(ControlMode.PercentOutput, -speed);
	}

	public void setPosition(Position position) {
		current_position = position;
		RobotMap.claw_position_solenoid.set(position.value);
	}

	public Position getPosition() {
		return current_position;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
		RobotMap.claw_open_solenoid.set(open ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
	}
	
	public boolean getOpen() {
		return open;
	}

}

