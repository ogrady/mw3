package environment.collision.verifier;

import environment.Positionable;

public class UncollidableValidator extends CollisionVerifier {

	@Override
	public boolean passes(final Positionable me, final Positionable other) {
		return false;
	}

}
