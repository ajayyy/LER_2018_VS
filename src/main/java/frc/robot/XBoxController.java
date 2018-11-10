package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class XBoxController extends Joystick {
	// buttons
	public static final int XBOX_A = 1;
	public static final int XBOX_B = 2;
	public static final int XBOX_X = 3;
	public static final int XBOX_Y = 4;
	public static final int XBOX_LB = 5;
	public static final int XBOX_RB = 6;
	public static final int XBOX_BACK = 7;
	public static final int XBOX_START = 8;
	public static final int XBOX_L3 = 9;
	public static final int XBOX_R3 = 10;
	// axes
	public static final int XBOX_LEFT_X = 0;
	public static final int XBOX_LEFT_Y = 1;
	public static final int XBOX_LEFT_TRIGGER = 2;
	public static final int XBOX_RIGHT_TRIGGER = 3;
	public static final int XBOX_RIGHT_X = 4;
	public static final int XBOX_RIGHT_Y = 5;
	// dpad angles
	public static final int XBOX_DPAD_UP_ANGLE = 0;
	public static final int XBOX_DPAD_RIGHT_ANGLE = 90;
	public static final int XBOX_DPAD_DOWN_ANGLE = 180;
	public static final int XBOX_DPAD_LEFT_ANGLE = 270;	

	public XBoxController(int port) {
		super(port);
	}
	
	public boolean getL3() {
		return this.getRawButton(XBOX_L3);
	}
	
	public boolean getR3() {
		return this.getRawButton(XBOX_R3);
	}
	
	public boolean get(int t) {
		return this.getRawButton(t);
	}
	
	public boolean getButtonA() {
        return this.getRawButton(XBOX_A);
    }
    
    public boolean getButtonB() {
        return this.getRawButton(XBOX_B);
    }
    
    public boolean getButtonX() {
        return this.getRawButton(XBOX_X);
    }
    
    public boolean getButtonY() {
        return this.getRawButton(XBOX_Y);
    }
    
    public boolean getBumperL() {
        return this.getRawButton(XBOX_LB);
    }
    
    public boolean getBumperR() {
        return this.getRawButton(XBOX_RB);
    }
    
    public boolean getButtonStart() {
        return this.getRawButton(XBOX_START);
    }
    
    public boolean getButtonBack() {
        return this.getRawButton(XBOX_BACK);
    }
    
    public double getJoyLeftX() {
        return this.getRawAxis(XBOX_LEFT_X);
    }
    
    public double getJoyLeftY() {
        return this.getRawAxis(XBOX_LEFT_Y);
    }
    
    public double getJoyRightX() {
        return this.getRawAxis(XBOX_RIGHT_X);
    }
    
    public double getJoyRightY() {
        return this.getRawAxis(XBOX_RIGHT_Y);
    }
    
    public double getTriggerRight() {
        return this.getRawAxis(XBOX_RIGHT_TRIGGER);
    }
    
    public double getTriggerLeft() {
        return this.getRawAxis(XBOX_LEFT_TRIGGER);
    }   
    
    public boolean getDpadUp() {
    	return this.getPOV() == XBOX_DPAD_UP_ANGLE;
    }
    
    public boolean getDpadRight() {
    	return this.getPOV() == XBOX_DPAD_RIGHT_ANGLE;
    }
    
    public boolean getDpadDown() {
    	return this.getPOV() == XBOX_DPAD_DOWN_ANGLE;
    }
    
    public boolean getDpadLeft() {
    	return this.getPOV() == XBOX_DPAD_LEFT_ANGLE;
    }
}
