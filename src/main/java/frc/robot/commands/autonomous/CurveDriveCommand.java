package frc.robot.commands.autonomous;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CurveDriveCommand extends Command {
	//Proportional PID constant
	private double k_p = 0.12;
	//Minimum allowed speed
	private final double MIN_SPEED_LIMIT = 0.15;
	//Distance left at which the drive starts to slow
	final double SLOW_DOWN_THRESHOLD = 10;
	//Angle difference at which angle is considered reached
	final double ANGLE_TOLERANCE = 4;
	//Distance from initial to target point
	private double distance;
	//Drive speed
	private double speed;
	private boolean finished;
	//Data given initiall; turning speed per inch travelled
	private double degrees_per_inch;
	//Whether this command should stop once it passes a field line
	private boolean line_stop;
	private double slow_down = 0;
	
    public CurveDriveCommand(double distance, double final_target_angle, double speed, boolean line_stop) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	this.setTimeout(Robot.AUTO_TIMEOUT);
    	this.distance = distance;
    	this.speed = speed;
    	this.degrees_per_inch = final_target_angle / distance;
    }
    
    public CurveDriveCommand(double distance, double final_target_angle, double speed, boolean line_stop, double slow_down) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(distance, final_target_angle, speed, line_stop);
    	this.slow_down = slow_down;
    }
    
    protected void initialize() {
    	Robot.drivetrain.resetEncoderPosition();
    	Robot.gyro.resetAngle();
    	
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
    	
    	double l = (double) auto_drive_output[0];
    	double r = (double) auto_drive_output[1];
    	
    	double current_angle = Robot.gyro.getAngle();
    	double target_degrees = degrees_per_inch * (Robot.drivetrain.getLeftEncoderPosition() + Robot.drivetrain.getLeftEncoderPosition()) / 2;
    	double modifier = (target_degrees - current_angle) * k_p;
		l -= modifier;
		r += modifier;
		
    	if (distance > 0) {
	    	if (l < MIN_SPEED_LIMIT) {
	    		l = MIN_SPEED_LIMIT;
	    	}
	    	if (r < MIN_SPEED_LIMIT) {
	    		r = MIN_SPEED_LIMIT;
	    	}
    	}
    	else {
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
    	if (isTimedOut()) {
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
