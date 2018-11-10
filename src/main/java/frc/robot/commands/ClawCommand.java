package frc.robot.commands;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClawCommand extends Command {
	//Multipliers for speeds so the intake can in/outtake efficientl (not drop boxes)
	public static double inMultiplier = 1;
	public static double outMultiplier = 0.75;
	public static double eject_speed = 0.75;
    public ClawCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Robot.oi.eject_button.get()) {
    		Robot.claw.setSpeed(-eject_speed);
    	}
    	else if (Robot.oi.intake_trigger.getAnalog() > Robot.oi.outtake_trigger.getAnalog()) {
    		Robot.claw.setSpeed(Robot.oi.intake_trigger.getAnalog() * inMultiplier);
//    		Robot.claw.setLeftSpeed(Robot.oi.intake_trigger.getAnalog() * inMultiplier);
//    		Robot.claw.setRightSpeed(Robot.oi.intake_trigger.getAnalog() * inMultiplier * 0.4);
    	}
    	else {
    		Robot.claw.setSpeed(-Robot.oi.outtake_trigger.getAnalog() * outMultiplier);
    	}
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
