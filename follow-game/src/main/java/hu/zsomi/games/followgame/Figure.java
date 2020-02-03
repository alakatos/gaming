package hu.zsomi.games.followgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

class Figure  {

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

	Figure(Point position, int size, Color color) {
		this.position = position;
		this.size = size;
		this.color = color;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	void draw(Graphics g) {
		Color savedColor = g.getColor();
		g.setColor(color);
		g.fillRect(position.x, position.y, size, size);
		g.setColor(savedColor);
	}

	public int getSize() {
		return size;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(int x, int y) {
		this.position = new Point(x,y);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(position.x, position.y, size, size);
	}


}
