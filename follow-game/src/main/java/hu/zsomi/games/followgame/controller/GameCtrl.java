package hu.zsomi.games.followgame.controller;

import java.awt.Rectangle;

import hu.zsomi.games.followgame.model.Bullet;

public interface GameCtrl {
	void addBullet(Bullet bullet);

	Rectangle getBounds();
}
