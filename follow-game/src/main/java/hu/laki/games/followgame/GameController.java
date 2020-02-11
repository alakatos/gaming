package hu.laki.games.followgame;

import java.awt.Rectangle;

import hu.laki.games.followgame.model.Bullet;

public interface GameController {
	void addBullet(Bullet bullet);
	Rectangle getBounds();
}
