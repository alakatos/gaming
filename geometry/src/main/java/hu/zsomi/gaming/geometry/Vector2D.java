package hu.zsomi.gaming.geometry;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Vector2D {

	private static final int ROUND_TO_DECIMAL_PLACES = 6;
	private Point2D point;

	public Vector2D(Point2D toPoint) {
		this.point = toPoint;
	}

	public Vector2D(Vector2D from, Vector2D to) {
		this(from.getPoint(), to.getPoint());
	}

	public Vector2D(Point2D from, Point2D to) {
		this(from.getX(), from.getY(), to.getX(), to.getY());
	}
	
	public Vector2D(double x, double y) {
		setTargetPoint(x, y);
	}

	public Vector2D(double x1, double y1, double x2, double y2) {
		setTargetPoint(x2 - x1, y2 - y1);
	}

	private final void setTargetPoint(double x, double y) {
		point = new Point2D.Double(x, y);
	}
	
	public Point2D getPoint() {
		return point;
	}

	public double getAngle() {
		double angle = Math.PI / 2;
		if (point.getX() != 0) {
			angle = Math.atan((double) point.getY() / point.getX());
			if (point.getX() < 0) {
				angle += Math.PI;
			}
		} else if (point.getY() < 0) {
			angle = Math.PI * 3 / 2;
		}
		return angle >= 0 ? angle : angle + 2 * Math.PI;
	}

	public double getAngleGrad() {
		return rad2Grad(getAngle());
	}

	public double getLength() {
		return Math.sqrt(point.getX() * point.getX() + point.getY() * point.getY());
	}

	public Vector2D rotate(double angleGradOffs) {
		double angle = getAngle() + grad2Rad(angleGradOffs);
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
	
	public Vector2D move(Point2D pt) {
		return move(pt.getX(), pt.getY());
	}

	
	public Vector2D move(double xOffs, double yOffs) {
		return new Vector2D(point.getX()+xOffs, point.getY()+yOffs);
	}
	
	public Vector2D invert() {
		return new Vector2D(-1*point.getX(), -1*point.getY());
	}

	public Point2D asPoint() {
		return point;
	}
	

	public Vector2D add(Vector2D vec2add) {
		return new Vector2D(vec2add.move(point).point);
	}

	public Vector2D mirrorToAxisX() {
		return new Vector2D(point.getX(), point.getY()*-1);
	}

	public Vector2D mirrorToAxisY() {
		return new Vector2D(point.getX()*-1, point.getY());
	}

	
	@Override
	public String toString() {
		return Vector2D.class.getSimpleName() + " to " + point.toString();
	}
	
	private static double rad2Grad(double angleRad) {
		return angleRad/(2*Math.PI)*360;
	}
	private static double grad2Rad(double angleGrad) {
		return angleGrad/360 * 2*Math.PI;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector2D) {
			Vector2D otherVec = (Vector2D) obj;
			return round(getPoint().getX()) == round(otherVec.getPoint().getX()) &&
				   round(getPoint().getY()) == round(otherVec.getPoint().getY());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (round(point.getX())+""+round(point.getY())).hashCode();
	}
	
	private static double round(double value) {
	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(ROUND_TO_DECIMAL_PLACES, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
