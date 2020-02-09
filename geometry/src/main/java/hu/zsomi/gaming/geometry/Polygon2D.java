package hu.zsomi.gaming.geometry;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class Polygon2D {

	private List<Vector2D> points = new ArrayList<>();

	public void addPoint(double x, double y) {
		addPoint(new Vector2D(x, y));
	}

	public Polygon2D(List<Vector2D> polyPoints) {
		this.points = new ArrayList<>(polyPoints);
	}

	public void addPoint(Vector2D polyPoint) {
		points.add(polyPoint);
	}

	public Polygon2D rotate(int angle) {
		List<Vector2D> newPolyPoints = new ArrayList<>();
		for (Vector2D pt : points) {
			newPolyPoints.add(pt.rotate(angle));
		}
		return new Polygon2D(newPolyPoints);
	}

	public Polygon2D scale(double extent) {
		if (extent < 0) {
			throw new IllegalArgumentException("Scale extent should be non-negative");
		}
		List<Vector2D> newPolyPoints = new ArrayList<>();
		for (Vector2D pt : points) {
			newPolyPoints.add(pt.scale(extent));
		}
		return new Polygon2D(newPolyPoints);
	}

	public Polygon2D move(Point pt) {
		return (move(pt.x, pt.y));
	}

	public Polygon2D move(Vector2D vec) {
		return move(vec.getTargetPoint());
	}
	public Polygon2D move(Location2D loc) {
		return move(loc.getX(), loc.getY());
	}

	public Polygon2D move(double xOffs, double yOffs) {
		List<Vector2D> newPolyPoints = new ArrayList<>();
		for (Vector2D pt : points) {
			newPolyPoints.add(pt.move(xOffs, yOffs));
		}
		return new Polygon2D(newPolyPoints);
	}
	

	public static Polygon2D createNormalNPolygon(double radius, int numberOfEdges) {
		List<Vector2D> polyPoints = new ArrayList<>();
		Vector2D baseVec = new Vector2D(radius, 0);
		double angleMul = 360d / numberOfEdges;
		for (int i = 0; i < numberOfEdges; i++) {
			polyPoints.add(baseVec.rotate((int) (angleMul * i)));
		}
		return new Polygon2D(polyPoints);
	}

	public Polygon asAwtPolygon() {
		Polygon p = new Polygon();
		for (Vector2D pt : points) {
			p.addPoint(pt.getTargetPoint().getXInt(), pt.getTargetPoint().getYInt());
		}
		return p;
	}

	public double calculateArea() {
		double area = 0.0;

		int n = points.size();
		if (n<3) {
			return 0;
		}
		
		int j = n - 1;
		for (int i = 0; i < n; i++) {
			area += (points.get(j).getTargetPoint().getX() + 
					points.get(i).getTargetPoint().getX()) * (
							points.get(j).getTargetPoint().getY() - 
							points.get(i).getTargetPoint().getY());
			j = i;
		}

		return Math.abs(area / 2.0);
	}
	
	
	public Vector2D calculateMassCenterPoint() {
		double sumX = 0;
		double sumY = 0;
		for (Vector2D corner : points) {
			sumX += corner.getTargetPoint().getX();
			sumY += corner.getTargetPoint().getY();
		}
		return new Vector2D(sumX/points.size(),sumY/points.size());
	}
	

	
	public List<Vector2D> getPoints() {
		return new ArrayList<>(points);
	}
	
	@Override
	public String toString() {
		return points.toString();
	}
}
