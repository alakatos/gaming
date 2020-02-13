package hul.laki.gaming.gravity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import hu.laki.gaming.geometry.Vector2D;

public class Planet extends Ball {

	private Color color;

	public Planet(Point2D location, double mass, Vector2D initialSpeed, Color color, int visibleSize) {
		super(location, mass, initialSpeed, visibleSize);
		this.color = color;
	}

	@Override
	void paint(Graphics g) {
		g.setColor(color);
		int size = getVisualSize();
		g.fillOval((int)getLocation().getX(), (int)getLocation().getY(), size, size);
	}
	
}
