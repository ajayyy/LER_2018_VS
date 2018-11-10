package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCloseSide extends CommandGroup {

    public AutoCloseSide() {
    	addSequential(new AutoGyroDriveCommand(1, 200, 0, false, 60));
    }
}
