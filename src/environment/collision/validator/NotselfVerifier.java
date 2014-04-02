package environment.collision.validator;

import environment.Positionable;

public class NotselfVerifier extends CollisionVerifier {

	@Override
	public boolean passes(final Positionable me, final Positionable other) {
		return me != other;
	}

}
