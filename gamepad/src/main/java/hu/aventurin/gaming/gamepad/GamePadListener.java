package hu.aventurin.gaming.gamepad;

public interface GamePadListener {

	void directionChanged(int gamePadId, Direction oldDirection, Direction newDirection);
	void firePressed(int gamePadId);
}
