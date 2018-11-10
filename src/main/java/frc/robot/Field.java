package frc.robot;

public class Field {
	
	private String placements;
	public Field(String field_data) {
		placements = field_data;
	}
	public boolean isFarSwitchLeft() {
		try {
			return Character.toUpperCase(placements.charAt(2)) == 'L';
		}
		catch (Exception e) {
			return false;
		}
	}
	public boolean isScaleLeft() {
		try {
			return Character.toUpperCase(placements.charAt(1)) == 'L';
		}
		catch (Exception e) {
			return false;
		}
	}
	public boolean isCloseSwitchLeft() {
		try {
			return Character.toUpperCase(placements.charAt(0)) == 'L';
		}
		catch (Exception e) {
			return false;
		}
	}
}
