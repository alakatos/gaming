package hu.zsomi.games.geom;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Location2D {
	static final int ROUND_TO_DECIMAL_PLACES = 6;
	private final double x; 
	private final double y;

	public Location2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Location2D(Point pt) {
		this.x = pt.x;
		this.y = pt.y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getXInt() {
		return (int)x;
	}
	
	public int getYInt() {
		return (int)y;
	}
	
	public Location2D move(double xOffs, double yOffs) {
		return new Location2D(x+xOffs, y+yOffs);
	}
	
	public Location2D move(int xOffs, int yOffs) {
		return move((double)xOffs, (double)yOffs);
	}
	
	public Location2D move(Point pt) {
		return move((double)pt.x, (double)pt.y);
	}
	
	public Point asPoint() {
		return new Point((int)x,(int)y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Location2D) {
			Location2D otherLoc = (Location2D)obj;
			return round(x) == round(otherLoc.x) && round(y) == round(otherLoc.y);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (round(x)+""+round(y)).hashCode();
	}
	
	@Override
	public String toString() {
		return Location2D.class.getSimpleName()+"("+x+","+y+")";
	}
	
	private static double round(double value) {
	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(ROUND_TO_DECIMAL_PLACES, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
