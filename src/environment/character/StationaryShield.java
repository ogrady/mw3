package environment.character;

import java.util.ArrayList;

import level.World;
import listener.IGameListener;
import listener.IListenable;
import listener.IStationaryShieldListener;
import listener.ListenerSet;
import listener.notifier.INotifier;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import renderer.slick.StationaryShieldRenderer;
import util.Const;
import environment.Positionable;
import environment.collision.StationaryShieldCollider;
import game.Configuration;
import game.MetalWarriors;
import game.Viewport;

/**
 * Stationary shields, that can be spawned by Nitro and absorb a certain amount
 * of damage before being destroyed.<br>
 * Even if they are not being destroyed by damage, they will disappear after a
 * certain time to live (ttl).<br>
 *
 *
 * @author Daniel
 *
 */
// TODO implement an IShieldListener for when a shield vanishes, to be able to
// track how many shields a Nitro currently has
public class StationaryShield extends Positionable implements IGameListener,
IListenable<IStationaryShieldListener> {
	public static final ArrayList<StationaryShield> instances = new ArrayList<StationaryShield>();
	private final ListenerSet<IStationaryShieldListener> _listeners;
	private long _ttl;
	public int _hp;

	/**
	 * Constructor
	 *
	 * @param position
	 *            position of the shield. Will be copied and therefore has no
	 *            reference-connection to the passed vector
	 */
	public StationaryShield(final Vector2f position) {
		super(position.copy(), 10, 60);
		_listeners = new ListenerSet<IStationaryShieldListener>();
		_ttl = Const.NITRO_SHIELD_TTL;
		_hp = Const.NITRO_SHIELD_HP;
		setCollider(new StationaryShieldCollider(this));
		setRenderer(new StationaryShieldRenderer(this));
		instances.add(this);
	}

	@Override
	public void onLoadConfig(final Configuration conf) {
		// shield doesn't care
	}

	@Override
	public void onLoadMap(final World map) {
		// shield doesn't give two fucks
	}

	@Override
	public void onTick(final Input input, final int delta) {
		this._ttl -= delta;
		if (this._ttl <= 0) {
			this.destruct();
		}

	}

	@Override
	public void onRender(final Graphics g, final Viewport vp) {
		_renderer.render(g, vp);
	}

	@Override
	public void destruct() {
		instances.remove(this);
		MetalWarriors.instance.getListeners().unregisterListener(this);
		_listeners.notify(new INotifier<IStationaryShieldListener>() {
			@Override
			public void notify(final IStationaryShieldListener listener) {
				listener.onDestruct(StationaryShield.this);
			}
		});
		super.destruct();
	}

	@Override
	public ListenerSet<IStationaryShieldListener> getListeners() {
		return _listeners;
	}
}
