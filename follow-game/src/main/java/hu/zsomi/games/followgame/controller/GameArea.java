package hu.zsomi.games.followgame.controller;

import java.awt.Color;
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

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import hu.aventurin.gaming.gamecontroller.GameController;
import hu.aventurin.gaming.gamecontroller.KeyBindingBuilder;
import hu.zsomi.games.followgame.Renderer;
import hu.zsomi.games.followgame.model.Bullet;
import hu.zsomi.games.followgame.model.Debris;
import hu.zsomi.games.followgame.model.Enemy;
import hu.zsomi.games.followgame.model.Player;
import hu.zsomi.gaming.geometry.Location2D;

public class GameArea implements GameCtrl {

	Player player;
	List<Enemy> enemies;
	List<Bullet> bullets = new ArrayList<>();
	List<Debris> debris = new ArrayList<>();;
	Timer uiUpdateTimer;
	GameController gameController;
	long startTime;
	
	Image backgroundImage;
	private JComponent container;
	 
	public GameArea(JComponent container) throws IOException {
		this.container = container;
		startTime = System.currentTimeMillis();
		player = new Player(new Location2D(500, 500), 60, new Color(0xD0FFFF00, true), 4 ,this);
		player.setMousePosition(new Point());
		enemies = new ArrayList<>(Arrays.asList(
				new Enemy(new Location2D(560, 100), 70, Color.BLUE, 2),
				new Enemy(new Location2D(560, 200), 50, Color.GREEN, 1),
				new Enemy(new Location2D(560, 300), 60, Color.ORANGE, 1.5)));
		setupTimer();
		gameController = new GameController(player);
		KeyBindingBuilder kbBuilder = new KeyBindingBuilder(gameController.getKeyMapping());
		kbBuilder.bindAction(this::spawnEnemy).to('E');
		gameController.setKeyMapping(kbBuilder.build());

		addEventListeners();
	}

	private void addEventListeners() {
		
		container.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				player.setMousePosition(e.getPoint());
			}
		});


		container.addKeyListener(gameController.getKeyListener());
		
	}

	JComponent getContainer() {
		return container;
	}
	
	private void spawnEnemy(boolean activated) {
		if (activated) {
			Enemy e = new Enemy(new Location2D(160, 100), 70, Color.BLUE, 2);
			enemies.add(e);
		}
	}

	private void setupTimer() {
		uiUpdateTimer = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				player.doIteration();
				for (Enemy enemy : enemies) {
					enemy.setTargetPoint(player.getPosition());
					if (System.currentTimeMillis() - startTime > 1000) {
						enemy.doIteration();
					}

				}

				moveBullets();
				moveDebris();

				container.repaint();

				Polygon playerPolygon = player.getPolygon().asAwtPolygon();
				for (Enemy enemy : enemies) {
					if (playerPolygon.intersects(enemy.getRectangle())) {
						JOptionPane.showMessageDialog(container, "The enemy hit you!", "Game Over!",
								JOptionPane.INFORMATION_MESSAGE);
						uiUpdateTimer.stop();
					}
				}
			}


		});

		uiUpdateTimer.start();
	}

	private void moveDebris() {
		for (Debris aDebris: new ArrayList<>(debris)) {
			if (aDebris.isOver()) {
				debris.remove(aDebris);
			} else {
				aDebris.doIteration();
			}
		}
	}

	public void addBullet(Bullet bullet) {
		if (bullet != null) {
			bullets.add(bullet);
		}
	}
	
	private void moveBullets() {
		for (Bullet bullet : new ArrayList<>(bullets)) {
			bullet.doIteration();
			if (!container.getVisibleRect().contains(bullet.getPosition().asPoint())) {
				bullets.remove(bullet);
			} else {
				checkBulletCollisionWithEnemy();
			}
		}
	}

	private void checkBulletCollisionWithEnemy() {
		
		for (Bullet bullet : new ArrayList<>(bullets)) {
			for (Enemy enemy : new ArrayList<>(enemies)) {
				if (bullet.getRectangle().intersects(enemy.getRectangle())) {
					debris.addAll(enemy.explode());
					enemies.remove(enemy);
					bullets.remove(bullet);
					break;
				}
			}
		}
		
	}

	void setAntiAliasing(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public Renderer getRenderer() {
		return (g) -> {
			if (g instanceof Graphics2D) {
				setAntiAliasing((Graphics2D) g);
			}

			for (Enemy enemy : enemies) {
				enemy.getRenderer().render(g);
			}
			for (Bullet bullet: bullets) {
				bullet.getRenderer().render(g);
			}
			for (Debris aDebris: debris) {
				aDebris.getRenderer().render(g);
			}
			player.getRenderer().render(g);
		};
	}

	@Override
	public Rectangle getBounds() {
		return container.getBounds();
	}


}