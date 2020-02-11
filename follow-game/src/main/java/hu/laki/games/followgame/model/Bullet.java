package hu.laki.games.followgame.model;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import hu.laki.games.followgame.Renderer;
import hu.laki.gaming.geometry.Vector2D;

public class Bullet extends Figure {

	private Vector2D speedVector;
	public Bullet(Point2D position, Point2D targetPosition, int size, double speed) {
		super(position, size, Color.RED, speed);
		speedVector = new Vector2D(position, targetPosition).toLength(speed);
	}

	
	public Renderer getRenderer() {
		return (g) -> {
			Rectangle r = getRectangle();
			g.setColor(Color.RED);
			g.fillOval(r.x, r.y, r.width, r.height);
			setColor(Color.BLACK);
			g.drawOval(r.x, r.y, r.width, r.height);
		};
	}


	@Override
	public void doIteration() {
		setPosition(speedVector.move(getPosition()).getPoint());
	}

}
