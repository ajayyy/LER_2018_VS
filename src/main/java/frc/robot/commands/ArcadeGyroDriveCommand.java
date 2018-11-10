package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArcadeGyroDriveCommand extends Command {

    public ArcadeGyroDriveCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gyro.resetAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double r_x = Robot.oi.r_joy.getX();
    	double r_y = Robot.oi.r_joy.getY();
    	
    	double[] arcade_gyro_output = Robot.gyro.getArcadeGyroOutput(r_x, r_y);
    	Robot.drivetrain.setPercentVoltage(arcade_gyro_output[0], arcade_gyro_output[1]);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
