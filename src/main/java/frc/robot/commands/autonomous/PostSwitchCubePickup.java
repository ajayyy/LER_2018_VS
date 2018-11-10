package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;


public class PostSwitchCubePickup extends CommandGroup {
	/**
	 * Backs up, moves forwards while sucking up, and moves backwards again using fixed distances and angles.
	 *@author Tim
	 *
	 *
	 */
    public PostSwitchCubePickup(int switch_position) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	double pickup_angle = AutoCommandGroup.SWITCH_PICKUP_ANGLE;
    	double drive_angle = AutoCommandGroup.SWITCH_PICKUP_ANGLE;
    	
    	double speed = AutoCommandGroup.MEDIUM_SPEED;
    	
    	if (switch_position == 0) {
			drive_angle *= -1;
			pickup_angle *= -1;
    	}
    	
    	addSequential(new AutoGyroDriveCommand(speed, -20, drive_angle, false));
		addSequential(new PickupCube(pickup_angle, 27, false));
		addSequential(new AutoGyroDriveCommand(speed, -8, drive_angle, false));
    	
    }
}
