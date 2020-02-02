package hu.aventurin.gaming.gamecontroller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameController {

	
	private Set<DirectionKey> pressedDirectionKeys = new HashSet<>();
	private Map<Character, DirectionKey> controlCharMap = new HashMap<>(); 
	
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
	
	private void handleKeyEvent(KeyEvent e) {
		Character chCapital = Character.toUpperCase(e.getKeyChar());
		DirectionKey dirKey = controlCharMap.get(chCapital);
		Direction oldDirection = currentDirection, 
				newDirection = currentDirection;
		boolean keyPressed = e.getID() == KeyEvent.KEY_PRESSED; 
		if (dirKey != null) {
			if (keyPressed) {
				pressedDirectionKeys.add(dirKey);
			} else { //KEY_RELEASED
				pressedDirectionKeys.remove(dirKey);
			}
			newDirection = createDirectionFromPressedKeys();
		}
		if (newDirection != oldDirection) {
			currentDirection = newDirection;
			player.directionChanged(oldDirection, newDirection);
		}
		if (chCapital == chFire && keyPressed) {
			player.firePressed();
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

	private KeyListener gameKeyListener;
	private char chFire;
	private GameControllerListener player;
	
	
	/** Expects the directions in UP-LEFT-DOWN-RIGHT (e.g. WASD) order. 5th param is the fire key.*/
	public GameController(GameControllerListener player, char... keyMapping) {
		if (keyMapping.length < 5) {
			throw new IllegalArgumentException("At least 5 characters expected");
		}
		if (player == null) {
			throw new IllegalArgumentException("GameControllerListener must not be null");
		}
		this.player = player;
		List<Character> charList = new ArrayList<>();
		for (char ch : keyMapping) {
			charList.add(Character.toUpperCase(ch));
		}
		
		gameKeyListener = new GameKeyListener();
		Iterator<Character> iter = charList.iterator();
		controlCharMap.put(iter.next(), DirectionKey.UP);
		controlCharMap.put(iter.next(), DirectionKey.LEFT);
		controlCharMap.put(iter.next(), DirectionKey.DOWN);
		controlCharMap.put(iter.next(), DirectionKey.RIGHT);
		chFire = iter.next();
	}

	public KeyListener getKeyListener() {
		return gameKeyListener;
	}

}
