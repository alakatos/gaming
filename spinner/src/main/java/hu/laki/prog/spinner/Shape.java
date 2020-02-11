package hu.laki.prog.spinner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hu.laki.gaming.geometry.Polygon2D;
import hu.laki.gaming.geometry.Vector2D;

public class Shape {
	 
	private double attenuation;
	private Polygon2D poly;
	private Point2D location;
	private double angularSpeed;
	private double angle;
	private double radius;
	
	private List<Color> colors = new ArrayList<>();
	
	public Shape(Point location, int radius, int numberOfEdges, double attenuation) {
		if (numberOfEdges < 3) {
			throw new IllegalArgumentException("Number of edges is expected to be greater that 2!");
		}
		if (attenuation > 1 || attenuation < 0) {
			throw new IllegalArgumentException("Attenuation must be a number between 0 and 1!");
		}
		angle = Math.random()*360;
		angularSpeed = Math.signum(0.5-Math.random())*(Math.random()*6+2);
		poly = Polygon2D.createRegularPolygon(radius, numberOfEdges);
		this.attenuation = attenuation;
		this.location = location;
		this.radius = radius;
		
		Color c = createRandomColor();
		for (int i = 0; i < numberOfEdges; i++) {
			colors.add(c);
		}
	}
	
	private Color createRandomColor() {
		int darkest = 100;
		int brightest = 200;
		return new Color(
				(int)(Math.random()*(brightest-darkest)+darkest) << 16 |
				(int)(Math.random()*(brightest-darkest)+darkest) << 8 |
				(int)(Math.random()*(brightest-darkest)+darkest)
				);
	}
	
	public void calculateNextFrame() {
		angle += angularSpeed;
		angularSpeed *= (1-attenuation);
		if (Math.abs(angularSpeed) < 0.05) {
			angularSpeed = 0;
		}
	}

	private void dropShadow(Graphics g, Environment env) {
		Vector2D middlePoint = new Vector2D(location);
		int shadowDistance = 10;
		Vector2D shadowOffsetVec = new Vector2D(shadowDistance,0).rotate(env.getLightAngle()+180);
		Vector2D shadowCenter = middlePoint.add(shadowOffsetVec);
		
		Polygon2D rotatedPolygon = poly.rotate(angle); 

		Color color = new Color(0x20000000, true);
		
		for (int distance = 6; distance > 0; distance-=2) {
			drawSmoothShadowPoly(rotatedPolygon, shadowCenter, g, color, distance);
		}

		g.setColor(color);
		g.fillPolygon(rotatedPolygon.move(shadowCenter).asAwtPolygon());
	}
	
	private void drawSmoothShadowPoly(Polygon2D rotatedPolygon, Vector2D shadowCenter, Graphics g, Color color, int distance) {
		g.setColor(color);
		g.fillPolygon(rotatedPolygon.toRadius(radius+distance).move(shadowCenter).asAwtPolygon());
	}

	private void paintAllSegments(Graphics g, Environment env) {
		List<Polygon2D> triangles = poly.rotate(angle).move(location).cutRadiallyToNEqualAreaTriangles();
		Iterator<Color> colorIter = colors.iterator();
		for (Polygon2D triangle: triangles) {
			Color newColor = calcShadowedColor(env.getLightAngle(), colorIter.next(), triangle.getPoints()); 
			g.setColor(newColor);
			g.fillPolygon(triangle.asAwtPolygon());
		}
	}

	private Color calcShadowedColor(double lightAngle, Color color, List<Vector2D> points) {
		Vector2D midLine = new Vector2D(points.get(0), points.get(1)).add(new Vector2D(points.get(1), points.get(2)).scale(0.5));
		double lightAngleDiff = Math.abs(lightAngle - midLine.getAngleGrad());
		if (lightAngleDiff >= 180) {
			lightAngleDiff = 180 - (lightAngleDiff-180);
		}
		double addBrightness = 1-lightAngleDiff/180;
		Color newColor = changeBrighness(color, 1+addBrightness*0.30);
		return newColor;
	}
    
	private Color changeBrighness(Color origColor, double extent) {
        int r = origColor.getRed();
        int g = origColor.getGreen();
        int b = origColor.getBlue();
        int alpha = origColor.getAlpha();

        return new Color(Math.min((int)(r*extent), 255),
                         Math.min((int)(g*extent), 255),
                         Math.min((int)(b*extent), 255),
                         alpha);
    }
	
	private void paintInOnePiece(Graphics g) {
		g.setColor(colors.get(0));
		g.fillPolygon(poly.rotate(angle).move(location).asAwtPolygon());
	}
	
	public void draw(Graphics g, Environment env) {
		dropShadow(g, env);
		paintAllSegments(g, env);
		//paintInOnePiece(g);
	}
	

	

}
