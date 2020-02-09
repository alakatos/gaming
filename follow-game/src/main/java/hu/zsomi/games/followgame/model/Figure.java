package hu.zsomi.games.followgame.model;
import java.awt.Color;
import java.awt.Rectangle;

import hu.zsomi.games.followgame.Renderer;
import hu.zsomi.gaming.geometry.Location2D;
import hu.zsomi.gaming.geometry.Vector2D;

public abstract class Figure  {

	private Location2D position;
	private int size = 10;
	private Color color;
	private double speed;
	
	public Figure(Location2D position, int size, Color color, double speed) {
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
	
	public Location2D getPosition() {
		return position;
	}
	
	public void setPosition(Location2D pt) {
		this.position = pt;
	}

	public void setPosition(Vector2D vec) {
		setPosition(vec.getTargetPoint());
	}

	public void setPosition(double x, double y) {
		setPosition(new Location2D(x,y));
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(position.getXInt()-size/2, position.getYInt()-size/2, size, size);
	}

	public abstract void doIteration();


	public abstract Renderer getRenderer();
}
