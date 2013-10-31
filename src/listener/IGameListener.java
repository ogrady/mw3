package listener;

import game.Configuration;

public interface IGameListener extends IListener {
	public void onLoadConfig(Configuration _conf);
}
