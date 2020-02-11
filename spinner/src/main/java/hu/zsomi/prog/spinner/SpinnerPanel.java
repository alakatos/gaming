package hu.zsomi.prog.spinner;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.Timer;

import hu.zsomi.gaming.geometry.Vector2D;

public class SpinnerPanel extends JComponent implements Environment {

	private Timer uiTmer;
	private List<Shape> shapes = new ArrayList<>();
	private Vector2D lightVector = new Vector2D(1, 1).rotate(180);

	public SpinnerPanel() {
		uiTmer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Shape shape : shapes) {
					shape.calculateNextFrame();
				}
				// lightVector = lightVector.rotate(1);
				repaint();
			}
		});
		uiTmer.start();

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
		//TODO Zsomikám, adj hozzá új sokszöget (fennebb találsz rá példát)
		//a sokszög a megkapott point kordinátára kerüljön
		//hogy hány szöge van, az a Shape osztály paraméterezésétől függ, nézd meg!
		//
		//Puszi!
		//Apa

		//ide írd az utasítást:
		
		
		
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
