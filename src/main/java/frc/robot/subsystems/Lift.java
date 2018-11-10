package frc.robot.subsystems;

import frc.robot.RobotMap;
import frc.robot.commands.LiftCommand;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lift extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	//clone heights
	final static public double SCALE_HEIGHT = 7000;//8398;//8175;//10000;//18891;//8648;//19260;
	final static public double SCALE_HEIGHT_LOWER = 5500;//6782;//6414;//8344;
	final static public double SWITCH_HEIGHT = 2500;//3416;//5107;//6860; //11000;//2654;//10251;
	final static public double PEEKING_HEIGHT = 700;//1000;
	final static public double GROUND_HEIGHT = 510;
	
	//real heights
//	final static public double SCALE_HEIGHT = 8000;//7990;//10000;//18891;//8648;//19260;
//	final static public double SCALE_HEIGHT_LOWER = 6100;//6414;//8344;
//	final static public double SWITCH_HEIGHT = 3600;//5107;//6860; //11000;//2654;//10251;
//	final static public double PEEKING_HEIGHT = 1088;
//	final static public double GROUND_HEIGHT = 0;

	
	final private double MAX_POSITION = SCALE_HEIGHT; // TODO make real values
	final private double MIN_POSITION = 0;
	
	
	private double target_position;
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
    	setDefaultCommand(new LiftCommand());
    }
    public double getPosition() {
    	return RobotMap.lift_talon_1.getSelectedSensorPosition(0);
    }
    
    public double getTargetPosition() {
    	return target_position;
    }
    
    public void resetPosition() {
    	RobotMap.lift_talon_1.setSelectedSensorPosition(0, 0, 0);
    }
    public void setTargetPosition(double position, double offset) {
    	double set_position = position + offset;
    	set_position = Math.max(set_position, MIN_POSITION);
    	set_position = Math.min(set_position, MAX_POSITION);
    	target_position = position;
    	RobotMap.lift_talon_1.set(ControlMode.Position, set_position);
    }

    public void setPercentVoltage(double p_voltage) {
    	RobotMap.lift_talon_1.set(ControlMode.PercentOutput, p_voltage);
    }
}

