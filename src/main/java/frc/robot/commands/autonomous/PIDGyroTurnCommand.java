package frc.robot.commands.autonomous;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;


public class PIDGyroTurnCommand extends Command {
	private double target_angle;
	//private boolean finished = false;
	private boolean absolute;
	final double HIGH_SPEED = 0.4;
	final double LOW_SPEED = 0.20;
	final double LOW_SPEED_LIMIT = 0.3;
	final double THRESHOLD_ANGLE = 40;
	final double TOLERANCE = 15;
	final double GYRO_MULTIPLIER = 5.475d / 360d;
	boolean clockwise = false;
	boolean intake = false;
	private LER_PID pid;
	/**
	 * Turns to an angle in degrees. Positive is CCW. If absolute is true, it turns to the angle relative to the angle it was at when enabled.
	 *@author Tim
	 */
	
	public PIDGyroTurnCommand(double target_angle, boolean absolute, boolean intake) {
		this(target_angle, absolute);
		intake = true;
	}
    public PIDGyroTurnCommand(double target_angle, boolean absolute) {
    	requires(Robot.drivetrain);
    	setTimeout(Robot.AUTO_TIMEOUT);
    	this.absolute = absolute;
    	this.target_angle = target_angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (!absolute) {
    		Robot.gyro.resetAngle();
//        	target_angle -= target_angle * GYRO_MULTIPLIER; // gyro tends to increase in error as it turns, this compensates (somewhat)
    		clockwise = target_angle < 0; // gyro tends to increase in error as it turns, this compensates (somewhat)
    	}
    	else {
    		if (Math.abs(Robot.gyro.getAbsoluteAngle() - target_angle) > Math.abs(Robot.gyro.getAbsoluteAngle() - (target_angle + 360))) {
        		target_angle += 360;
        	}
    		else if (Math.abs(Robot.gyro.getAbsoluteAngle() - target_angle) > Math.abs(Robot.gyro.getAbsoluteAngle() - (target_angle - 360))) {
    			target_angle -= 360;
    		}
//    		target_angle += (target_angle - Robot.gyro.getAbsoluteAngle()) * GYRO_MULTIPLIER; // gyro tends to increase in error as it turns, this compensates (somewhat)	
    		clockwise = Robot.gyro.getAbsoluteAngle() > target_angle;
    	}
    	pid = new LER_PID(0.01, 0.06, 0.05, 1.0);
    	pid.setMaxOutput(0.8);
    	pid.setMinOutput(-0.8);
    	pid.setDesiredValue(target_angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double set_speed = 0;
    	
    	double current_angle;
    	Robot.claw.setSpeed(0.8);
    	if (absolute) {
    		current_angle = Robot.gyro.getAbsoluteAngle();
    	}
    	else {
    		current_angle = Robot.gyro.getAngle();
    	}
    	
    	set_speed=pid.calcPID(current_angle);
    	Robot.drivetrain.setPercentVoltage(-set_speed, set_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (isTimedOut()) {
    		Robot.autonomous_command_group.cancel();
    	}
        return pid.isDone() || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.setPercentVoltage(0, 0);
    	Robot.claw.setSpeed(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.setPercentVoltage(0, 0);
    	Robot.claw.setSpeed(0.0);
    }
}
