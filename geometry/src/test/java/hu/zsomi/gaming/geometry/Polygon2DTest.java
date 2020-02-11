package hu.zsomi.gaming.geometry;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Polygon2DTest {

	@Test
	public void testArea() {
		assertThat(Polygon2D.createRegularPolygon(Math.sqrt(2), 4).calculateArea(), equalTo(4d));
		
		Polygon2D p = new Polygon2D(Arrays.asList(
				new Vector2D(1,0),
				new Vector2D(0,0),
				new Vector2D(0,1)
				));
		assertThat(p.calculateArea(), equalTo(0.5));
	}

	@Test
	public void testCutToNPieces() {
		for (int i = 3; i < 8; i++) {
			Polygon2D polygon = Polygon2D.createRegularPolygon(10, i);
			double parentPolyArea = polygon.calculateArea();
			List<Polygon2D> triangles = polygon.cutRadiallyToNEqualAreaTriangles();
			assertThat(triangles.size(), equalTo(i));
			for (Polygon2D triangle : triangles) {
				System.out.println(triangle);
				assertThat(triangle.getPoints().size(), equalTo(3));
				assertThat(triangle.calculateArea(), closeTo(parentPolyArea/i, 0.000000001));
			}
		}
	}
}
