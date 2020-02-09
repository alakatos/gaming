package hu.aventurin.gaming.gamepad;

enum DirectionKey {
	UP(3),
	DOWN(2),
	LEFT(4),
	RIGHT(12);
	
	static int VERTICAL_MASK=3;
	static int HORIZONTAL_MASK=12;

	private int directionValue;
	private DirectionKey(int directionValue) {
		this.directionValue = directionValue;
	}
	
	int getDirectionValue() {
		return directionValue;
	}
	
	static DirectionKey fromStatusValue(int statusValue, int axisMask) {
		for (DirectionKey dk : values()) {
			if ((statusValue & axisMask) == dk.directionValue) {
				return dk;
			}
		}
		return null;
	}
	
}
