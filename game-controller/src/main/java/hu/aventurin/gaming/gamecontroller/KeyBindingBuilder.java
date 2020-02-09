package hu.aventurin.gaming.gamecontroller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class KeyBindingBuilder {
	
	private final Map<Character, KeyAction> mapping; 
	
	public class CharMapper {
		private KeyAction func;

		public CharMapper(KeyAction func) {
			Objects.requireNonNull(func);
			this.func = func;
		}
		public KeyBindingBuilder to(char keyChar) {
			mapping.put(Character.toUpperCase(keyChar), func);
			return KeyBindingBuilder.this;
		}
	}
	
	public KeyBindingBuilder() {
		this(new HashMap<>());
	}

	public KeyBindingBuilder(Map<Character, KeyAction> existingMapping) {
		this.mapping = new HashMap<>(existingMapping);
	}

	public CharMapper bindAction(KeyAction  task) {
		return new CharMapper(task);
	}
	
	public Map<Character, KeyAction> build() {
		return new HashMap<>(mapping);
	}

}
