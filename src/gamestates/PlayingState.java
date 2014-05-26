package gamestates;

import level.MapLoader;
import level.World;
import listener.IListenable;
import listener.IPlayingStateListener;
import listener.ListenerSet;
import listener.notifier.INotifier;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import util.Const;
import controller.TestAi;
import controller.device.MechKeyboardController;
import environment.Movable;
import environment.character.mech.Mech;
import environment.character.mech.Nitro;
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
public class PlayingState extends BasicGameState implements
		IListenable<IPlayingStateListener> {
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
	public void init(final GameContainer gc, final StateBasedGame sbg)
			throws SlickException {
		if (!(sbg instanceof MetalWarriors)) {
			throw new RuntimeException(getClass().getSimpleName()
					+ "can only be used with an instance of "
					+ MetalWarriors.class.getSimpleName());
		}
		final MetalWarriors mw = (MetalWarriors) sbg;
		_viewport = new Viewport(0, 0, gc);
		_listeners = new ListenerSet<IPlayingStateListener>();
		_container = gc;
		loadMap("rsc/map/tm3.tmx");
		_player = new Nitro(new Vector2f(440, 480), "");
		// _player = new Nitro(new Vector2f(500, 120), "");
		_player.setController(new MechKeyboardController((Mech) _player, mw
				.getConfiguration()));
		/*_player.setController(new MechXboxPadController((Mech) _player,
				_configuration));*/
		for (int i = 0; i < 7; i++) {
			final Nitro n = new Nitro(new Vector2f(20 + i * _player.getWidth()
					+ 1, 450), "");
			n.setController(new TestAi(n));
		}

	}

	@Override
	public void render(final GameContainer gc, final StateBasedGame sbg,
			final Graphics g) throws SlickException {
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
	public void update(final GameContainer gc, final StateBasedGame sbg,
			final int delta) throws SlickException {
		for (final Movable mv : Movable.instances) {
			mv.applyGravity(9.81f);
		}
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

}
