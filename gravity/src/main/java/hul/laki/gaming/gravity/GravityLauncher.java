package hul.laki.gaming.gravity;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JFrame;

class GravityLauncher {

	public static void main(String[] args) throws IOException {
		JFrame window = new JFrame("Spinning shapes");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JComponent gameArea = new GravityPanel();
		gameArea.setPreferredSize(new Dimension(800, 600));

		window.getContentPane().add(gameArea, BorderLayout.CENTER);
		gameArea.setFocusable(true);
		window.getContentPane().setBackground(Color.BLACK);
		window.pack();
		window.setVisible(true);
	}
}
