package frc.robot.commands.instant;

import frc.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */

public class IntakeOpenCommand extends InstantCommand {
	private static int OPEN=0;
	private static int CLOSED=1;
	private static int TOGGLE=2 ;
	private int m_IntakeDesiredState=CLOSED;

    public IntakeOpenCommand() {
        super();
        //requires(Robot.claw);
        m_IntakeDesiredState = TOGGLE;
    }

    public IntakeOpenCommand(boolean openState) {
        super();
        //requires(Robot.claw);
        if (openState)
        	m_IntakeDesiredState = OPEN;
        else
        	m_IntakeDesiredState = CLOSED;
    }

    // Called once when the command executes
    protected void initialize() {
		if (m_IntakeDesiredState==TOGGLE) {
	    	if(Robot.claw.getOpen())
	    		Robot.claw.setOpen(false);
	    	else
	    		Robot.claw.setOpen(true);
		} else if (m_IntakeDesiredState==OPEN) {
			Robot.claw.setOpen(true);
			System.out.println("OPENING");
		} else {
			Robot.claw.setOpen(false);
			System.out.println("CLOSING");
		}
    }
}
