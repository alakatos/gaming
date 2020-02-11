package hu.laki.prog.spinner;

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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;

import hu.laki.gaming.geometry.Vector2D;

public class SpinnerPanel extends JComponent implements Environment {

	private Timer uiTmer;
	private List<Shape> shapes = new ArrayList<>();
	private Vector2D lightVector = new Vector2D(1, 1).rotate(180);
	private int edgeCount = 3;
	private BufferedImage backgroundImage;

	public SpinnerPanel() throws IOException {
		backgroundImage = ImageIO.read(getClass().getResourceAsStream("/Landscape_cartoon.png"));
		uiTmer = new Timer(15, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Shape shape : shapes) {
					shape.calculateNextFrame();
				}
				//lightVector = lightVector.rotate(1);
				repaint();
			}
		});
		uiTmer.start();

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar()>'2' && e.getKeyChar()<='9') {
					edgeCount = e.getKeyChar()-'0';
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				addNewPolygon(new Point(e.getX(), e.getY()));
			}
		});

		Shape oktogon = new Shape(new Point(300, 300), 150, 8, 0.001);
		shapes.add(oktogon);

	}

	private void addNewPolygon(Point point) {
		Shape shape = new Shape(point, 100, edgeCount, 0.002);
		shapes.add(shape);
		
		if (shapes.size() > 20) {
			shapes.remove(0);
		}
	}

	void setAntiAliasing(Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		if (g instanceof Graphics2D) {
			setAntiAliasing((Graphics2D) g);
		}

		for (Shape shape : shapes) {
			shape.draw(g, this);
		}
	}

	@Override
	public double getLightAngle() {
		return lightVector.getAngleGrad();
	}
}
