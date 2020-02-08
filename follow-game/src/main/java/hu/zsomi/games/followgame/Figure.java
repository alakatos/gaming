package hu.zsomi.games.followgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

abstract class Figure  {

	Point position = new Point();
	int size = 10;
	Color color;
	int speed;
	
	public Figure(Point position, int size, Color color, int speed) {
		this.position = position;
		this.size = size;
		this.color = color;
		this.speed = speed;
	}

	void setSpeed(int speed) {
		this.speed = speed;
	}
	
	void setColor(Color color) {
		this.color = color;
	}
	
	protected Color getColor() {
		return color;
	}

	Figure(Point position, int size, Color color) {
		this.position = position;
		this.size = size;
		this.color = color;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point pt) {
		this.position = new Point(pt.x, pt.y);
	}

	public void setPosition(int x, int y) {
		this.position = new Point(x,y);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(position.x-size/2, position.y-size/2, size, size);
	}

	abstract void draw(Graphics g);
	abstract void drawShadow(Graphics g);


}
