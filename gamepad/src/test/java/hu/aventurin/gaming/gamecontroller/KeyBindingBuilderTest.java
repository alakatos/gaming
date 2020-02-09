package hu.aventurin.gaming.gamecontroller;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Map;

import org.junit.Test;

import hu.aventurin.gaming.gamepad.KeyAction;
import hu.aventurin.gaming.gamepad.KeyBindingBuilder;

public class KeyBindingBuilderTest {
	

	@Test
	public void testIt() {
		KeyAction actionMock = mock(KeyAction.class);
		KeyBindingBuilder builder = new KeyBindingBuilder();
		Map<Character, KeyAction> mapping = builder.
			bindAction(actionMock).to('f').build();
		
		assertThat(mapping.get('F'), sameInstance(actionMock));
	}
}
