package gamestates;

import java.util.Random;

import level.MapLoader;
import level.World;
import listener.IListenable;
import listener.IPlayingStateListener;
import listener.ListenerSet;
import listener.notifier.INotifier;

import org.lwjgl.input.Controllers;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import renderer.slick.MapRenderer;
import util.Const;
import controller.IController;
import controller.TestAi;
import controller.device.MechKeyboardController;
import controller.device.MechXboxPadController;
import environment.Movable;
import environment.character.mech.Mech;
import environment.character.mech.Nitro;
import game.Configuration;
import game.MetalWarriors;
import game.Viewport;

/**
 * This is the {@link GameState} in which the actual game takes place. Instances
 * of ingame-objects will register as listener of this state, so that they will
 * only receive ticks while this state is active.
 *
 * @author Daniel
 *
 */
public class PlayingState extends BasicGameState implements IListenable<IPlayingStateListener> {
	private Movable _player;
	private World _map;
	private Viewport _viewport;
	private GameContainer _container;
	private ListenerSet<IPlayingStateListener> _listeners;

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

	/**
	 * Loads the map from the given path and notifies all listeners that the map
	 * is now ready
	 *
	 * @param path
	 *            path to the map
	 */
	public void loadMap(final String path) {
		_map = MapLoader.load(path);
		_listeners.notify(new INotifier<IPlayingStateListener>() {
			@Override
			public void notify(final IPlayingStateListener listener) {
				listener.onLoadMap(_map);
			}
		});
	}

	@Override
	public void init(final GameContainer gc, final StateBasedGame sbg) throws SlickException {
		if (!(sbg instanceof MetalWarriors)) {
			throw new RuntimeException(getClass().getSimpleName() + " can only be used with an instance of " + MetalWarriors.class.getSimpleName());
		}
		final MetalWarriors mw = (MetalWarriors) sbg;
		_viewport = new Viewport(0, 0, gc);
		_listeners = new ListenerSet<IPlayingStateListener>();
		_container = gc;
		_container.setVSync(true);
		loadMap("rsc/map/tm4.tmx");
		_map.loadBGM("rsc/sound/music/DST-ClubFight.ogg");
		_map.setRenderer(new MapRenderer(_map));
		// _map.playBGM();
		_player = new Nitro(new Vector2f(440, 480), "");
		// _player = new Nitro(new Vector2f(500, 120), "");
		spawnBots(20);
		selectInputDevice((Mech) _player, mw.getConfiguration());
	}

	/**
	 * Looks for a connected xbox-pad and uses it. If none is found, the
	 * keyboard will be used as controller
	 *
	 * @param mov
	 *            the movable to select the controller for
	 * @param conf
	 *            the configuration to fetch the key-binding for the controller
	 *            from
	 */
	private static void selectInputDevice(final Mech mov, final Configuration conf) {
		IController controller = null;
		int i = 0;
		while (i < Controllers.getControllerCount() && controller == null) {
			if (Controllers.getController(i).getName().toLowerCase().contains("xbox")) {
				controller = new MechXboxPadController(mov, conf);
			}
			i++;
		}
		if (controller == null) {
			controller = new MechKeyboardController(mov, conf);
		}
		mov.setController(controller);
	}

	@Override
	public void render(final GameContainer gc, final StateBasedGame sbg, final Graphics g) throws SlickException {
		g.translate(_viewport.getPosition().x, _viewport.getPosition().y);
		_map.getRenderer().render(g, _viewport);
		_listeners.notify(new INotifier<IPlayingStateListener>() {
			@Override
			public void notify(final IPlayingStateListener listener) {
				listener.onRender(g, _viewport);
			}
		});
	}

	@Override
	public void update(final GameContainer gc, final StateBasedGame sbg, final int delta) throws SlickException {
		_listeners.notify(new INotifier<IPlayingStateListener>() {
			@Override
			public void notify(final IPlayingStateListener listener) {
				listener.onTick(gc.getInput(), delta);
			}
		});
		_viewport.centerAround(_player);

	}

	@Override
	public int getID() {
		return Const.PLAYING_STATE_ID;
	}

	@Override
	public ListenerSet<IPlayingStateListener> getListeners() {
		return _listeners;
	}

	/**
	 * Spawns AI-controlled Nitros somewhere on the map and makes them partake
	 * in the game
	 *
	 * @param number
	 *            amount of bots
	 * @return the spawned bots (will be part of the game already)
	 */
	public Mech[] spawnBots(final int number) {
		final Random r = new Random();
		final Mech[] bots = new Mech[number];
		for (int i = 0; i < number; i++) {
			final int x = r.nextInt(_map.getPixelWidth() - 100) + 100;
			final int y = r.nextInt(_map.getPixelHeight() - 100) + 100;
			final Nitro n = new Nitro(new Vector2f(x, y), "Bot" + i);
			n.setController(new TestAi(n));
			bots[i] = n;
		}
		return bots;
	}

}
