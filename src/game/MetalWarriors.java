package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import level.MapLoader;
import level.World;
import listener.IGameListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.INotifier;
import logger.LogMessageType;
import logger.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import util.Const;
import controller.TestAi;
import controller.device.MechKeyboardController;
import environment.Movable;
import environment.character.mech.Mech;
import environment.character.mech.Nitro;
import exception.MapException;

/**
 * Main class for the game
 *
 * @author Daniel
 *
 */
public class MetalWarriors extends BasicGame implements
		IListenable<IGameListener> {
	private static final boolean DEBUG = true;

	public static MetalWarriors instance;
	public static final Logger logger = new Logger();
	private Movable _player;
	private World _map;
	private Viewport _viewport;
	private GameContainer _container;
	private Configuration _configuration;
	private ListenerSet<IGameListener> _listeners;

	static {
		if (DEBUG) {
			reloadDebugFlags(Const.DEBUGFLAGS);
		}
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
	 * @return the map the game is currently playing
	 */
	public World getMap() {
		return _map;
	}

	/**
	 * @return the viewport which determines which part of the playing field the
	 *         player can see
	 */
	public Viewport getViewport() {
		return _viewport;
	}

	/**
	 * @return the width of the window
	 */
	public int getWidth() {
		return _container.getWidth();
	}

	/**
	 * @return the height of the window
	 */
	public int getHeight() {
		return _container.getHeight();
	}

	public MetalWarriors() {
		super("Metal Warriors 3");
		instance = this;
	}

	public void loadConfiguration(final String path) {
		_configuration = new Configuration(Const.CONF_PATH);
		getListeners().notify(new INotifier<IGameListener>() {
			@Override
			public void notify(final IGameListener listener) {
				listener.onLoadConfig(_configuration);
			}
		});
	}

	@Override
	public void init(final GameContainer container) throws SlickException {
		_viewport = new Viewport(0, 0, container);
		_listeners = new ListenerSet<IGameListener>();
		_container = container;
		loadConfiguration(Const.CONF_PATH);
		loadMap("rsc/map/tm3.tmx");
		_player = new Nitro(new Vector2f(440, 480), "");
		// _player = new Nitro(new Vector2f(500, 120), "");
		_player.setController(new MechKeyboardController((Mech) _player,
				_configuration));
		/*_player.setController(new MechXboxPadController((Mech) _player,
				_configuration));*/
		for (int i = 0; i < 0; i++) {
			final Nitro n = new Nitro(new Vector2f(20 + i * _player.getWidth()
					+ 1, 450), "");
			n.setController(new TestAi(n));
		}
	}

	/**
	 * Loads the map from the given path and notifies all listeners that the map
	 * is now ready
	 *
	 * @param path
	 *            path to the map
	 */
	public void loadMap(final String path) {
		_map = MapLoader.load(path);
		_listeners.notify(new INotifier<IGameListener>() {

			@Override
			public void notify(final IGameListener listener) {
				listener.onLoadMap(_map);
			}
		});
	}

	/**
	 * This method is called every iteration and causes all controllers to
	 * consider updating their controlled objects
	 */
	@Override
	public void update(final GameContainer container, final int delta)
			throws SlickException {
		for (final Movable mv : Movable.instances) {
			mv.applyGravity(9.81f);
		}
		_listeners.notify(new INotifier<IGameListener>() {
			@Override
			public void notify(final IGameListener listener) {
				listener.onTick(container.getInput(), delta);
			}
		});
		_viewport.centerAround(_player);
	}

	/**
	 * This method is called every iteration after
	 * {@link #update(GameContainer, int)} and causes all renderer to render
	 * their held object
	 */
	@Override
	public void render(final GameContainer container, final Graphics g)
			throws SlickException {
		g.translate(_viewport.getPosition().x, _viewport.getPosition().y);
		_map.getRenderer().render(g, _viewport);
		_listeners.notify(new INotifier<IGameListener>() {
			@Override
			public void notify(final IGameListener listener) {
				listener.onRender(g, _viewport);
			}
		});
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

	@Override
	public ListenerSet<IGameListener> getListeners() {
		return _listeners;
	}

	/**
	 * Reloads the flags for the logger from the specified file
	 *
	 * @param file
	 *            file to load the flags from line by line
	 */
	private static void reloadDebugFlags(final String file) {
		final List<LogMessageType> flags = new ArrayList<LogMessageType>();
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(new File(file)));
			String line;
			while ((line = in.readLine()) != null) {
				try {
					flags.add(LogMessageType.valueOf(line));
				} catch (final IllegalArgumentException iae) {
					logger.print(
							String.format(
									"Could not parse debug flag '%s' from '%s'. Discarding.",
									line, file), LogMessageType.GENERAL_ERROR);
				}
			}
		} catch (final FileNotFoundException e) {
			logger.print(
					String.format(
							"Could not find file '%s' to parse debug flags from. Falling back to default flags.",
							file), LogMessageType.GENERAL_INFO);
		} catch (final IOException e) {
			logger.print(
					String.format(
							"Error when attempting to read debug flags from '%s': '%s'. Falling back to default flags.",
							file, e.getMessage()), LogMessageType.GENERAL_ERROR);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		for (final LogMessageType flag : flags) {
			logger.accept(flag);
		}
	}
}