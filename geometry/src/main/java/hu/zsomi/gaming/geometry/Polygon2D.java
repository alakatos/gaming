package hu.zsomi.gaming.geometry;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
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

	public Polygon2D move(Vector2D vec) {
		return move(vec.getPoint());
	}
	public Polygon2D move(Point2D loc) {
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
			polyPoints.add(baseVec.rotate(angleMul * i));
		}
		return new Polygon2D(polyPoints);
	}

	public Polygon asAwtPolygon() {
		Polygon p = new Polygon();
		for (Vector2D pt : points) {
			p.addPoint((int)pt.getPoint().getX(), (int)pt.getPoint().getY());
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
			area += (points.get(j).getPoint().getX() + 
					points.get(i).getPoint().getX()) * (
							points.get(j).getPoint().getY() - 
							points.get(i).getPoint().getY());
			j = i;
		}

		return Math.abs(area / 2.0);
	}
	
	
	public Vector2D calculateMassCenterPoint() {
		double sumX = 0;
		double sumY = 0;
		for (Vector2D corner : points) {
			sumX += corner.getPoint().getX();
			sumY += corner.getPoint().getY();
		}
		return new Vector2D(sumX/points.size(),sumY/points.size());
	}

	public List<Polygon2D> cutRadiallyToNEqualAreaTriangles() {
		return cutRadiallyToNTriangles(calculateMassCenterPoint());
	}
	
	public List<Polygon2D> cutRadiallyToNTriangles(Vector2D fromPoint) {
		asAwtPolygon().contains(fromPoint.getPoint());
		List<Polygon2D> polys = new ArrayList<>();
		for (int i = 0; i < points.size()-1; i++) {
			polys.add(new Polygon2D(Arrays.asList(
					fromPoint,
					points.get(i),
					points.get(i+1))));
		}
		polys.add(new Polygon2D(Arrays.asList(
				fromPoint,
				points.get(points.size()-1),
				points.get(0))));
		return polys;
	}

	
	public List<Vector2D> getPoints() {
		return new ArrayList<>(points);
	}
	
	@Override
	public String toString() {
		return points.toString();
	}
}
