package hu.aventurin.gaming.gamecontroller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class KeyBindingBuilder {
	
	private Map<Character, KeyAction> mapping = new HashMap<>(); 
	private GameController gc;
	
	class CharMapper {
		private KeyAction func;

		public CharMapper(KeyAction func) {
			Objects.requireNonNull(func);
			this.func = func;
		}
		KeyBindingBuilder to(char keyChar) {
			mapping.put(Character.toUpperCase(keyChar), func);
			return KeyBindingBuilder.this;
		}
	}
	
	public KeyBindingBuilder() {
	}

	public CharMapper bindAction(KeyAction  task) {
		return new CharMapper(task);
	}
	
	public Map<Character, KeyAction> build() {
		return new HashMap<>(mapping);
	}

}
