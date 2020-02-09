package hu.zsomi.games.followgame;

import java.awt.Rectangle;

import hu.zsomi.games.followgame.model.Bullet;

public interface GameController {
	void addBullet(Bullet bullet);
	Rectangle getBounds();
}
