package hu.zsomi.games.followgame;

import java.awt.Color;
import java.awt.Graphics;

import hu.zsomi.games.geom.Location2D;

public class CrossHair extends Figure {

	CrossHair(Location2D position, int size, Color color) {
		super(position, size, color, 0);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(getColor());
		int focusX = getPosition().getXInt(); 
		int focusY = getPosition().getYInt();
		int w = getSize();
		int h = getSize();
		g.drawOval(focusX-5, focusY-5, w-30, h-30);
		g.drawOval(focusX-10, focusY-10, w-20, h-20);
		g.drawOval(focusX-15, focusY-15, w-10, h-10);
		g.drawLine(focusX, focusY-h/2, focusX, focusY+h/2);
		g.drawLine(focusX-w/2, focusY, focusX+w/2, focusY);
	}

	@Override
	public void doIteration() {
		//do nothing
	}

}
