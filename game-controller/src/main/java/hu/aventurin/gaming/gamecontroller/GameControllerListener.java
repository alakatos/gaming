package hu.aventurin.gaming.gamecontroller;

public interface GameControllerListener {

	void directionChanged(Direction oldDirection, Direction newDirection);
	void firePressed();
}
