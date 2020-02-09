package hu.zsomi.games.followgame.model;

import java.awt.Color;

import hu.zsomi.games.followgame.Renderer;
import hu.zsomi.games.geom.Location2D;
import hu.zsomi.games.geom.Polygon2D;
import hu.zsomi.games.geom.Vector2D;

public class Debris extends Figure {

	private Vector2D speedVector;
	private double rotationSpeed;
	private Polygon2D shape;
	private double angle;
	private static final double MAX_LIFETIME = 1000d;
	private long createdTst;
	public Debris(Location2D position, Polygon2D shape, Color color, Vector2D speedVector, double rotationSpeed) {
		super(position, 1, color, speedVector.getLength());
		this.speedVector = speedVector;
		this.rotationSpeed = rotationSpeed;
		this.shape = shape;
		angle = 0;
		createdTst = System.currentTimeMillis();
	}

	@Override
	protected Color getColor() {
		int fadedPercentage = 256-(int)(getLifetimeLeftPercentage()*256);
		return new Color(
				(super.getColor().getRGB() & 0x00FFFFFF) | fadedPercentage<<24, true);
	}
	public boolean isOver() {
		return System.currentTimeMillis() - createdTst >= MAX_LIFETIME;
	}
	
	double getLifetimeLeftPercentage() {
		if (isOver()) {
			return 0;
		}
		return (System.currentTimeMillis() - createdTst) / MAX_LIFETIME; 
	}
	
	public Renderer getRenderer() {
		return (g) -> {
			g.setColor(getColor());
			g.fillPolygon(shape.rotate(((int)angle)%360).move(getPosition()).asAwtPolygon());
		};
	}


	@Override
	public void doIteration() {
		setPosition(new Vector2D(getPosition()).add(speedVector));
		angle += rotationSpeed;
	}

}
