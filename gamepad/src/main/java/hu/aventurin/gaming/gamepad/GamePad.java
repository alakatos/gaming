package hu.aventurin.gaming.gamepad;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GamePad {

	
	private Set<DirectionKey> pressedDirectionKeys = new HashSet<>();
	private KeyListener gameKeyListener;
	private GamePadListener player;
	private Map<Character, KeyAction> keyMapping;
	private final int id;
	
	public class GameKeyListener implements KeyListener {

		public void keyTyped(KeyEvent e) {

		}

		public void keyPressed(KeyEvent e) {
			handleKeyEvent(e);
		}

		public void keyReleased(KeyEvent e) {
			handleKeyEvent(e);
		}
	}
	
	private void goLeft(Boolean keyPressed) { 
		maintainPressedKeys(keyPressed, DirectionKey.LEFT);
	};
	private void goUp(Boolean keyPressed) { 
		maintainPressedKeys(keyPressed, DirectionKey.UP);
	};
	private void goDown(Boolean keyPressed) { 
		maintainPressedKeys(keyPressed, DirectionKey.DOWN);
	};
	private void goRight(Boolean keyPressed) { 
		maintainPressedKeys(keyPressed, DirectionKey.RIGHT);
	};
	
	public final Map<Character, KeyAction> createDefaultMapping() {
		return createCustom5Mapping('w', 'a', 's', 'd', ' ');
	}
	
	public final Map<Character, KeyAction> createCustom5Mapping(char up, char left, char down, char right, char fire) {
		return new KeyBindingBuilder().
				bindAction(this::goUp).to(up).
				bindAction(this::goLeft).to(left).
				bindAction(this::goDown).to(down).
				bindAction(this::goRight).to(right).
				bindAction(this::callFirePressed).to(fire).
				build();
	}
	
	private void callFirePressed(Boolean keyPressed) {
		if (keyPressed) {
			player.firePressed(id);
		}
	}

	
	private void maintainPressedKeys(boolean keyPressed, DirectionKey directionKey) {
		if (keyPressed) {
			pressedDirectionKeys.add(directionKey);
		} else {
		    pressedDirectionKeys.remove(directionKey);
		}
	}
	
	private void handleKeyEvent(KeyEvent e) {
		if (keyMapping == null || keyMapping.isEmpty()) {
			return;
			//TODO log
		}
		Character chCapital = Character.toUpperCase(e.getKeyChar());
		KeyAction action = keyMapping.get(chCapital);
		boolean keyPressed = e.getID() == KeyEvent.KEY_PRESSED; 
		if (action != null) {
			action.accept(keyPressed);
		}
		
		Direction oldDirection = currentDirection, 
				newDirection = currentDirection;
		newDirection = createDirectionFromPressedKeys();

		if (newDirection != oldDirection) {
			currentDirection = newDirection;
			player.directionChanged(id, oldDirection, newDirection);
		}
		
	}

	Direction currentDirection = Direction.NONE;
	
	private Direction createDirectionFromPressedKeys() {
		int keyDownStatus = 0;
		for (DirectionKey directionKey : pressedDirectionKeys) {
			keyDownStatus |= directionKey.getDirectionValue();
		}
		DirectionKey verticalKey = DirectionKey.fromStatusValue(keyDownStatus, DirectionKey.VERTICAL_MASK);
		DirectionKey horizontalKey = DirectionKey.fromStatusValue(keyDownStatus, DirectionKey.HORIZONTAL_MASK);
		return Direction.fromDirectionKeys(horizontalKey, verticalKey);
	}
	
	/** Expects the directions in UP-LEFT-DOWN-RIGHT (e.g. WASD) order. 5th param is the fire key.*/
	public GamePad(GamePadListener player, int id) {
		if (player == null) {
			throw new IllegalArgumentException("GameControllerListener must not be null");
		}
		this.player = player;
		this.id = id;
		gameKeyListener = new GameKeyListener();
		setKeyMapping(createDefaultMapping());
	}

	public void setKeyMapping(Map<Character, KeyAction> keyMapping) {
		this.keyMapping = keyMapping;
	}
	
	public Map<Character, KeyAction> getKeyMapping() {
		return Collections.unmodifiableMap(keyMapping);
	}
	
	public KeyListener getKeyListener() {
		return gameKeyListener;
	}

}
