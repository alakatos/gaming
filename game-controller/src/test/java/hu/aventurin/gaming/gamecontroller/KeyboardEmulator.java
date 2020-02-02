package hu.aventurin.gaming.gamecontroller;

import static org.mockito.Mockito.mock;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardEmulator {

	private KeyListener listener;
	private Component componentMock;
	public KeyboardEmulator(KeyListener listener) {
		this.listener = listener;
		componentMock = mock(Component.class);
	}
	
	public void pressKey(char ch) {
		listener.keyPressed( new KeyEvent(componentMock, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, 0, ch));
	}
	
	public void releaseKey(char ch) {
		listener.keyReleased(new KeyEvent(componentMock, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, 0, ch));
	}

}
