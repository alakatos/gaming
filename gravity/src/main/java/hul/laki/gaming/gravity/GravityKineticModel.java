package hul.laki.gaming.gravity;

import java.util.ArrayList;
import java.util.List;

import hu.aventurin.gaming.gamepad.Direction;
import hu.aventurin.gaming.gamepad.GamePadListener;
import hu.laki.gaming.geometry.Vector2D;

public class GravityKineticModel implements GamePadListener {

	private List<Ball> balls = new ArrayList<>();
	private Vector2D windowMoveVector = new Vector2D(0,0);
	
	void addBall(Ball ball){
		balls.add(ball);
	}
	
	public List<Ball> getBalls() {
		return balls;
	}

	private double g = 6.6726;
	
	void calculateNextFrame() {
		for (Ball ball: balls) {
			ball.resetGForce();
		}
		
		for (int i = 0; i < balls.size(); i++) {
			for (int j = i+1; j < balls.size(); j++) {
				Ball bA = balls.get(i);
				Ball bB = balls.get(j);
				Vector2D gForceBtwn2 = new Vector2D(bA.getLocation(), bB.getLocation());
				double distance = gForceBtwn2.getLength()*100;
				gForceBtwn2 = gForceBtwn2.toLength(g*bA.getMass()*bB.getMass()).scale(1/(distance*distance));
				int maxForce = 300;
				if (gForceBtwn2.getLength() > maxForce) {
					gForceBtwn2 = gForceBtwn2.toLength(maxForce);
				}
				bA.addGForce(gForceBtwn2);
				bB.addGForce(gForceBtwn2.invert());
			}
		}
		
		for (Ball ball: balls) {
			ball.applyGravitalForce(windowMoveVector);
		}		
	}

	public void clear() {
		balls.clear();
	}
	

	@Override
	public void directionChanged(int gamePadId, Direction oldDirection, Direction newDirection) {
		int angle = newDirection.getAngle();
		if (angle == -1) {
			windowMoveVector = new Vector2D(0,0);
		} else {
			windowMoveVector = new Vector2D(5,0).rotate(angle).mirrorToAxisY();
		}
	}

	@Override
	public void firePressed(int gamePadId) {
		clear();
	}

}
