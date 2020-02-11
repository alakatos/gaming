package hu.laki.games.followgame.model;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import hu.laki.games.followgame.Renderer;
import hu.laki.gaming.geometry.Vector2D;

public abstract class Figure  {

	private Point2D position;
	private int size = 10;
	private Color color;
	private double speed;
	
	public Figure(Point2D position, int size, Color color, double speed) {
		this.position = position;
		this.size = size;
		this.color = color;
		this.speed = speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	void setColor(Color color) {
		this.color = color;
	}
	
	protected Color getColor() {
		return color;
	}


	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D pt) {
		this.position = pt;
	}

	public void setPosition(Vector2D vec) {
		setPosition(vec.getPoint());
	}

	public void setPosition(double x, double y) {
		setPosition(new Point2D.Double(x,y));
	}
	
	public Rectangle getRectangle() {
		return new Rectangle((int)position.getX()-size/2, (int)position.getY()-size/2, size, size);
	}

	public abstract void doIteration();


	public abstract Renderer getRenderer();
}
