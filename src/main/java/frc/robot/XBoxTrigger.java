package frc.robot;

import edu.wpi.first.wpilibj.buttons.Button;

public class XBoxTrigger extends Button {
	XBoxController controller;
	int button;
	boolean left;
	
	public XBoxTrigger(XBoxController controller, boolean left) {
		this.controller = controller;
		this.left = left;
	}
	@Override
	public boolean get() {
		if (left) {
			return controller.getTriggerLeft() > 0.1;
		}
		else {
			return controller.getTriggerRight() > 0.1;
		}
	}
	
	public double getAnalog() {
		if (left) {
			return controller.getTriggerLeft();
		}
		else {
			return controller.getTriggerRight();
		}
	}

}
