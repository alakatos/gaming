package hu.zsomi.games.followgame.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import hu.aventurin.gaming.gamepad.Direction;
import hu.aventurin.gaming.gamepad.GamePadListener;
import hu.zsomi.games.followgame.GameController;
import hu.zsomi.games.followgame.Renderer;
import hu.zsomi.gaming.geometry.Polygon2D;
import hu.zsomi.gaming.geometry.Vector2D;

public class Player extends Figure implements GamePadListener {

	private Direction direction = Direction.NONE;
	private GameController gameCtrl;
	private CrossHair crossHair;

	public Player(Point2D position, int size, Color color, double speed, GameController gameCtrl) {
		super(position, size, color, speed);
		this.gameCtrl = gameCtrl;
		crossHair = new CrossHair(new Point2D.Double(50,50), 40, Color.BLACK);
	}

	void goToDirection() {
		int angle = direction.getAngle();
		if (angle > -1) {
			Vector2D speedVec = new Vector2D(getSpeed(),0).rotate(angle).mirrorToAxisX();
			Point2D newLocation = speedVec.move(getPosition()).getPoint();
			int s = getSize();
			Rectangle bounds = new Rectangle(s/2, s/2,  gameCtrl.getBounds().width-s, gameCtrl.getBounds().height-s);
			setPosition(Math.max(bounds.x, Math.min(bounds.x+bounds.width, newLocation.getX())),
						Math.max(bounds.y, Math.min(bounds.y+bounds.height, newLocation.getY())));
		}

	}

	@Override
	public void directionChanged(int gamePadId, Direction oldDirection, Direction newDirection) {
		this.direction = newDirection;
	}

	@Override
	public void firePressed(int gamePadId) {
		gameCtrl.addBullet(shootBullet());
	}

	private Point2D mousePosition;

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
		Vector2D vec = new Vector2D(getPosition(), mousePosition).
				toLength(200).
				move(getPosition());
		crossHair.setPosition(vec.getPoint());
	}


	public Polygon2D getPolygon() {
		return Polygon2D.createRegularPolygon(getSize() / 2, 5)
				.rotate((int) (((getPosition().getX() + getPosition().getY()) / (double) getSize() / 4) * 360)).
				move(getPosition());

	}


	@Override
	public void doIteration() {
		goToDirection();
	}

	int weaponReloadTime = 500;
	long latesShot = 0;
	
	Bullet shootBullet() {
		if (System.currentTimeMillis() - latesShot > weaponReloadTime) {
			Point2D bulletStartPoint = new Vector2D(getPosition(), mousePosition).toLength(getSize()/2).move(getPosition()).getPoint();
			latesShot = System.currentTimeMillis();
			return new Bullet(bulletStartPoint, mousePosition, 6, 10);
		} else {
			return null;
		}
	}

	
	public Renderer getRenderer() {
		return new Renderer() {
			private void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2) {

				Graphics2D g2d = (Graphics2D) g.create();

				Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 9 }, 0);
				g2d.setStroke(dashed);
				g2d.drawLine(x1, y1, x2, y2);

				g2d.dispose();
			}
			@Override
			public void render(Graphics g) {
				crossHair.getRenderer().render(g);
				g.setColor(new Color(0x80000000, true));
				drawDashedLine(g, (int)getPosition().getX(), (int)getPosition().getY(), (int)crossHair.getPosition().getX(),
						(int)crossHair.getPosition().getY());

				g.setColor(getColor());
				Polygon2D fiveEdges = getPolygon();
				g.fillPolygon(fiveEdges.asAwtPolygon());
				g.setColor(new Color(0x80000000, false));
				g.drawPolygon(fiveEdges.asAwtPolygon());
			}
		};
	}


}
