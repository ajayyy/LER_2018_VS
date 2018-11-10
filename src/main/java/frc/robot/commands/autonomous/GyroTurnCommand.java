package frc.robot.commands.autonomous;

import frc.robot.Robot;
import frc.robot.Tools;

import edu.wpi.first.wpilibj.command.Command;


public class GyroTurnCommand extends Command {
	private double target_angle;
	private boolean finished = false;
	private boolean absolute;
	final double HIGH_SPEED = 0.4;
	final double LOW_SPEED = 0.20;
	final double LOW_SPEED_LIMIT = 0.3;
	final double THRESHOLD_ANGLE = 40;
	final double TOLERANCE = 15;
	final double GYRO_MULTIPLIER = 5.475d / 360d;
	boolean clockwise = false;
	/**
	 * Turns to an angle in degrees. Positive is CCW. If absolute is true, it turns to the angle relative to the angle it was at when enabled.
	 *@author Tim
	 */
    public GyroTurnCommand(double target_angle, boolean absolute) {
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
    		target_angle = Tools.closestEquivalentAngle(target_angle);
//    		target_angle += (target_angle - Robot.gyro.getAbsoluteAngle()) * GYRO_MULTIPLIER; // gyro tends to increase in error as it turns, this compensates (somewhat)	
    		clockwise = Robot.gyro.getAbsoluteAngle() > target_angle;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double l = 0;
    	double r = 0;
    	double calculated_speed = 0;
    	
    	double difference;

    	if (absolute) {
    		difference = target_angle - Robot.gyro.getAbsoluteAngle();
    	}
    	else {
    		difference = target_angle - Robot.gyro.getAngle();
    	}
    	//If the angle difference is above THRESHOLD_ANGLE, turn at HIGH_SPEED
    	if (!clockwise && difference > THRESHOLD_ANGLE) {
    		calculated_speed = HIGH_SPEED;
    	}
    	else if (clockwise && difference < -THRESHOLD_ANGLE) {
    		calculated_speed = -HIGH_SPEED;
    	}
    	//If the angle difference is below THRESHOLD_ANGLE, turn at LOW SPEED, 
    	//decreasing as it approaches the target
    	else if (Math.abs(difference) > TOLERANCE && Math.abs(difference) <= THRESHOLD_ANGLE) {
    		calculated_speed = (difference / THRESHOLD_ANGLE) * LOW_SPEED;
    		//If the speed is lower than the allowed limit, set it to the limit
    		if(Math.abs(calculated_speed) < LOW_SPEED_LIMIT) {
    			calculated_speed = LOW_SPEED_LIMIT * Math.signum(calculated_speed);
    		}
    	}
    	//If the angle difference is below the tolerance then the turn is finished and the command exits
    	else {
    		finished = true;
    	}
    	r = calculated_speed;
    	l = -calculated_speed;
    	Robot.drivetrain.setPercentVoltage(l, r);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (isTimedOut()) {
    		Robot.autonomous_command_group.cancel();
    	}
        return finished || isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.setPercentVoltage(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.setPercentVoltage(0, 0);
    }
}
