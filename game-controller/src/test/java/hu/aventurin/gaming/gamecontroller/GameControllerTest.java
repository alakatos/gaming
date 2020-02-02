package hu.aventurin.gaming.gamecontroller;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Map;

import static org.mockito.Mockito.times;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GameControllerTest {

	@Mock
	GameControllerListener playerSpy;
	@Mock
	KeyAction fireActionMock;
	GameController gc;
	KeyboardEmulator keyboardEmulator;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		gc = new GameController(playerSpy);
		keyboardEmulator = new KeyboardEmulator(gc.getKeyListener());
	}
	
	@Test
	public void testGameControllerNotifiesPlayerOnSateChange() {
		keyboardEmulator.pressKey('w');
		keyboardEmulator.pressKey(' ');
		verify(playerSpy).directionChanged(any(Direction.class), any(Direction.class));
		verify(playerSpy).firePressed();
	}

	@Test
	public void testGameControllerMapsDirectionsCorrectly() {
		keyboardEmulator.pressKey('w');
		keyboardEmulator.releaseKey('w');
		keyboardEmulator.pressKey('a');
		keyboardEmulator.releaseKey('a');
		keyboardEmulator.pressKey('s');
		keyboardEmulator.releaseKey('s');
		keyboardEmulator.pressKey('d');
		keyboardEmulator.releaseKey('d');
		ArgumentCaptor<Direction> oldDirectionCaptor = ArgumentCaptor.forClass(Direction.class);
		ArgumentCaptor<Direction> newDirectionCaptor = ArgumentCaptor.forClass(Direction.class);
		verify(playerSpy, times(8)).directionChanged(oldDirectionCaptor.capture(), newDirectionCaptor.capture());
		
		assertThat(oldDirectionCaptor.getAllValues(), equalTo(Arrays.asList(
				Direction.NONE,
				Direction.N,
				Direction.NONE,
				Direction.W,
				Direction.NONE,
				Direction.S,
				Direction.NONE,
				Direction.E
				)));
		
		assertThat(newDirectionCaptor.getAllValues(), equalTo(Arrays.asList(
				Direction.N,
				Direction.NONE,
				Direction.W,
				Direction.NONE,
				Direction.S,
				Direction.NONE,
				Direction.E,
				Direction.NONE
				)));
	}

	@Test
	public void testGameControllerMapsDoubleDirectionsCorrectly() {
		keyboardEmulator.pressKey('w');
		keyboardEmulator.pressKey('a');
		keyboardEmulator.releaseKey('a');
		keyboardEmulator.pressKey('d');
		keyboardEmulator.releaseKey('d');
		keyboardEmulator.releaseKey('w');
		ArgumentCaptor<Direction> newDirectionCaptor = ArgumentCaptor.forClass(Direction.class);
		verify(playerSpy, times(6)).directionChanged(any(Direction.class), newDirectionCaptor.capture());
		
		assertThat(newDirectionCaptor.getAllValues(), equalTo(Arrays.asList(
				Direction.N,
				Direction.NW,
				Direction.N,
				Direction.NE,
				Direction.N,
				Direction.NONE
				)));
		
	}

 }
