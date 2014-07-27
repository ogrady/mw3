package level;

import java.util.ArrayList;
import java.util.HashSet;

import listener.IBlockListener;
import listener.IListenable;
import listener.ListenerSet;
import listener.notifier.INotifier;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import renderer.DefaultRenderer;
import util.Const;
import util.magicwand.MagicWand;
import environment.Positionable;
import environment.collision.DefaultCollider;
import environment.collision.DestructableBlockCollider;
import environment.collision.NeverCollider;

/**
 * Block of which a map consists of. They hold information such as whether they
 * are destructable or not
 *
 * @author Daniel
 *
 */
public class Block extends Positionable implements IListenable<IBlockListener> {
	public static final ArrayList<Block> instances = new ArrayList<Block>();
	public static final HashSet<Block> solidBlocks = new HashSet<Block>();
	private final World _map;
	private boolean _solid, _destructable;
	private final int _xIndex, _yIndex;
	private final ListenerSet<IBlockListener> _listeners;
	private Shape _hitbox;

	/**
	 * Replaces the old hitbox with a new one. NULL will be ignored
	 *
	 * @param hitbox
	 *            the new shape that represents the hitbox of this block
	 */
	public void setHitbox(final Shape hitbox) {
		if (hitbox == null) {
			_hitbox = hitbox;
		}
	}

	/**
	 * Derives the hitbox from a passed image (the tile used in the map for this
	 * block)
	 *
	 * @param img
	 *            the tile-image for this block to derive the hitbox from. See
	 *            {@link MagicWand} for more detail
	 */
	public void computeHitbox(final Image img) {
		setHitbox(new MagicWand().getBoundingShape(img, (int) getPosition().x,
				(int) getPosition().y));
	}

	/**
	 * Blocks have fixed instances of {@link Shape} as hitbox, instead of
	 * creating a new {@link Rectangle} through this getter as in
	 * {@link Positionable#getHitbox()}
	 */
	@Override
	public Shape getHitbox() {
		return _hitbox;
	}

	/**
	 * @return the x-coordinate this block has in the grid of the map (not to
	 *         confuse with the absolute position as received by
	 *         {@link #getPosition()} which is the upper left corner of the
	 *         block in absolute pixels)
	 */
	public int getX() {
		return _xIndex;
	}

	/**
	 * @return the y-coordinate this block has in the grid of the map (not to
	 *         confuse with the absolute position as received by
	 *         {@link #getPosition()} which is the upper left corner of the
	 *         block in absolute pixels)
	 */
	public int getY() {
		return _yIndex;
	}

	/**
	 * @return the map this block resides in
	 */
	public World getMap() {
		return _map;
	}

	/**
	 * @return whether this block is solid
	 */
	public boolean isSolid() {
		return _solid;
	}

	/**
	 * @param _solid
	 *            set a block solid or not
	 */
	public void setSolid(final boolean solid) {
		_solid = solid;
		if (solid) {
			solidBlocks.add(this);
			setCollider(isDestructable() ? new DestructableBlockCollider(this)
			: new DefaultCollider<Block>(this));
		} else {
			solidBlocks.remove(this);
			setCollider(new NeverCollider(this));
		}
		_listeners.notify(new INotifier<IBlockListener>() {

			@Override
			public void notify(final IBlockListener listener) {
				listener.onChangeSolidness(Block.this, _solid);
			}
		});
	}

	/**
	 * @return whether this block is destructable
	 */
	public boolean isDestructable() {
		return _destructable;
	}

	/**
	 * Sets whether a block is destructed upon receiving damage. The collider
	 * will be replaced accordingly
	 *
	 * @param destructable
	 *            set a block destructable or not
	 */
	public void setDestructable(final boolean destructable) {
		_destructable = destructable;
		setCollider(_destructable ? new DestructableBlockCollider(this)
		: new DefaultCollider<Block>(this));
	}

	/**
	 * Constructor
	 *
	 * @param x
	 *            x-coordinate within the map-grid
	 * @param y
	 *            y-coordinate within the map-grid
	 * @param map
	 *            map in which the block is contained
	 */
	public Block(final int x, final int y, final World map) {
		super(new Vector2f(x * map.getBlockWidth(), y * map.getBlockHeight()),
				map.getBlockWidth(), map.getBlockHeight());
		_xIndex = x;
		_yIndex = y;
		_map = map;
		_listeners = new ListenerSet<IBlockListener>();
		_hitbox = super.getHitbox();
		instances.add(this);
	}

	/**
	 * Destroying a {@link Block} is NOT equal to calling it's destructor!<br>
	 * It just removes the image and makes if undestructable and unblocking.<br>
	 * Destroyed {@link Block}s will have a {@link DefaultRenderer}.<br>
	 * The tile-id on the corresponding layer must be invisible on the
	 * {@value Const#MAP_LAYER_DESTRUCTABLE}, or else whatever image on that
	 * position will be used for destroyed {@link Block}s.
	 */
	public void destroy() {
		if (_destructable) {
			setDestructable(false);
			setSolid(false);
			setRenderer(new DefaultRenderer());
			_map.getTiledMap().setTileId(
					_xIndex,
					_yIndex,
					_map.getTiledMap().getLayerIndex(
							Const.MAP_LAYER_DESTRUCTABLE), 0);
		}
	}

	@Override
	public void destruct() {
		super.destruct();
		instances.remove(this);
	}

	@Override
	public String toString() {
		return String.format("Block at (%d|%d)", _xIndex, _yIndex);
	}

	@Override
	public ListenerSet<IBlockListener> getListeners() {
		return _listeners;
	}
}
