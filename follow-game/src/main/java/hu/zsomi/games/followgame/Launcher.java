package hu.zsomi.games.followgame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

class Launcher {

	public static void main(String[] args) {
		JFrame window = new JFrame("SpagiProgi :)");
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		final GameArea gameArea = new GameArea();
		gameArea.setPreferredSize(new Dimension(1920, 1000));

		window.getContentPane().add(gameArea, BorderLayout.CENTER);
		gameArea.setFocusable(true);
		Color NiceOrangeColor = new Color(240, 150, 10);
		window.getContentPane().setBackground(NiceOrangeColor);
		window.pack();
		window.setVisible(true);
	}
}
