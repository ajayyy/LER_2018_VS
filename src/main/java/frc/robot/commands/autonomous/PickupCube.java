package frc.robot.commands.autonomous;

import frc.robot.commands.TimerCommand;
import frc.robot.commands.instant.IntakeOpenCommand;
import frc.robot.commands.instant.SetClawPositionCommand;
import frc.robot.commands.instant.SetLiftHeightCommand;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PickupCube extends CommandGroup {

	public PickupCube(double turn, double distance, boolean backup) {
		this(turn, distance, backup, true);
	}
	
    public PickupCube(double turn, double distance, boolean backup, boolean wait_claw_down) {
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
    	final double speed = AutoCommandGroup.MEDIUM_SPEED;
		addParallel(new IntakeOpenCommand(true));//////////////
		System.out.println("OPEN DAMN YOU!");
    	addSequential(new SetClawPositionCommand(Claw.Position.DOWN));
		addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT));
		if (wait_claw_down)
			addSequential(new TimerCommand(0.5));
		addParallel(new IntakeCube(1, 2));
		addSequential(new AutoGyroDriveCommand(speed, distance, turn, false, false));
		addParallel(new IntakeOpenCommand(false));///////////
		System.out.println("CLOSE DAMN YOU!");
		addSequential(new TimerCommand(0.5));
		
		if (backup) {
			addSequential(new AutoGyroDriveCommand(speed, -distance, turn, false));
		}
    }
}
