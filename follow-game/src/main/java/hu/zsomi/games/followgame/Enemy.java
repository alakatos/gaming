package hu.zsomi.games.followgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import hu.zsomi.games.geom.Location2D;
import hu.zsomi.games.geom.Vector2D;

class Enemy extends Figure {

	Location2D targetPosition = new Location2D(0,0);

	Enemy(Location2D position, int size, Color color, double speed) {
		super(position, size, color, speed+(Math.random()-0.5)*0.2);
	}

	void setTargetPoint(Location2D targetPoint) {
		this.targetPosition = targetPoint;
	}
	
	private void followTarget() {
		Vector2D followVec = new Vector2D(getPosition().asPoint(), targetPosition.asPoint()).toLength(getSpeed());
		setPosition(followVec.move(getPosition()).getTargetPoint());
	}
	
	void draw(Graphics g) {
		g.setColor(getColor());
		Rectangle rect = getRectangle();
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}

	@Override
	public void doIteration() {
		followTarget();
	}

}
