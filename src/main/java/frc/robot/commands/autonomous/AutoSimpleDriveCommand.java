package frc.robot.commands.autonomous;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoSimpleDriveCommand extends Command {
	//Drive speed
	private double left, right, time;
	private double K_LEFT=1.0, K_RIGHT=1.05;
	
	/**
	 * <b>speed</b> controls the speed at which it drives. 
	 * <br><b>time</b> is the time to drive.
	 *@author Mr. Wood
	 */
	public AutoSimpleDriveCommand(double left, double right, double time) {
		requires(Robot.drivetrain);
		this.left = left;
		this.right = right;
		this.time=time;
	}

	protected void initialize() {
		//Robot.gyro.resetAngle();
		Robot.drivetrain.resetEncoderPosition();
		Robot.drivetrain.setPercentVoltage(left*K_LEFT, right*K_RIGHT);
	}

	protected void execute() {
		Robot.drivetrain.setPercentVoltage(left*K_LEFT, right*K_RIGHT);
	}

	protected boolean isFinished() {
		return (timeSinceInitialized()>this.time);
	}

	protected void end() {
		Robot.drivetrain.setPercentVoltage(0, 0);
	}

	protected void interrupted() {
		Robot.drivetrain.setPercentVoltage(0, 0);
	}
}
