package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.FPA;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class FPAToggleCommand extends InstantCommand {

    public FPAToggleCommand() {
    	super();
        requires(Robot.fpa);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.fpa.getPosition() == FPA.Position.UP) {
    		Robot.fpa.setDown();
    	}
    	else if (Robot.fpa.getPosition() == FPA.Position.DOWN) {
    		Robot.fpa.setUp();
    	}
    }
}
