package frc.robot.commands.autonomous;

import frc.robot.Robot;
import frc.robot.Tools;

import edu.wpi.first.wpilibj.command.Command;

public class AutoGyroDriveCommand extends Command {
	//Drive speed
	private double speed;
	//Total distance to travel
	private double distance;
	private boolean finished = false;
	//whether the robot should stop when it passes a field line or not
	private boolean line_stop = false;
	//Distance left at which the drive starts to slow
	private double slow_down = 0;
	//Ending target angle
	private double target_angle = 0;
	//Minimum allowed driving speed
	private final double MIN_SPEED_LIMIT = 0.15;
	boolean timeout = true;
	
	/**
	 * <b>speed</b> controls the speed at which it drives. Will be set to MIN_SPEED if too low. DO NOT MAKE NEGATIVE. 
	 * <br><b>distance</b> is the distance to drive. Make this negative if you want to go backwards.
	 * <br><b>target_angle</b> is the angle of the line which it should be following. This is a necessary parameter because really, you should know where you're going...
	 * <br><b>line_stop</b> is whether to stop when it detects a white line underneath the sensor. Not functional right now.
	 *@author Tim
	 */
	public AutoGyroDriveCommand(double speed, double distance, double target_angle, boolean line_stop) {
		requires(Robot.drivetrain);
		this.setTimeout(Robot.AUTO_TIMEOUT);
		this.speed = speed;
		this.distance = distance;
		this.line_stop = line_stop;
		this.target_angle = target_angle;
	}
	/**
	 * <b>speed</b> controls the speed at which it drives. Will be set to MIN_SPEED if too low. DO NOT MAKE NEGATIVE. 
	 * <br><b>distance</b> is the distance to drive. Make this negative if you want to go backwards.
	 * <br><b>target_angle</b> is the angle of the line which it should be following. This is a necessary parameter because really, you should know where you're going...
	 * <br><b>line_stop</b> is whether to stop when it detects a white line underneath the sensor. Not functional right now.
	 * <br><b>slow down</b> is the distance before the target distance at which to start slowing down linearly. 0 by default to make stacking drive commands easier.
	 *@author Tim
	 */
	public AutoGyroDriveCommand(double speed, double distance, double target_angle, boolean line_stop, double slow_down) {
		this(speed, distance, target_angle, line_stop);
		this.slow_down = slow_down;
	}
	/**
	 * <b>speed</b> controls the speed at which it drives. Will be set to MIN_SPEED if too low. DO NOT MAKE NEGATIVE. 
	 * <br><b>distance</b> is the distance to drive. Make this negative if you want to go backwards.
	 * <br><b>target_angle</b> is the angle of the line which it should be following. This is a necessary parameter because really, you should know where you're going...
	 * <br><b>line_stop</b> is whether to stop when it detects a white line underneath the sensor. Not functional right now.
	 * <br><b>slow down</b> is the distance before the target distance at which to start slowing down linearly. 0 by default to make stacking drive commands easier.
	 * <br><b>timeout</b> controls whether to cancel the entire command group if this command times out. True by default.
	 *@author Tim
	 */
	public AutoGyroDriveCommand(double speed, double distance, double target_angle, boolean line_stop, double slow_down, boolean timeout) {
		this(speed, distance, target_angle, line_stop, slow_down);
		this.timeout = timeout;
	}
	/**
	 * <b>speed</b> controls the speed at which it drives. Will be set to MIN_SPEED if too low. DO NOT MAKE NEGATIVE. 
	 * <br><b>distance</b> is the distance to drive. Make this negative if you want to go backwards.
	 * <br><b>target_angle</b> is the angle of the line which it should be following. This is a necessary parameter because really, you should know where you're going...
	 * <br><b>line_stop</b> is whether to stop when it detects a white line underneath the sensor. Not functional right now.
	 * <br><b>timeout</b> controls whether to cancel the entire command group if this command times out. True by default.
	 *@author Tim
	 */
	public AutoGyroDriveCommand(double speed, double distance, double target_angle, boolean line_stop, boolean timeout) {
		this(speed, distance, target_angle, line_stop);
		this.timeout = timeout;
	}

	protected void initialize() {
		Robot.gyro.resetAngle();
		Robot.drivetrain.resetEncoderPosition();
		target_angle = Tools.closestEquivalentAngle(target_angle);
		finished = false;

	}

	protected void execute() {
		Object[] auto_drive_output;
    	if (slow_down == 0) {
    		auto_drive_output = Robot.drivetrain.getAutoDrive(speed, distance, line_stop);
    	}
    	else {
    		auto_drive_output = Robot.drivetrain.getAutoDrive(speed, distance, line_stop, slow_down);
    	}
    	
		finished = (boolean) auto_drive_output[2];
		
		double[] straight_gyro_output = Robot.gyro.getStraightOutput((double) auto_drive_output[0], (double) auto_drive_output[1], target_angle);
			
		double l = straight_gyro_output[0];
		double r = straight_gyro_output[1];
		
		if (distance > 0) {
	    	if (l < MIN_SPEED_LIMIT) {
	    		l = MIN_SPEED_LIMIT;
	    	}
	    	if (r < MIN_SPEED_LIMIT) {
	    		r = MIN_SPEED_LIMIT;
	    	}
    	}
    	else if (distance < 0) {
    		if (l > -MIN_SPEED_LIMIT) {
	    		l = -MIN_SPEED_LIMIT;
	    	}
	    	if (r > -MIN_SPEED_LIMIT) {
	    		r = -MIN_SPEED_LIMIT;
	    	}
    	}
		
		Robot.drivetrain.setPercentVoltage(l, r);
	}

	protected boolean isFinished() {
		if (isTimedOut() && timeout) {
    		Robot.autonomous_command_group.cancel();
    	}
		return finished || isTimedOut();
	}

	protected void end() {
		Robot.drivetrain.setPercentVoltage(0, 0);
	}

	protected void interrupted() {
		Robot.drivetrain.setPercentVoltage(0, 0);
	}
}
