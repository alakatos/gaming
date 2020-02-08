package hu.zsomi.games.followgame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;

import hu.aventurin.gaming.gamecontroller.Direction;
import hu.aventurin.gaming.gamecontroller.GameControllerListener;
import hu.zsomi.games.geom.Location2D;
import hu.zsomi.games.geom.Polygon2D;
import hu.zsomi.games.geom.Vector2D;

public class Player extends Figure implements GameControllerListener {

	private Direction direction = Direction.NONE;
	private GameArea gameArea;
	private CrossHair crossHair;

	Player(Location2D position, int size, Color color, double speed, GameArea gameArea) {
		super(position, size, color, speed);
		this.gameArea = gameArea;
		crossHair = new CrossHair(position.move(50,50), 40, Color.BLACK);
	}

	void goToDirection() {
		int angle = direction.getAngle();
		if (angle > -1) {
			Vector2D speedVec = new Vector2D(getSpeed(),0).rotate(angle).mirrorToAxisX();
			Location2D newLocation = speedVec.move(getPosition()).getTargetPoint();
			int s = getSize();
			Rectangle bounds = new Rectangle(s/2, s/2,  gameArea.getWidth()-s, gameArea.getHeight()-s);
			setPosition(Math.max(bounds.x, Math.min(bounds.x+bounds.width, newLocation.getX())),
						Math.max(bounds.y, Math.min(bounds.y+bounds.height, newLocation.getY())));
		}

	}

	@Override
	public void directionChanged(Direction oldDirection, Direction newDirection) {
		this.direction = newDirection;
	}

	@Override
	public void firePressed() {
		gameArea.addBullet(shootBullet());
	}

	private Point mousePosition;

	public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
		updateCrossHairPosition();
	}

	@Override
	public void setPosition(double x, double y) {
		super.setPosition(x, y);
		updateCrossHairPosition();
	}

	public void updateCrossHairPosition() {
		Vector2D vec = new Vector2D(getPosition(), new Location2D(mousePosition)).
				toLength(200).
				move(getPosition());
		crossHair.setPosition(vec.getTargetPoint());
	}

	void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2) {

		Graphics2D g2d = (Graphics2D) g.create();

		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(x1, y1, x2, y2);

		g2d.dispose();
	}

	Polygon2D getPolygon() {
		return Polygon2D.createNormalNPolygon(getSize() / 2, 5)
				.rotate((int) (((getPosition().getX() + getPosition().getY()) / (double) getSize() / 4) * 360)).
				move(getPosition());

	}

	void draw(Graphics g) {

		crossHair.draw(g);
		g.setColor(new Color(0x80000000, true));
		drawDashedLine(g, getPosition().getXInt(), getPosition().getYInt(), crossHair.getPosition().getXInt(),
				crossHair.getPosition().getYInt());

		g.setColor(getColor());
		Polygon2D fiveEdges = getPolygon();
		g.fillPolygon(fiveEdges.asAwtPolygon());
		g.setColor(new Color(0x80000000, false));
		g.drawPolygon(fiveEdges.asAwtPolygon());
	}

	@Override
	public void doIteration() {
		goToDirection();
	}

	int weaponReloadTime = 500;
	long latesShot = 0;
	
	Bullet shootBullet() {
		if (System.currentTimeMillis() - latesShot > weaponReloadTime) {
			Location2D bulletStartPoint = new Vector2D(getPosition(), new Location2D(mousePosition)).toLength(getSize()/2).move(getPosition()).getTargetPoint();
			latesShot = System.currentTimeMillis();
			return new Bullet(bulletStartPoint, new Location2D(mousePosition), 6, 10);
		} else {
			return null;
		}
	}

}
