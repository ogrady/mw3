import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.junit.Test;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import util.QuadTree;
import environment.IBounding;

public class QuadTreeAdd {
	@Test
	public void test() {
		final Random r = new Random(100000);
		final QuadTree<IBounding> qt = new QuadTree<IBounding>();
		final int elements = 10;
		for (int i = 0; i < elements; i++) {
			qt.add(new IBounding() {
				@Override
				public Shape getHitbox() {
					return new Rectangle(r.nextInt(100), r.nextInt(100), 10, 10);
				}
			});
		}
		assertTrue(qt.size() == elements);
	}
}
