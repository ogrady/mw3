package game;

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

import environment.Movable;
import environment.character.PlayerMechFactory;
import exception.MapException;

/**
 * Main class for the game
 * 
 * @author Daniel
 * 
 */
public class MetalWarriors extends BasicGame implements
		IListenable<IGameListener> {
	public static final String CONF_PATH = "rsc/conf.properties";

	public static MetalWarriors instance;
	public static final Logger logger = new Logger();
	private Movable _player;
	private World _map;
	private Viewport _viewport;
	private GameContainer _container;
	private Configuration _configuration;
	private ListenerSet<IGameListener> _listeners;

	// change debugging flags here if needed
	static {
		logger.accept(
		// LogMessageType.PHYSICS_DEBUG,
		LogMessageType.INPUT_DEBUG);
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
	 * @return the viewport which determines which part of the playing field the
	 *         player can see
	 */
	public Viewport getViewPort() {
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
		_configuration = new Configuration(CONF_PATH);
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
		loadConfiguration(CONF_PATH);
		_player = PlayerMechFactory.create(500, 480,
				PlayerMechFactory.EMech.NITRO, _configuration);
		_map = MapLoader.load("rsc/map/tm3.tmx");
	}

	/**
	 * This method is called every iteration and causes all controllers to
	 * consider updating their controlled objects
	 */
	@Override
	public void update(final GameContainer container, final int delta)
			throws SlickException {
		_listeners.notify(new INotifier<IGameListener>() {
			@Override
			public void notify(final IGameListener listener) {
				listener.onTick(container.getInput(), delta);
			}
		});
		for (final Movable mv : Movable.instances) {
			mv.applyGravity(9.81f);
		}
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
		_player.getRenderer().render(g, _viewport);
	}

	public static void main(final String[] args) throws MapException {
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
}