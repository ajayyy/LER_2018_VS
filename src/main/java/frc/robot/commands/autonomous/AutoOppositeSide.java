package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Brings the robot from beginning point to Point 'C'
 * 
 * @author Ewan
 */
public class AutoOppositeSide extends CommandGroup {
	public AutoOppositeSide(double turn) {
		addSequential(new AutoCloseSide());
		addSequential(new GyroTurnCommand(turn * 90, true));
		addSequential(new AutoGyroDriveCommand(1, 184, turn * 90, false, 60));
	}
}
