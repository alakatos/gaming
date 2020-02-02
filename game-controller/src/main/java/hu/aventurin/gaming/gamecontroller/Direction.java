package hu.aventurin.gaming.gamecontroller;

public enum Direction {
	E(0), NE(45), N(90), NW(135), W(180), SW(225), S(270), SE(315), NONE(-1);

	int angle;

	private Direction(int angle) {
		this.angle = angle;
	}

	public boolean isUp() {
		return angle > 0 && angle < 180;
	}

	public boolean isDown() {
		return angle > 180 && angle < 360;
	}

	public boolean isLeft() {
		return angle > 90 && angle < 270;
	}

	public boolean isRight() {
		return angle > 270 || (angle < 90 && angle > 0);
	}
	
	public int getAngle() {
		return angle;
	}
	
	static Direction fromDirectionKeys(DirectionKey horizontalKey, DirectionKey verticalKey) {
		if (horizontalKey == null && verticalKey == null) {
			return Direction.NONE;
		}
		if (horizontalKey == null) {
			switch (verticalKey) {
			case UP:
				return Direction.N;
			case DOWN:
				return Direction.S;
			default:
				break;
			}
		} else if (verticalKey == null) {
			switch (horizontalKey) {
			case LEFT:
				return Direction.W;
			case RIGHT:
				return Direction.E;
			default:
				break;
			}

		} else {
			switch (verticalKey) {
			case UP:
				switch (horizontalKey) {
				case RIGHT:
					return Direction.NE;
				case LEFT:
					return Direction.NW;
				default:
					break;
				}
				break;
			case DOWN:
				switch (horizontalKey) {
				case RIGHT:
					return Direction.SE;
				case LEFT:
					return Direction.SW;
				default:
					break;
				}
			default:
				break;
			}
		}
		return Direction.NONE;
	}
}
