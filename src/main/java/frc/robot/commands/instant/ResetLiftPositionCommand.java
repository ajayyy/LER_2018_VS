package frc.robot.commands.instant;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetLiftPositionCommand extends InstantCommand {

    public ResetLiftPositionCommand() {
        super();
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.lift.resetPosition();
    }

}
