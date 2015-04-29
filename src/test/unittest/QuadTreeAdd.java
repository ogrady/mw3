package test.unittest;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import java.util.ArrayList;

import org.junit.Test;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import util.QuadTree;
import environment.IBounding;
import environment.Hitbox;

public class QuadTreeAdd {
	@Test
	public void test() {
		final Random r = new Random(100000);
		final QuadTree<IBounding> qt = new QuadTree<IBounding>();
		final int elements = 10;
		for (int i = 0; i < elements; i++) {
			qt.add(new IBounding() {
				@Override
				public Hitbox getHitbox() {
					return new Hitbox(new Rectangle(r.nextInt(100), r.nextInt(100), 10, 10));
				}
			});
		}
		assertTrue(qt.size() == elements);
	}
}
