package test.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Intersection {
	private Shape[] shapes;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		shapes = new Shape[4];
		shapes[0] = new Rectangle(2, 2, 4, 4);
		shapes[1] = new Rectangle(2, 8, 2, 4);
		shapes[2] = new Rectangle(5, 9, 4, 4);
		shapes[3] = new Rectangle(5, 5, 4, 2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPath() {
		final Path p = new Path(544, 384);
		p.lineTo(5, 3);
		p.lineTo(6, 10);
		p.lineTo(7, 11);
		p.lineTo(9, 3);
		p.close();
		assertEquals(true, intersectsWithAll(p));
	}

	@Test
	public void testUnion() {
		Rectangle r1, r2;
		r1 = new Rectangle(4, 6, 2, 2);
		r2 = new Rectangle(5, 7, 5, 2);
		final Shape union = r1.union(r2)[0];
		assertEquals(true, intersectsWithAll(union));
	}

	public void testRectangle() {
		assertEquals(true, intersectsWithAll(new Rectangle(4, 3, 10, 8)));
	}

	private boolean intersectsWithAll(final Shape s) {
		boolean intersects = false;
		for (final Shape shape : shapes) {
			// intersects = shape.contains(p) || intersects;
			intersects = shape.intersects(s) || intersects;
		}
		return intersects;
	}

}
