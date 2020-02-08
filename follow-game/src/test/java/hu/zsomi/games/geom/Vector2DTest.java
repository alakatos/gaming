package hu.zsomi.games.geom;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class Vector2DTest {
	
	Vector2D vector;
	
	@Test
	public void testAngle() {
		vector = new Vector2D(1,0);
		assertThat(vector.getAngle(), equalTo(0d));
		vector = new Vector2D(0,1);
		assertThat(vector.getAngle(), equalTo(Math.PI/2));
		vector = new Vector2D(-1,0);
		assertThat(vector.getAngle(), equalTo(Math.PI));
		vector = new Vector2D(0,-1);
		assertThat(vector.getAngle(), equalTo(Math.PI*3/2));
	}
	
	@Test
	public void testAngleGrad() {
		vector = new Vector2D(1,0);
		assertThat(vector.getAngleGrad(), equalTo(0));
		vector = new Vector2D(0,1);
		assertThat(vector.getAngleGrad(), equalTo(90));
		vector = new Vector2D(-1,0);
		assertThat(vector.getAngleGrad(), equalTo(180));
		vector = new Vector2D(0,-1);
		assertThat(vector.getAngleGrad(), equalTo(270));

		vector = new Vector2D(1,1);
		assertThat(vector.getAngleGrad(), equalTo(45));
		vector = new Vector2D(-1,1);
		assertThat(vector.getAngleGrad(), equalTo(135));
		vector = new Vector2D(-1,-1);
		assertThat(vector.getAngleGrad(), equalTo(225));
		vector = new Vector2D(1,-1);
		assertThat(vector.getAngleGrad(), equalTo(315));
	}
	
	@Test
	public void testLength() {
		vector = new Vector2D(1,0);
		assertThat(vector.getLength(), equalTo(1d));
		vector = new Vector2D(1,1);
		assertThat(vector.getLength(), equalTo(Math.sqrt(2)));
		vector = new Vector2D(-1,1);
		assertThat(vector.getLength(), equalTo(Math.sqrt(2)));
	}

	@Test
	public void testRotate() {
		double sqrt2 = Math.sqrt(2);
		vector = new Vector2D(0,1).rotate(90);
		assertThat(vector, equalTo(new Vector2D(-1,0)));
		vector = new Vector2D(0,1).rotate(45);
		assertThat(vector, equalTo(new Vector2D(-sqrt2/2,sqrt2/2)));
		vector = new Vector2D(1,0).rotate(135);
		assertThat(vector, equalTo(new Vector2D(-sqrt2/2,sqrt2/2)));
		vector = new Vector2D(1,0).rotate(225);
		assertThat(vector, equalTo(new Vector2D(-sqrt2/2,-sqrt2/2)));
		vector = new Vector2D(1,1).rotate(90);
		assertThat(vector, equalTo(new Vector2D(-1,1)));

		vector = new Vector2D(sqrt2,0).rotate(45);
		assertThat(vector, equalTo(new Vector2D(1,1)));
		vector = new Vector2D(1,1).rotate(270);
		assertThat(vector, equalTo(new Vector2D(1,-1)));
	}

	@Test
	public void testRotateZeroVec() {
		for (int i=0; i< 360; i++) {
			assertThat(new Vector2D(0,0).rotate(i*3), equalTo(new Vector2D(0,0)));
		}
	}

	@Test
	public void testScale() {
		vector = new Vector2D(1,1).scale(10);
		assertThat(vector, equalTo(new Vector2D(10,10)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testScaleFailsWithNegativeValue() {
		vector = new Vector2D(1,1).scale(-1);
	}
}
