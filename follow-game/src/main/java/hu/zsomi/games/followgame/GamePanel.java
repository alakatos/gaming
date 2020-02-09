package hu.zsomi.games.followgame;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import hu.zsomi.games.followgame.controller.GameArea;

class GamePanel extends JComponent {

	Image backgroundImage;
	GameArea controller; 
	
	GamePanel() throws IOException {
		backgroundImage = ImageIO.read(getClass().getResourceAsStream("/Landscape_cartoon.png"));
		controller = new GameArea(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		
		controller.getRenderer().render(g);
	}


}