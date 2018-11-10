package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StraightGyroDriveCommand extends Command {

    public StraightGyroDriveCommand() {
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
    	double l = -Robot.oi.l_joy.getY();
    	double r = -Robot.oi.r_joy.getY();
    	
    	l = Math.abs(l) > Math.abs(r) ? l : r;
    	r = l;
    	
    	double[] straight_gyro_output = Robot.gyro.getStraightOutput(l, r);
    	
    	Robot.drivetrain.setPercentVoltage(straight_gyro_output[0], straight_gyro_output[1]);
    }

    protected boolean isFinished() {
        return false;
    }
}
