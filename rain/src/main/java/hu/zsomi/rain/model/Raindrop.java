package hu.zsomi.rain.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import hu.zsomi.rain.Main;

public class Raindrop {
    private final Point2D location;
	private long birthTime = System.currentTimeMillis();
	private boolean outOfScreen;
	private int size;
	private boolean landed;
	private Vector speed;
	private SpaceGeometryProvider mouseLocationProvider;
	


    public Raindrop(int xCoord, SpaceGeometryProvider mouseLocationProvider) {
        location = new Point2D.Double(xCoord, 0);
		size = (int)(Math.random()*3+1);
		speed = new Vector(0, 0);//Main.WIND_SPEED+(0.5 + Math.random()/2), 0);
		this.mouseLocationProvider = mouseLocationProvider;
	}

	public void calcNextLocation() {
		Vector raindropLocation = new Vector(location);
		Vector mouseLocation = new Vector(mouseLocationProvider.getMouseLocation());
		Vector attractVector = raindropLocation.to(mouseLocation);
		double attractStrength = 3/(attractVector.length()*attractVector.length());
		attractVector = attractVector.multiply(attractStrength);

		Vector gVector = new Vector(0, Main.ACC/200);
		Vector accelerationVector = attractVector.add(gVector);
		accelerationVector = accelerationVector.add(speed.multiply(-1/2));
		speed = speed.add(accelerationVector);
		location.setLocation(
			location.getX()+speed.x, 
			location.getY()+speed.y);

		if (location.getY() > mouseLocationProvider.getWindowDimensions().height) {
			outOfScreen = true;
		}
	}

	public boolean isOutOfScreen() {
		return outOfScreen;
	}

	public void paint(Graphics g) {
		double percentage = 1- (location.getY() / mouseLocationProvider.getWindowDimensions().height);
		int shade = 100+(int)(155*percentage);
		g.setColor(Color.WHITE);//new Color((int)(shade*0.7), (int)(shade*0.9), shade));
		g.fillOval((int)location.getX(), (int)location.getY(), size, size);
	}

}
