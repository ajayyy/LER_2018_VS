package frc.robot.commands;

//import frc.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetGear extends InstantCommand {

	public enum Position {
		HIGH(DoubleSolenoid.Value.kReverse),
		LOW(DoubleSolenoid.Value.kForward);

		private DoubleSolenoid.Value value;    

		private Position(DoubleSolenoid.Value value) {
			this.value = value;
		}
		public DoubleSolenoid.Value getValue() {
			return value;
		}
	}
	Position position;
    public SetGear(Position p) {
        super();
        position = p;
    }
    public void initialize() {
//    	RobotMap.gear_position_solenoid.set(position.getValue());
    }
   

}
