package hu.zsomi.games.followgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import hu.aventurin.gaming.gamecontroller.GameController;

class GameArea extends JComponent {

	Player player;
	List<Enemy> enemies;
	Timer uiUpdateTimer;
	GameController gameController;
	long startTime;
	
	Image backgroundImage;
	 
	GameArea() throws IOException {
		backgroundImage = ImageIO.read(getClass().getResourceAsStream("/Landscape_cartoon.png"));
		startTime = System.currentTimeMillis();
		player = new Player(new Point(500, 500), 60, new Color(0xD0FFFF00, true), 4 ,this);
		player.setMousePosition(new Point(0,0));
		enemies = new ArrayList<>(Arrays.asList(
				new Enemy(new Point(560, 100), 70, Color.BLUE, 2),
				new Enemy(new Point(560, 200), 50, Color.GREEN, 1),
				new Enemy(new Point(560, 300), 60, Color.ORANGE, 1)));
		setupTimer();
		gameController = new GameController(player);
		

		addEventListeners();
	}

	private void addEventListeners() {
		
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				player.setMousePosition(e.getPoint());
			}
		});

		addKeyListener(gameController.getKeyListener());
		
	}

	void addNewEnemy() {
		// TODO adj hozzá egy új ellenséget az ellenségek listájához
		//ha lehet, random helyre, random sebességgel
		
		//dplayer.getPosition();
		Enemy e = new Enemy(new Point(160, 100), 70, Color.BLUE, 2);
		enemies.add(e);
	}

	private void setupTimer() {
		uiUpdateTimer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				player.goToDirection();
				for (Enemy enemy : enemies) {
					enemy.setTargetPoint(player.getPosition());
					if (System.currentTimeMillis() - startTime > 1000) {
						enemy.followTarget();
					}

				}

				repaint();

				Polygon playerPolygon = player.getPolygon().asAwtPolygon();
				for (Enemy enemy : enemies) {
					if (playerPolygon.intersects(enemy.getRectangle())) {
						JOptionPane.showMessageDialog(GameArea.this, "The enemy hit you!", "Game Over!",
								JOptionPane.INFORMATION_MESSAGE);
						uiUpdateTimer.stop();
					}
				}
			}
		});

		uiUpdateTimer.start();
	}

	void setAntiAliasing(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		if (g instanceof Graphics2D) {
			setAntiAliasing((Graphics2D) g);
		}

		super.paintComponent(g);
		for (Enemy enemy : enemies) {
			enemy.draw(g);
		}
		player.draw(g);

	}

}