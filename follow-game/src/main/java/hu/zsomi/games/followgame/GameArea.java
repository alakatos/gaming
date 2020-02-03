package hu.zsomi.games.followgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	GameArea() {
		startTime = System.currentTimeMillis();
		player = new Player(new Point(500, 500), 60, Color.GRAY, 20 ,this);
		enemies = new ArrayList<>(Arrays.asList(
				new Enemy(new Point(160, 100), 70, Color.BLUE, 2),
				new Enemy(new Point(160, 100), 50, Color.GREEN, 1),
				new Enemy(new Point(160, 100), 60, Color.ORANGE, 1)));
		setupTimer();
		gameController = new GameController(player);

		addEventListeners();
	}

	private void addEventListeners() {

		addKeyListener(gameController.getKeyListener());
		
	}

	void addNewEnemy() {
		// TODO adj hozzá egy új ellenséget az ellenségek listájához
		//ha lehet, random helyre, random sebességgel
		
		int randomNumberBetween50and150 = (int)(50 + Math.random()*100);
		//dplayer.getPosition();
		Enemy e = new Enemy(new Point(160, 100), 70, Color.BLUE, 3);
		enemies.add(e);
	}

	private void setupTimer() {
		uiUpdateTimer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				player.goToDirection();
				Rectangle playerRectangle = player.getRectangle();
				for (Enemy enemy : enemies) {
					enemy.setTargetPoint(player.getPosition());
					if (System.currentTimeMillis() - startTime > 1000) {
						enemy.followTarget();
					}

					if (enemy.getRectangle().intersects(playerRectangle)) {
						JOptionPane.showMessageDialog(GameArea.this, "The enemy hit you!", "Game Over!",
								JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
					}
				}

				// square2.setSize(square2.getSize()+1);
				repaint();
			}
		});

		uiUpdateTimer.start();
	}

	void setAntiAliasing(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	protected void paintComponent(Graphics g) {
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