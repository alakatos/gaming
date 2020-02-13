package hul.laki.gaming.gravity;

import java.awt.Graphics;
import java.awt.geom.Point2D;

import hu.laki.gaming.geometry.Vector2D;

public abstract class Ball {
	private static final double DT = 0.1;
	private double mass;
	private Point2D location;
	private Vector2D speed;
	private Vector2D gravitalForce;
	private int visualSize;
	
	
	public Ball(Point2D location, double mass, Vector2D initialSpeed, int visualSize) {
		this.location =location;
		this.mass = mass;
		this.speed = initialSpeed;
		this.visualSize = visualSize;
	}
	
	public Point2D getLocation() {
		return location;
	}
	
	public void resetGravitalForce() {
		this.gravitalForce = new Vector2D(0,0);
	}

	public void resetGForce() {
		this.gravitalForce = new Vector2D(0,0);
	}

	public double getMass() {
		return mass;
	}
	
	public void addGForce(Vector2D gravitalForce) {
		this.gravitalForce = this.gravitalForce.add(gravitalForce);
	}

	void applyGravitalForce(Vector2D windowMovingVec) {
		//a=F/m
		//v=v+a*dt
		//s=v*dt
		Vector2D acceleration = gravitalForce.scale(gravitalForce.getLength()/mass);
		speed = speed.add(acceleration.scale(DT));
		location = speed.scale(DT).move(location).add(windowMovingVec).asPoint();
	}
	
	abstract void paint(Graphics g);

	protected int getVisualSize() {
		return visualSize;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getClass().getSimpleName() + " at " + location + " with mass " + mass;
	}


}
