package hu.zsomi.games.followgame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import hu.aventurin.gaming.gamecontroller.Direction;
import hu.aventurin.gaming.gamecontroller.GameControllerListener;

public class Player extends Figure implements GameControllerListener {

	
	private Direction direction = Direction.NONE;
	private GameArea gameArea;
	
	Player(Point position, int size, Color color, int speed, GameArea gameArea) {
		super(position, size, color, speed);
		this.gameArea = gameArea;
	}

	void goToDirection() {
		int x = getPosition().x;
		int y = getPosition().y;
		
		if (direction.isUp()) y -= speed;
		if (direction.isDown()) y += speed;
		if (direction.isLeft()) x -= speed;
		if (direction.isRight()) x += speed;

		setPosition(x, y);
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
	
	void draw(Graphics g) {
		Color savedColor = g.getColor();
		g.setColor(color);
		g.fillOval(position.x, position.y, size, size);
		g.setColor(savedColor);
	}
}
