package hu.aventurin.gaming.gamecontroller.spike;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JFrame;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class KeySpike {
	
	static void dumpEvent(KeyEvent e) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(System.out, e);
		} catch (JsonGenerationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

		Color niceOrangeColor = new Color(240, 150, 10);
		window.getContentPane().setBackground(niceOrangeColor);
		window.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				System.out.println("KeyTyped" + e);
				
			}
			
			public void keyReleased(KeyEvent e) {
				System.out.println("KeyReleased" + e);
			}
			
			public void keyPressed(KeyEvent e) {
				System.out.println("KeyPressed" + e);
				
			}
		});
		window.pack();
		window.setVisible(true);
	}
}
