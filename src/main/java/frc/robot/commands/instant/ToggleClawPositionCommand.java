package frc.robot.commands.instant;

import frc.robot.Robot;
import frc.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ToggleClawPositionCommand extends InstantCommand {
	
    public ToggleClawPositionCommand() {
        super();
        requires(Robot.claw);
    }

    // Called once when the command executes
    protected void initialize() {
    	if (Robot.claw.getPosition() == Claw.Position.DOWN) {
    		Robot.claw.setPosition(Claw.Position.UP);
    	}
    	else if (Robot.claw.getPosition() == Claw.Position.UP) {
    		Robot.claw.setPosition(Claw.Position.DOWN);
    	}
    }

}
