package hu.aventurin.gaming.gamepad;

@FunctionalInterface
public interface KeyAction  {
	void accept(boolean accepts);
}
