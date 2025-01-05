package hu.zsomi.rain.model;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import hu.zsomi.rain.RenderingContext;

public class Particle {
	private final Point2D location;
	private boolean outOfScreen = false;
	private int size;
	private Vector speed;
	private RenderingContext context;

	private final Vector gVector;

	public Particle(int xCoord, RenderingContext context) {
		gVector = new Vector(0, context.getConfig().getG() / 10000d);
		this.context = context;
		location = new Point2D.Double(xCoord, 200);
		size = (int) (Math.random() * 3 + 1);
		speed = new Vector(0, 0);// Main.WIND_SPEED+(0.5 + Math.random()/2), 0);
	}

	void calcNextLocation() {
		if (!outOfScreen) {
			Vector raindropLocation = new Vector(location);
			Vector mouseLocation = new Vector(context.getMouseLocation());
			Vector attractVector = raindropLocation.to(mouseLocation);
			double attractStrength = attractVector.length() < context.getConfig().getZeroGravityRadius() ? 0 : 1 / (attractVector.length() * 300);
			attractVector = attractVector.multiply(attractStrength);

			Vector accelerationVector = attractVector.add(gVector);
			accelerationVector = accelerationVector.add(speed.multiply(19 / 20));
			speed = speed.add(accelerationVector);
			
			location.setLocation(
					location.getX() + speed.x,
					location.getY() + speed.y);

			if (location.getX() < -100 || location.getX() > context.getWindowDimensions().width + 100
					|| location.getY() < -100
					|| location.getY() > context.getWindowDimensions().height + 100) {
				outOfScreen = true;
			}
		}
	}

	public boolean isOutOfScreen() {
		return outOfScreen;
	}

	public void paint(Graphics g) {
		g.drawRect((int) location.getX(), (int) location.getY(), size, size);
	}

}
