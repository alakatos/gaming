package hu.zsomi.gaming.geometry;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.awt.Point;

import org.junit.Test;

public class Location2DTest {
	
	Location2D loc;
	
	@Test
	public void testGetXY() {
		loc = new Location2D(3,4);
		assertThat(loc.getX(), equalTo(3d));
		assertThat(loc.getY(), equalTo(4d));
		loc = new Location2D(new Point(3,4));
		assertThat(loc.getX(), equalTo(3d));
		assertThat(loc.getY(), equalTo(4d));
	}
}
