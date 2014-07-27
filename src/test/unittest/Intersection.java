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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		final Path p = new Path(544, 384);
		p.lineTo(544, 399);
		p.lineTo(559, 399);
		p.lineTo(559, 399);
		p.lineTo(559, 384);
		p.close();
		final Shape[] s = new Shape[4];
		s[0] = new Rectangle(0, 0, 800, 560);
		s[1] = new Rectangle(800, 0, 800, 560);
		s[2] = new Rectangle(0, 560, 800, 560);
		s[3] = new Rectangle(800, 560, 800, 560);
		boolean intersects = false;
		for (final Shape shape : s) {
			intersects = shape.contains(p) || intersects;
		}
		assertEquals(true, intersects);
	}

}
