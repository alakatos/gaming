package hu.zsomi.games.followgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

class Enemy extends Figure {

	Point targetPosition = new Point();

	Enemy(Point position, int size, Color color, int speed) {
		super(position, size, color, speed);
	}

	void setTargetPoint(Point targetPoint) {
		this.targetPosition = targetPoint;
	}
	
	void followTarget() {
		if (targetPosition.x > position.x) position.x = position.x + speed;
		if (targetPosition.x < position.x) position.x = position.x - speed;
		if (targetPosition.y > position.y) position.y = position.y +
				speed;
		if (targetPosition.y < position.y) position.y = position.y - speed;

		//position = targetPoint;
	}
	
	void draw(Graphics g) {
		g.setColor(color);
		Rectangle rect = getRectangle();
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	void drawShadow(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
