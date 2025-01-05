package hu.zsomi.rain.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

import hu.zsomi.rain.Main;

public class Particle {
    private final Point2D location;
	private long birthTime = System.currentTimeMillis();
	private boolean outOfScreen = false;
	private int size;
	private Vector speed;
	private SpaceGeometryProvider mouseLocationProvider;
	
	private  final Vector gVector = new Vector(0, Main.ACC/200);


    public Particle(int xCoord, SpaceGeometryProvider mouseLocationProvider) {
        location = new Point2D.Double(xCoord, 100);
		size = (int)(Math.random()*3+1);
		speed = new Vector(0, 0);//Main.WIND_SPEED+(0.5 + Math.random()/2), 0);
		this.mouseLocationProvider = mouseLocationProvider;
	}

	void calcNextLocation() {
		if (!outOfScreen) {
			Vector raindropLocation = new Vector(location);
			Vector mouseLocation = new Vector(mouseLocationProvider.getMouseLocation());
			Vector attractVector = raindropLocation.to(mouseLocation);
			double attractStrength = attractVector.length() < 50 ? 0 : 1/(attractVector.length()*80);
			attractVector = attractVector.multiply(attractStrength);
	
			Vector accelerationVector = attractVector.add(gVector);
			accelerationVector = accelerationVector.add(speed.multiply(19/20));
			speed = speed.add(accelerationVector);
			location.setLocation(
				location.getX()+speed.x, 
				location.getY()+speed.y);
	
			if (location.getX() < -100 || location.getX() > mouseLocationProvider.getWindowDimensions().width+100 || location.getY() < -100 || location.getY() > mouseLocationProvider.getWindowDimensions().height + 100) {
				outOfScreen = true;
			}
		}
	}

	public boolean isOutOfScreen() {
		return outOfScreen;
	}

	public void paint(Graphics g) {
		//double percentage = 1- (location.getY() / mouseLocationProvider.getWindowDimensions().height);
		//int shade = 100+(int)(155*percentage);
		//g.fillOval((int)location.getX(), (int)location.getY(), size, size);
		g.drawRect((int)location.getX(), (int)location.getY(), size, size);
	}

}
