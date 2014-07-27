package test.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.newdawn.slick.geom.Path;

public class ShapeEdge {

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
		final Path p = new Path(5, 5);
		p.lineTo(5, 10);
		p.lineTo(10, 10);
		p.lineTo(10, 5);
		p.close();
		assertEquals(p.getMinX(), 5, 0);
		assertEquals(p.getMinY(), 5, 0);
		assertEquals(p.getMaxX(), 10, 0);
		assertEquals(p.getMaxY(), 10, 0);
	}
}
