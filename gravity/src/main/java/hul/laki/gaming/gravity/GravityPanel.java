package hul.laki.gaming.gravity;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.Timer;

import hu.aventurin.gaming.gamepad.GamePad;
import hu.laki.gaming.geometry.Vector2D;

public class GravityPanel extends JComponent {

	private Timer uiTmer;
	private GravityKineticModel kineticModel;
	private int planetSize = 4;
	private GamePad keypad;

	public GravityPanel() throws IOException {
		kineticModel = new GravityKineticModel();
		keypad = new GamePad(kineticModel, 1);
		new Timer(5, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				kineticModel.calculateNextFrame();
			}
		}).start();

		uiTmer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
		uiTmer.start();

		addKeyListener(keypad.getKeyListener());
		
		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar()>='1' && e.getKeyChar()<='9') {
					planetSize = e.getKeyChar()-'0';
				} else {
					if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						
					}
				}
			}
		});
		
		addMouseListener(new MouseAdapter() {
			
			private Point pt;
			@Override
			public void mousePressed(MouseEvent e) {
				pt = e.getPoint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				addNewBall(pt, new Vector2D(pt, e.getPoint()).scale(0.05));
			}
		});


	}

	private void addNewBall(Point point, Vector2D speedVec) {
		kineticModel.addBall(new Planet(point, 1000*planetSize*planetSize*planetSize, speedVec, Color.YELLOW, planetSize*2));
	}

	void setAntiAliasing(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (g instanceof Graphics2D) {
			setAntiAliasing((Graphics2D) g);
		}

		for (Ball ball : kineticModel.getBalls()) {
			ball.paint(g);
		}
	}

}
