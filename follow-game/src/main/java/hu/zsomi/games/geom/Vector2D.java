package hu.zsomi.games.geom;

import java.awt.Point;

public class Vector2D {

	private Location2D targetPoint;

	public Vector2D(Point to) {
		this(0, 0, to.x, to.y);
	}

	public Vector2D(Point from, Point to) {
		this(from.x, from.y, to.x, to.y);
	}

	public Vector2D(double x, double y) {
		setTargetPoint(x, y);
	}

	public Vector2D(double x1, double y1, double x2, double y2) {
		setTargetPoint(x2 - x1, y2 - y1);
	}

	private final void setTargetPoint(double x, double y) {
		targetPoint = new Location2D(x, y);
	}
	
	public Location2D getTargetPoint() {
		return targetPoint;
	}

	public double getAngle() {
		double angle = Math.PI / 2;
		if (targetPoint.getX() != 0) {
			angle = Math.atan((double) targetPoint.getY() / targetPoint.getX());
			if (targetPoint.getX() < 0) {
				angle += Math.PI;
			}
		} else if (targetPoint.getY() < 0) {
			angle = Math.PI * 3 / 2;
		}
		return angle >= 0 ? angle : angle + 2 * Math.PI;
	}

	public int getAngleGrad() {
		double angle = getAngle();
		return angle == 0 ? 0 : (int) (angle / (2 * Math.PI) * 360);
	}

	public double getLength() {
		return Math.sqrt(targetPoint.getX() * targetPoint.getX() + targetPoint.getY() * targetPoint.getY());
	}

	public Vector2D rotate(int angleOffs) {
		int angleGrad = getAngleGrad();
		double angle = (angleGrad + angleOffs) % 360 / 360d * (2 * Math.PI);
		double len = getLength();
		return new Vector2D(Math.cos(angle) * len, Math.sin(angle) * len);
	}

	public Vector2D scale(double extent) {
		if (extent < 0) {
			throw new IllegalArgumentException("Scale extent should be non-negative");
		}
		return new Vector2D(getLength()*Math.abs(extent),0).rotate(getAngleGrad());
	}
	
	public Vector2D toLength(double newLength) {
		if (newLength < 0) {
			throw new IllegalArgumentException("New length should be non-negative");
		}
		return new Vector2D(newLength, 0).rotate(getAngleGrad());
	}
	
	public Vector2D move(Point pt) {
		return move(pt.x, pt.y);
	}
	
	public Vector2D move(double xOffs, double yOffs) {
		return new Vector2D(targetPoint.move(xOffs, yOffs).asPoint());
	}
	public Point asPoint() {
		return targetPoint.asPoint();
	}
	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector2D) {
			Vector2D otherVec = (Vector2D) obj;
			return targetPoint.equals(otherVec.targetPoint);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return Vector2D.class.getSimpleName() + " to " + targetPoint.toString();
	}
}
