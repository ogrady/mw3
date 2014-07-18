package game;

import exception.MapException;
import gamestates.MainMenuState;
import gamestates.PlayingState;
import logger.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import util.Const;

/**
 * Main class for the game
 *
 * @author Daniel
 *
 */
public class MetalWarriors extends StateBasedGame {
	// singleton instance
	public static MetalWarriors instance;
	// global logger-instance
	public static final Logger logger = new Logger();
	// current configuration which can be reloaded on the fly
	private final Configuration _configuration;
	// gamestates
	private PlayingState _playingState;
	private MainMenuState _mainMenuState;

	private static final boolean DEBUG = true;
	static {
		if (DEBUG) {
			logger.loadFlagsFromFile(Const.DEBUGFLAGS);
		}
	}

	/**
	 * @return the {@link GameState} that is active, when the game is played
	 */
	public PlayingState getPlayingState() {
		return _playingState;
	}

	/**
	 * @return the current configuration. The object itself might be replaced
	 *         when the game reloads the config. If your object is interested in
	 *         such changes you should let it implement {@link IGameListener}
	 *         and register as listener to wait for the onLoadConfig method
	 */
	public Configuration getConfiguration() {
		return _configuration;
	}

	/**
	 * Puts the game in the menu-state. A bit sketchy but works for testing the
	 * different states for now
	 */
	public void gotoMenu() {
		enterState(Const.MAIN_MENU_STATE_ID);
	}

	/**
	 * Constructor
	 */
	public MetalWarriors() {
		super("Metal Warriors 3");
		_configuration = new Configuration();
		loadConfiguration();
		instance = this;
	}

	/**
	 * Reloads the configuration from a confpath. The configuration itself will
	 * notify all of its listeners of this event.
	 */
	public void loadConfiguration() {
		_configuration.reload(Const.CONF_PATH);
	}

	@Override
	public void initStatesList(final GameContainer gc) throws SlickException {
		_playingState = new PlayingState();
		_mainMenuState = new MainMenuState();
		// order is important here to preserve the ids as set in the config!
		addState(_playingState);
		addState(_mainMenuState);
		enterState(Const.PLAYING_STATE_ID);
	}

	public static void main(final String[] args) throws MapException {
		// try {
		// String message = new String("Hello, Server!");
		// Socket clientSocket = new Socket("localhost",
		// MetalWarriorsServer.MW3_PORT);
		// DataOutputStream outToServer = new
		// DataOutputStream(clientSocket.getOutputStream());
		// outToServer.write(message.getBytes());
		// clientSocket.close();
		// } catch (UnknownHostException e1) {
		// e1.printStackTrace();
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }

		try {
			final AppGameContainer app = new AppGameContainer(
					new MetalWarriors());
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			app.setTargetFrameRate(60);
			app.start();
		} catch (final SlickException e) {
			e.printStackTrace();
		}
	}
}