package hu.zsomi.games.geom;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class Polygon2D {
	
	private List<Vector2D> points = new ArrayList<>();
	
	public void addPoint(double x, double y) {
		addPoint(new Vector2D(x, y));
	}
	
	Polygon2D(List<Vector2D> polyPoints) {
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
		List<Vector2D> newPolyPoints = new ArrayList<>();
		for (Vector2D pt : points) {
			newPolyPoints.add(pt.scale(extent));
		}
		return new Polygon2D(newPolyPoints);
	}
	
	public Polygon2D move(Point pt) {
		return (move(pt.x, pt.y));
	}
	
	public Polygon2D move(double xOffs, double yOffs) {
		List<Vector2D> newPolyPoints = new ArrayList<>();
		for (Vector2D pt : points) {
			newPolyPoints.add(pt.move(xOffs, yOffs));
		}
		return new Polygon2D(newPolyPoints);
	}
	
	public static Polygon2D createNormalNPolygon(double size, int numberOfEdges) {
		List<Vector2D> polyPoints = new ArrayList<>();
		Vector2D baseVec = new Vector2D(size, 0);
		double angleMul = 360d/numberOfEdges;
		for (int i = 0; i < numberOfEdges; i++) {
			polyPoints.add(baseVec.rotate((int)(angleMul*i)));
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
 }	
