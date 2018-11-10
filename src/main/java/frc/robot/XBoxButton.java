package frc.robot;

import edu.wpi.first.wpilibj.buttons.Button;

public class XBoxButton extends Button {
	XBoxController controller;
	int button;
	
	public XBoxButton(XBoxController controller, int button) {
		this.controller = controller;
		this.button = button;
	}
	@Override
	public boolean get() {
		return controller.getRawButton(button);
	}

}