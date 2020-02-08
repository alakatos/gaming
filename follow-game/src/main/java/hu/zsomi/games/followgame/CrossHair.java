package hu.zsomi.games.followgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class CrossHair extends Figure {

	CrossHair(Point position, int size, Color color) {
		super(position, size, color);
	}
	
	@Override
	void draw(Graphics g) {
		g.setColor(getColor());
		int focusX = getPosition().x; 
		int focusY = getPosition().y;
		int w = size;
		int h = size;
		g.drawOval(focusX-5, focusY-5, w-30, h-30);
		g.drawOval(focusX-10, focusY-10, w-20, h-20);
		g.drawOval(focusX-15, focusY-15, w-10, h-10);
		g.drawLine(focusX, focusY-h/2, focusX, focusY+h/2);
		g.drawLine(focusX-w/2, focusY, focusX+w/2, focusY);
	}

	@Override
	void drawShadow(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
