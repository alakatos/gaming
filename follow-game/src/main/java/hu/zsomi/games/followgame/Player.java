package hu.zsomi.games.followgame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import hu.aventurin.gaming.gamecontroller.Direction;
import hu.aventurin.gaming.gamecontroller.GameControllerListener;
import hu.zsomi.games.geom.Polygon2D;
import hu.zsomi.games.geom.Vector2D;

public class Player extends Figure implements GameControllerListener {

	
	private Direction direction = Direction.NONE;
	private GameArea gameArea;
	private CrossHair crossHair;
	
	Player(Point position, int size, Color color, int speed, GameArea gameArea) {
		super(new Point(position), size, color, speed);
		this.gameArea = gameArea;
		Point pt = new Point(position.x, position.y);
		pt.move(50,50);
		crossHair = new CrossHair(pt, 40, Color.BLACK);
	}

	void goToDirection() {
		int x = getPosition().x;
		int y = getPosition().y;
		
		if (direction.isUp()) y -= speed;
		if (direction.isDown()) y += speed;
		if (direction.isLeft()) x -= speed;
		if (direction.isRight()) x += speed;

		setPosition(
				Math.max(size/2, Math.min(gameArea.getWidth()-size/2, x)), 
				Math.max(size/2, Math.min(gameArea.getHeight()-size/2, y)));
	}

	@Override
	public void directionChanged(Direction oldDirection,
			Direction newDirection) {
		this.direction = newDirection;
	}

	@Override
	public void firePressed() {
		gameArea.addNewEnemy();
	}
	
	private Point mousePosition;
	
	public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
		updateCrossHairPosition();
	}
	
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		updateCrossHairPosition();
	}
	
	public void updateCrossHairPosition() {
		Vector2D vec = new Vector2D(position,  mousePosition).toLength(200).move(position);
		crossHair.setPosition(vec.asPoint());
	}

	
	void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2){

        Graphics2D g2d = (Graphics2D) g.create();

        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(x1, y1, x2, y2);

        g2d.dispose();
	}
	
	void draw(Graphics g) {
		
		crossHair.draw(g);
		g.setColor(new Color(0x80000000, true));
		drawDashedLine(g, position.x, position.y, crossHair.position.x, crossHair.position.y);
		
		g.setColor(color);
		Polygon2D fiveEdges = 
				Polygon2D.createNormalNPolygon(size/2, 5).
				rotate( (int)(((position.x+position.y)/(double)size/4)*360 )).
				move(position);
		g.fillPolygon(fiveEdges.asAwtPolygon());
		g.setColor(new Color(0x80000000, false));
		g.drawPolygon(fiveEdges.asAwtPolygon());
	}

	@Override
	void drawShadow(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
