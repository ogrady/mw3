package listener;

import game.Configuration;

/**
 * Should be implemented by classes that wish to listen for game-events
 * 
 * @author Daniel
 * 
 */
public interface IGameListener extends IListener {
	public void onLoadConfig(Configuration conf);
}
