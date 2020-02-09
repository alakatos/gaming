package hu.zsomi.games.followgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.zsomi.games.geom.Location2D;
import hu.zsomi.games.geom.Polygon2D;
import hu.zsomi.games.geom.Vector2D;

class Enemy extends Figure {

	Location2D targetPosition = new Location2D(0, 0);

	Enemy(Location2D position, int size, Color color, double speed) {
		super(position, size, color, speed + (Math.random() - 0.5) * 0.2);
	}

	void setTargetPoint(Location2D targetPoint) {
		this.targetPosition = targetPoint;
	}

	private void followTarget() {
		setPosition(countSpeedVector().move(getPosition()).getTargetPoint());
	}

	private Vector2D countSpeedVector() {
		return new Vector2D(0,0);//new Vector2D(getPosition(), targetPosition).toLength(getSpeed());
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

	public List<Debris> explode() {
		Rectangle r = getRectangle();
		List<Vector2D> corners = new ArrayList<>(
				Arrays.asList(
						new Vector2D(r.x, r.y), 
						new Vector2D(r.x + getSize(), r.y),
						new Vector2D(r.x + getSize(), r.y + getSize()), 
						new Vector2D(r.x, r.y + getSize()),
						new Vector2D(r.x, r.y) 
						)
		);
		Vector2D randomMiddlePoint = new Vector2D(r.x + countMiddlePoint(), r.y + countMiddlePoint());
		List<Polygon2D> triangles = new ArrayList<>();
		for (int i = 0; i < corners.size()-1; i++) {
			triangles.add(new Polygon2D(Arrays.asList(
					corners.get(i), 
					corners.get(i+1),
					randomMiddlePoint
			)));
		}
		List<Debris> debris = new ArrayList<>();
		double areaOfSquare = getSize()*getSize();
		for (Polygon2D triangle : triangles) {
			double massRatio = triangle.calculateArea()/areaOfSquare;
			massRatio = massRatio*massRatio;
			Vector2D massCenterPoint = triangle.calculateMassCenterPoint();
			Vector2D debrisSpeedVector = countSpeedVector().add(new Vector2D(randomMiddlePoint, massCenterPoint).toLength(1).scale((1-massRatio)*5));
			debris.add(
					new Debris(
							massCenterPoint.getTargetPoint(), 
							triangle.move(massCenterPoint.invert()), 
							getColor(),
							debrisSpeedVector, 
							Math.signum(0.5-Math.random())*((0.3-massRatio)*20)));
		}
		return debris;
	}


	private int countMiddlePoint() {
		return (int)(Math.random()*getSize()*3/4+1d/8*getSize());
	}

}
