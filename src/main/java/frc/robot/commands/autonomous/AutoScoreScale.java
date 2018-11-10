package frc.robot.commands.autonomous;

import frc.robot.commands.instant.IntakeOpenCommand;
import frc.robot.commands.instant.SetClawPositionCommand;
import frc.robot.commands.instant.SetLiftHeightCommand;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoScoreScale extends CommandGroup {
	static final double SPEED = AutoCommandGroup.SLOW_SPEED;
	static final double HEIGHT = Lift.SCALE_HEIGHT;

	public AutoScoreScale(double distance, double angle, double backup_angle) {
		//Check if we can set the drive/lift as parallel commands to save time
			//	If it happened fast enough, could lifting and driving forward at the same time possibly hinder the robot's movement?
		addSequential(new SetLiftHeightCommand(HEIGHT)); // Move lift up
		addSequential(new SetClawPositionCommand(Claw.Position.UP));
		addSequential(new WaitForLift());
		addSequential(new AutoGyroDriveCommand(SPEED, distance, angle, false)); // Move Forward
		addSequential(new SpitOutCube(0.4));
		addSequential(new IntakeOpenCommand(true));
		addSequential(new AutoGyroDriveCommand(SPEED, -distance, backup_angle, false)); // Back out
		addParallel(new IntakeOpenCommand(false));
		addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT)); // Move lift back down
		addSequential(new WaitForLift());
	}
	
	public AutoScoreScale(double distance, double angle) {
		this(distance, angle, angle);
	}
}
