package hu.zsomi.games.followgame;

import java.awt.Color;
import java.awt.Graphics;

import hu.zsomi.games.geom.Location2D;
import hu.zsomi.games.geom.Polygon2D;

public class Debris extends Figure {

	public Debris(Location2D position, Polygon2D shape, Color color, int speed) {
		super(position, 1, color, speed);
	}

	@Override
	public void draw(Graphics g) {

	}

	@Override
	public void doIteration() {
		//rotate

	}

}
