package frc.robot.commands.autonomous;

import frc.robot.commands.TimerCommand;
import frc.robot.commands.instant.SetClawPositionCommand;
import frc.robot.commands.instant.SetLiftHeightCommand;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoScoreSwitch extends CommandGroup {
	static final double SPEED = AutoCommandGroup.MEDIUM_SPEED;
	/**
	 * Lifts lift, moves forwards, sets claw up, spits out, then backs up same distance if backup is true.
	 *@author Tim
	 */
	public AutoScoreSwitch(double distance, double angle, boolean backup, boolean claw_up, double spit_speed ) {
		addSequential(new SetLiftHeightCommand(Lift.SWITCH_HEIGHT));
		if (claw_up) {
			addSequential(new SetClawPositionCommand(Claw.Position.UP));
		}
		else {
			addSequential(new SetClawPositionCommand(Claw.Position.DOWN));
		}
		addSequential(new TimerCommand(0.5));
		addSequential(new AutoGyroDriveCommand(SPEED, distance, angle, false, false)); // Move Forward
		addSequential(new TimerCommand(0.5));
		addSequential(new SpitOutCube(spit_speed));
		if (backup) {
			addSequential(new AutoGyroDriveCommand(SPEED, -distance, angle, false)); // Back out
		}
		
	}
	
	public AutoScoreSwitch(double distance, double angle, boolean backup) {
		this(distance, angle, backup, false, 0.5);
	}
}
