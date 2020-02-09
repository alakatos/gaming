package hu.zsomi.games.geom;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

public class Polygon2DTest {

	@Test
	public void testArea() {
		assertThat(Polygon2D.createNormalNPolygon(Math.sqrt(2), 4).calculateArea(), equalTo(4d));
		
		Polygon2D p = new Polygon2D(Arrays.asList(
				new Vector2D(1,0),
				new Vector2D(0,0),
				new Vector2D(0,1)
				));
		assertThat(p.calculateArea(), equalTo(0.5));
	}
}
