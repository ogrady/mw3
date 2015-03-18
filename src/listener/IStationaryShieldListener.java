package listener;

import environment.character.StationaryShield;

public interface IStationaryShieldListener extends IListener {
	default void onDestruct(final StationaryShield shield) {
	}
}
