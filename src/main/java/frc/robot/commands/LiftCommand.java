package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LiftCommand extends Command {
	//How far the joystick can control the lift. the total range is -multiplier to +multiplier
	final double MULTIPLIER = 500;
	
    public LiftCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.lift);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double target_position = Robot.lift.getTargetPosition();
    	if (Robot.oi.scale_height_button.get()) {
    		target_position = Lift.SCALE_HEIGHT;
    	}
    	else if (Robot.oi.scale_lower_height_button.get()) {
    		target_position = Lift.SCALE_HEIGHT_LOWER;
    	}
    	else if (Robot.oi.switch_height_button.get() && !Robot.oi.drop_override.get()) {
    		target_position = Lift.SWITCH_HEIGHT;
    	}
    	else if (Robot.oi.ground_height_button.get() && !Robot.oi.drop_override.get()) {
    		target_position = Lift.GROUND_HEIGHT;
    	}
    	else if (Math.abs(Robot.oi.xbox.getJoyLeftY()) > 0.15) {
    		target_position += -Robot.oi.xbox.getJoyLeftY()*(MULTIPLIER/5);
    		if(target_position < Lift.GROUND_HEIGHT) target_position = Lift.GROUND_HEIGHT;
    		if(target_position > Lift.SCALE_HEIGHT) target_position = Lift.SCALE_HEIGHT;
    		System.out.println(target_position + "\t" + Robot.oi.xbox.getJoyLeftY());
    	}
    	
    	if (Robot.oi.xbox.getR3()) {
    		Robot.lift.setPercentVoltage(-Robot.oi.xbox.getJoyRightY());
    	}
    	else if (Robot.oi.hold_climb_button.get()) {
    		Robot.lift.setPercentVoltage(-0.5);
    	}
    	else {
    		Robot.lift.setTargetPosition(target_position, -Robot.oi.xbox.getJoyRightY() * MULTIPLIER);
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
