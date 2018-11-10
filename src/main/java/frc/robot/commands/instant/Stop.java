package frc.robot.commands.instant;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class Stop extends InstantCommand {

    public Stop() {
        super();
        requires(Robot.drivetrain);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.drivetrain.setPercentVoltage(0, 0);
    }

}
