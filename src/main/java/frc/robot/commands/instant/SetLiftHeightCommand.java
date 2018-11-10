package frc.robot.commands.instant;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetLiftHeightCommand extends InstantCommand {
	double setpoint;
    public SetLiftHeightCommand(double setpoint) {
        super();
        requires(Robot.lift);
        this.setpoint = setpoint;
    }

    protected void initialize() {
    	Robot.lift.setTargetPosition(setpoint, 0);
    }

}
