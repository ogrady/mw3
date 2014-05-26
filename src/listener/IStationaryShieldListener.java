package listener;

import environment.character.StationaryShield;

public interface IStationaryShieldListener extends IListener {
	void onDestruct(StationaryShield shield);
}
