package frc.robot.commands.autonomous;

import frc.robot.Robot;
import frc.robot.Robot.AutonomousTarget;
//import frc.robot.RobotMap;
import frc.robot.commands.TimerCommand;
import frc.robot.commands.instant.SetClawPositionCommand;
import frc.robot.commands.instant.SetLiftHeightCommand;
import frc.robot.commands.instant.Stop;
import frc.robot.subsystems.Claw;
import frc.robot.subsystems.Lift;

//import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main autonomous command group for auto.<p>
 * There are 3 modes for the first score. The FMS sends information which tells us whether we need to head to the opposite side first.<br>
 * <ul><b>Switch</b> scores the switch on the side closest to the scale. (Avoid other robots who may want to score from the close side.<br>
 * <b>Scale</b> scores on the side of the scale closest to switch.<br>
 * <b>Auto Line</b> Moves the robot past the Auto Line...<p>
 * </ul>
 * After this, if there is a secondary target selected, the robot attempts to score another time by picking up a cube and putting it in the desired location.
 * <p>
 * <i>Tech Info</i><p>
 * There is a variable called <b><i>turn</i></b> which is -1 if starting on the left, and +1 if starting on the right. All of the turns we perform in auto use <b><i>turn</i></b> * angle so we only have to program for one side of the field. This way if we start on the opposite side, then the robot performs mirror image movements.
 *@author Ewan and Tim
 *
 *
 */
public class AutoCommandGroup extends CommandGroup {
	public static final double SLOW_SPEED = 0.3;
	public static final double MEDIUM_SPEED = 0.5;
	public static final double HIGH_SPEED = 0.7;
	//cohen was here
	public static final double AUTO_LINE_DISTANCE = 110;
	public static final double CURVE_SCALE_SCORE_ANGLE = 11;
	public static final double SWITCH_SCORE_ANGLE = 138;
	public static final double SWITCH_PICKUP_ANGLE = 130;
	public static final double SEC_SCALE_SCORE_ANGLE = 20;
	public static final double SEC_SCALE_SCORE_DISTANCE = 44;
	public static final double CUBE_PICKUP_ANGLE_POST_SCALE = 159;
	public static final double CUBE_PICKUP_ANGLE_POST_OPPOSITE_SCALE = 139;
	public static final double CUBE_SPIT_OUT_ANGLE_POST_SCALE = 150;
	public AutoCommandGroup() {
		
		
		AutonomousTarget auto_target = Robot.autonomous_target_chooser.getSelected();
		AutonomousTarget sec_auto_target = Robot.secondary_autonomous_target_chooser.getSelected();
		
		int start_position = Robot.position_chooser.getSelected();
		int switch_position = (Robot.field.isCloseSwitchLeft()) ? 0 : 2;
		int scale_position = (Robot.field.isScaleLeft()) ? 0 : 2;
		double direction = (start_position == 2) ? 1 : -1;
		boolean only_close = SmartDashboard.getBoolean("Only do close side", false);				
		
		addSequential(new SetClawPositionCommand(Claw.Position.UP));
		addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT));
		
		switch (auto_target) {
		case SWITCH:
			// close single switch
			if (start_position == switch_position) { 
				addSequential(new AutoCloseSide());
				addSequential(new SetClawPositionCommand(Claw.Position.UP));
				addSequential(new PIDGyroTurnCommand(direction * SWITCH_SCORE_ANGLE, false));
				addSequential(new AutoScoreSwitch(30, direction * SWITCH_SCORE_ANGLE, false, true, 1));
				// close double switch
				if (sec_auto_target == AutonomousTarget.SWITCH) { 
					addSequential(new PostSwitchCubePickup(switch_position));
					addSequential(new AutoScoreSwitch(24, direction * 150, true));
				}
				else if (sec_auto_target == AutonomousTarget.SCALE) { 
					addSequential(new PostSwitchCubePickup(switch_position));
					//close scale
					if (scale_position == switch_position) { 
						addSequential(new PIDGyroTurnCommand(direction * SEC_SCALE_SCORE_ANGLE, true));
						addSequential(new AutoScoreScale(SEC_SCALE_SCORE_DISTANCE, SEC_SCALE_SCORE_ANGLE * direction));
					}
					// opposite scale
					else { 
						addSequential(new PIDGyroTurnCommand(direction * 90, true));
						addSequential(new AutoGyroDriveCommand(1, 176, direction * 90, false, 60));
						addSequential(new PIDGyroTurnCommand(-direction * SEC_SCALE_SCORE_ANGLE, true));
						addSequential(new AutoScoreScale(40, -direction * SEC_SCALE_SCORE_ANGLE));
					}
				}
			}
			// single opposite switch
			else if (!only_close && start_position != 1){ 
				addSequential(new AutoOppositeSide(direction));
				addSequential(new SetClawPositionCommand(Claw.Position.UP));
				addSequential(new PIDGyroTurnCommand(-direction * SWITCH_SCORE_ANGLE, true));
				addSequential(new AutoScoreSwitch(19, -direction * SWITCH_SCORE_ANGLE, false, true, 1));
				// double opposite switch
				if (sec_auto_target == AutonomousTarget.SWITCH) { 
					addSequential(new PostSwitchCubePickup(switch_position));
					addSequential(new AutoScoreSwitch(12, -direction * 150, true));
					addSequential(new SetClawPositionCommand(Claw.Position.DOWN));
				}
				if (sec_auto_target == AutonomousTarget.SCALE) { 
					addSequential(new PostSwitchCubePickup(switch_position));
					// opposite switch then opposite scale
					if (scale_position == switch_position) { 
						addSequential(new PIDGyroTurnCommand(-direction * SEC_SCALE_SCORE_ANGLE, true));
						addSequential(new AutoScoreScale(SEC_SCALE_SCORE_DISTANCE, -SEC_SCALE_SCORE_ANGLE * direction));
					}
					// opposite switch then close scale
					else { 
						addSequential(new PIDGyroTurnCommand(-direction * 90, true));
						addSequential(new AutoGyroDriveCommand(1, 176, -direction * 90, false, 60));
					}
				}
			}
			else if (start_position == 1) { // Centre
				if (switch_position == 0) { // Left
					addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, 70, 45, false, 0));
					addSequential(new SetLiftHeightCommand(Lift.PEEKING_HEIGHT));
					addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 50, 0, false, 10));
					addSequential(new SpitOutCube(0.7));
					addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT));
					if (sec_auto_target == AutonomousTarget.SWITCH) {
						addSequential(new AutoGyroDriveCommand(HIGH_SPEED, -40, 0, false, 10));
						addSequential(new GyroTurnCommand(-51, true));
						addSequential(new PickupCube(-51, 40, true, false));	
						addSequential(new GyroTurnCommand(0, true));
						addSequential(new SetLiftHeightCommand(Lift.PEEKING_HEIGHT));
						addSequential(new SetClawPositionCommand(Claw.Position.UP));
						addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, 54, 0, false, 10, false));
						addSequential(new SpitOutCube(0.7));
						addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT));
						addSequential(new AutoGyroDriveCommand(HIGH_SPEED, -20, 0, false, 10));
//						addSequential(new GyroTurnCommand(-55, true));
//						addSequential(new PickupCube(-55, 40, false, false));	
//						addSequential(new AutoGyroDriveCommand(HIGH_SPEED, -30, -55, false, 10, false));
						addSequential(new GyroTurnCommand(-62, true));
						addSequential(new PickupCube(-62, 40, false, false));	
						addSequential(new AutoGyroDriveCommand(HIGH_SPEED, -30, -62, false, 10, false));
						addSequential(new GyroTurnCommand(0, true));
						addSequential(new SetLiftHeightCommand(Lift.PEEKING_HEIGHT));
						addSequential(new SetClawPositionCommand(Claw.Position.UP));
						addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, 36, 0, false, 10, false));
						addSequential(new SpitOutCube(0.7));
						addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT));
					}
				}
				else if (switch_position == 2) { //Right
					addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, 62, -35, false, 0));
					addSequential(new SetLiftHeightCommand(Lift.PEEKING_HEIGHT));
					addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 47, 0, false, 10));
					addSequential(new SpitOutCube(0.7));
					addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT));
					if (sec_auto_target == AutonomousTarget.SWITCH) {
						addSequential(new AutoGyroDriveCommand(HIGH_SPEED, -44, 0, false, 10));
						addSequential(new GyroTurnCommand(46, true));
						addSequential(new PickupCube(46, 40, true, false));	
						addSequential(new GyroTurnCommand(0, true));
						addSequential(new SetLiftHeightCommand(Lift.PEEKING_HEIGHT));
						addSequential(new SetClawPositionCommand(Claw.Position.UP));
						addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, 58, 0, false, 10, false));
						addSequential(new SpitOutCube(0.7));
						addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT));
						addSequential(new AutoGyroDriveCommand(HIGH_SPEED, -25, 0, false, 10));
						addSequential(new GyroTurnCommand(55, true));
						addSequential(new PickupCube(55, 40, false, false));	
						addSequential(new AutoGyroDriveCommand(HIGH_SPEED, -30, 55, false, 10, false));
						addSequential(new GyroTurnCommand(0, true));
						addSequential(new SetClawPositionCommand(Claw.Position.UP));
						addSequential(new SetLiftHeightCommand(Lift.PEEKING_HEIGHT));
						addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, 36, 0, false, 10, false));
						addSequential(new SpitOutCube(0.7));
						addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT));
					}
				}
			}
			else {
				addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, AUTO_LINE_DISTANCE, 0, false, 50));
			}
			addSequential(new SetLiftHeightCommand(Lift.GROUND_HEIGHT));
			break;
		case SCALE:
			if (start_position != 1) {
				if (start_position == scale_position) {
					addSequential(new SetLiftHeightCommand(Lift.SCALE_HEIGHT)); // Move lift up
					addSequential(new SetClawPositionCommand(Claw.Position.UP));
					System.out.println("Driving");
					addSequential(new CurveDriveCommand(222, direction * CURVE_SCALE_SCORE_ANGLE, 1, false, 100));
					System.out.println("Step 2");
					addSequential(new AutoScoreScale(26, direction * SEC_SCALE_SCORE_ANGLE, direction * CURVE_SCALE_SCORE_ANGLE));
					if (sec_auto_target == AutonomousTarget.SWITCH) {
						if (scale_position == switch_position) {
							addSequential(new SetClawPositionCommand(Claw.Position.DOWN));
							addSequential(new WaitForLift());
							addSequential(new TimerCommand(0.5));
							addSequential(new PIDGyroTurnCommand(direction * CUBE_PICKUP_ANGLE_POST_SCALE, true));
	//						addSequential(new PostScaleCubePickup(switch_position));
							addSequential(new PickupCube(CUBE_PICKUP_ANGLE_POST_SCALE * direction, 20, false, false));
							addSequential(new AutoScoreSwitch(5, direction * CUBE_SPIT_OUT_ANGLE_POST_SCALE, false));
						}
					}
					else if (sec_auto_target == AutonomousTarget.SCALE) {
						addSequential(new SetClawPositionCommand(Claw.Position.DOWN));
						addSequential(new PIDGyroTurnCommand(direction * CUBE_PICKUP_ANGLE_POST_SCALE, true));
	//					addSequential(new PostScaleCubePickup(switch_position));
						addSequential(new PickupCube(CUBE_PICKUP_ANGLE_POST_SCALE * direction, 20, false, false));
						addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, -16, direction * CUBE_PICKUP_ANGLE_POST_SCALE, false));
						addSequential(new SetLiftHeightCommand(Lift.SCALE_HEIGHT)); // Move lift up
						addSequential(new PIDGyroTurnCommand(direction * SEC_SCALE_SCORE_ANGLE, true, true));
						addSequential(new AutoScoreScale(26, direction * SEC_SCALE_SCORE_ANGLE));
					}
				}
				else if (!only_close){
					addSequential(new AutoGyroDriveCommand(1, 205, 0, false, 60));
					addSequential(new GyroTurnCommand(direction * 90, true));
					addSequential(new AutoGyroDriveCommand(1, 195, direction * 90, false, 60));
					addSequential(new SetLiftHeightCommand(Lift.SCALE_HEIGHT)); // Move lift up
					addSequential(new PIDGyroTurnCommand(-direction * 20, true));
					addSequential(new AutoScoreScale(32, -direction * 20));	
					if (sec_auto_target == AutonomousTarget.SCALE) {
						addSequential(new SetClawPositionCommand(Claw.Position.DOWN));
						addSequential(new PIDGyroTurnCommand(-CUBE_PICKUP_ANGLE_POST_OPPOSITE_SCALE * direction, true));
						addSequential(new PickupCube(-CUBE_PICKUP_ANGLE_POST_OPPOSITE_SCALE * direction, 20, true, false));
						addSequential(new SetLiftHeightCommand(Lift.SCALE_HEIGHT)); // Move lift up
						addSequential(new PIDGyroTurnCommand(-direction * 20, true));
						addSequential(new AutoScoreScale(32, -direction * 20));	
					}
				}
				else {
					addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, AUTO_LINE_DISTANCE, 0, false, 50));
				}
				
			}
			break;
		case AUTO_LINE:
			addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, AUTO_LINE_DISTANCE, 0, false, 50));
			break;
		case AUTO_LINE_SIMPLE:
			addSequential(new TimerCommand(5.0));
			addSequential(new AutoSimpleDriveCommand(SLOW_SPEED, SLOW_SPEED, 5.0));
			addSequential(new TimerCommand(10.0));
			break;
		case SWITCH_SIMPLE:
			switch (start_position)
			{
				case 1: // center
					if (switch_position==0)
					{
						addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, 65, 45, false, 0));
						addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 55, 0, false, 10));
						addSequential(new SpitOutCube(0.5));
						if (sec_auto_target == AutonomousTarget.SWITCH) {
							addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, -60, -10, false, 10));
							addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 10, -50, false, 10));
							addSequential(new PickupCube(-50.0, 22.0, false, false));
							addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 0, -50, false, 10));							
							addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 55, 0, false, 10));
							addSequential(new SpitOutCube(0.5));
						}
					} else {
						addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, 55, -45, false, 0));
						addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 55, -5, false, 10));
						addSequential(new SpitOutCube(0.5));
						if (sec_auto_target == AutonomousTarget.SWITCH) {
							addSequential(new AutoGyroDriveCommand(MEDIUM_SPEED, -55, 10, false, 10));
							addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 10, 45, false, 10));
							addSequential(new PickupCube(45.0, 24.0, false, false));
							addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 0, 50, false, 10));							
							addSequential(new AutoGyroDriveCommand(SLOW_SPEED, 55, 0, false, 10));
							addSequential(new SpitOutCube(0.5));
						}
						addSequential(new TimerCommand(10.0));
					}
					break;
				case 0:
				case 2:
					if (switch_position==start_position) // go straight and score
					{
						addSequential(new AutoSimpleDriveCommand(SLOW_SPEED, SLOW_SPEED, 3.0));
						addSequential(new SpitOutCube(0.5));
						
					} else { // just cross the line
						addSequential(new AutoSimpleDriveCommand(SLOW_SPEED, SLOW_SPEED, 3.0));
						addSequential(new TimerCommand(10.0));
					}
					break;
				default:
					break;
			}
			break;
		default:
			break;
		}
		addSequential(new Stop());
	}
}
