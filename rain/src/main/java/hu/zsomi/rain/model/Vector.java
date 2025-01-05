package hu.zsomi.rain.model;

import java.awt.Point;
import java.awt.geom.Point2D;


public class Vector {

    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Point p) {
        this(p.x, p.y);
    }

    public Vector(Point2D p) {
        this(p.getX(), p.getY());
    }

    public Vector add(Vector v2) {
        return new Vector(x + v2.x, y + v2.y);
    }

    public Vector to(Vector v2) {
        return new Vector(v2.x - x, v2.y - y);
    }

    public Vector multiply(double multiplier) {
      return new Vector(x*multiplier, y*multiplier);
    }

    public double length() {
        return Math.sqrt(x*x + y*y);
    }
    
}
