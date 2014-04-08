package environment.collider.collector;

import java.util.HashSet;

import util.QuadTree;
import environment.Positionable;
import environment.collision.verifier.CollisionVerifier;

public class QuadTreeCollector<E extends Positionable> extends
		CollisionCollector<E> {
	private final QuadTree<E> _quadtree;

	public QuadTreeCollector(final QuadTree<E> candidates,
			final CollisionVerifier... verifiers) {
		super(candidates, verifiers);
		_quadtree = candidates;
	}

	@Override
	public void collectionCollisions(final Positionable me,
			final HashSet<Positionable> collisions) {
		final HashSet<E> candidates = _quadtree.getCandidates(me);
		for (final E candidate : candidates) {
			if (collides(me, candidate)) {
				collisions.add(candidate);
			}
		}
	}
}
