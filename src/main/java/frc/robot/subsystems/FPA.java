package frc.robot.subsystems;

import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FPA extends Subsystem { // foul prevention apparatus
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public enum Position {UP, DOWN}
	private Position position;
	
    public void initDefaultCommand() {
    }
    
    public void setDown() {
    	// clone
//    	RobotMap.fpa_r.setRaw(650);
//    	RobotMap.fpa_l.setRaw(1250);
    	
    	//real
    	RobotMap.fpa_r.setRaw(1050);
    	RobotMap.fpa_l.setRaw(1050);
    	
    	position = Position.DOWN;
    }
    
    public void setUp() {
    	// clone
//    	RobotMap.fpa_r.setRaw(1250);
//    	RobotMap.fpa_l.setRaw(650);
    	
    	// real
    	RobotMap.fpa_r.setRaw(750);
    	RobotMap.fpa_l.setRaw(650);
    	
    	position = Position.UP;
    }
    
    public Position getPosition() {
    	return position;
    }
    
    public void set(int pos) {
    	RobotMap.fpa_r.setRaw(pos);
    }
}

