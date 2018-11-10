package frc.robot.commands.instant;

import frc.robot.Robot;
import frc.robot.subsystems.Claw;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetClawPositionCommand extends InstantCommand {
	Claw.Position position;
    public SetClawPositionCommand(Claw.Position position) {
        super();
        requires(Robot.claw);
        this.position = position;
    }

    protected void initialize() {
    	Robot.claw.setPosition(position);
    }

}
