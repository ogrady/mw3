package game;

import level.Map;
import level.MapLoader;
import listener.IGameListener;
import listener.IListenable;
import listener.ListenerSet;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import environment.Movable;
import environment.character.PlayerMechFactory;
import exception.MapException;

public class MetalWarriors extends BasicGame implements
		IListenable<IGameListener> {
	public static final String CONF_PATH = "rsc/conf.properties";

	public static MetalWarriors instance;

	private Movable player;
	private Map map;
	private Viewport viewport;
	private GameContainer container;
	private Configuration configuration;
	private ListenerSet<IGameListener> listeners;

	public Viewport getViewPort() {
		return viewport;
	}

	public int getWidth() {
		return container.getWidth();
	}

	public int getHeight() {
		return container.getHeight();
	}

	public MetalWarriors() {
		super("Metal Warriors 3");
		instance = this;
	}

	@Override
	public void init(final GameContainer _container) throws SlickException {
		try {
			viewport = new Viewport(0, 0, _container);
			listeners = new ListenerSet<IGameListener>();
			configuration = new Configuration(CONF_PATH);
			container = _container;
			player = PlayerMechFactory.create(500, 480,
					PlayerMechFactory.EMech.NITRO, configuration);
			map = MapLoader.load("rsc/map/tm3.tmx");
		} catch (final MapException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(final GameContainer container, final int delta)
			throws SlickException {
		player.getController().update(container.getInput(), delta);
		player.getRenderer().getCurrentAnimation().update(delta);
		viewport.centerAround(player);
		for (final Movable mv : Movable.instances) {
			mv.applyGravity(9.81f);
		}
	}

	@Override
	public void render(final GameContainer container, final Graphics g)
			throws SlickException {
		g.translate(viewport.getPosition().x, viewport.getPosition().y);
		map.getRenderer().render(g, viewport);
		player.getRenderer().render(g, viewport);
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
		return listeners;
	}
}