package hu.zsomi.games.followgame.model;

import java.awt.Color;

import hu.zsomi.games.followgame.Renderer;
import hu.zsomi.gaming.geometry.Location2D;

public class CrossHair extends Figure {

	CrossHair(Location2D position, int size, Color color) {
		super(position, size, color, 0);
	}
	
	public Renderer getRenderer() {
		return (g) -> {
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
		};
	}

	@Override
	public void doIteration() {
		//do nothing
	}

}
