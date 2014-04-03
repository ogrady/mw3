package test;

import java.util.ArrayList;

import level.Block;
import level.MapLoader;
import level.World;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import util.Const;
import util.Quadtree;
import environment.IBounding;

/**
 * Testclass for testing quadtrees. To be removed in final release.<br>
 * By default, blocks are solid as stored in the map-object.<br>
 * By right-clicking a block, its solidness can be toggled<br>
 * Left-clicking creates a rectangle at the click-position to check for
 * candidates
 * 
 * @author Daniel
 * 
 */
public class QuadtreeTest extends BasicGame {
	private World _world;
	private Quadtree<Block> _qt;
	private Rectangle _r;
	private ArrayList<Block> _collisions;

	public QuadtreeTest() {
		super("Quadtree Test");
	}

	public static void main(final String[] args) {

		try {
			final AppGameContainer app = new AppGameContainer(
					new QuadtreeTest());
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			app.setTargetFrameRate(60);
			app.start();
		} catch (final SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(final GameContainer container, final Graphics g)
			throws SlickException {
		_world.getTiledMap().render(0, 0);
		g.setColor(Color.white);
		renderQuadTree(g, _qt);
		g.setColor(Color.green);
		g.draw(_r);
		if (_collisions != null) {
			g.setColor(Color.red);
			for (final Block b : _collisions) {
				g.fill(b.getHitbox());
			}
		}

	}

	@Override
	public void init(final GameContainer arg0) throws SlickException {
		_world = MapLoader.load(Const.RSC_PATH + "map/tm3.tmx");
		_qt = new Quadtree<Block>(0, new Rectangle(0, 0, _world.getWidth()
				* _world.getBlockWidth(), _world.getHeight()
				* _world.getBlockHeight()));
		_qt.addAll(Block.solidBlocks);
		System.out.println("solid blocks: " + Block.solidBlocks.size());
		System.out.println("tree has size: " + _qt.size());
		arg0.getInput().addMouseListener(this);
		_r = new Rectangle(0, 0, 0, 0);
	}

	@Override
	public void update(final GameContainer arg0, final int arg1)
			throws SlickException {
		final ArrayList<Block> collisions = new ArrayList<Block>();
		_qt.retrieve(collisions, new IBounding() {

			@Override
			public Shape getHitbox() {
				return _r;
			}
		});
		System.out.println("collision candidates: " + collisions.size());
		_collisions = collisions;

	}

	@Override
	public void mouseClicked(final int button, final int x, final int y,
			final int clickCount) {
		if (button == 0) {
			new ArrayList<Block>();
			_r = new Rectangle(x, y, 30, 30);
		} else {
			final Block b = _world.getBlockAt(x, y);
			b.setSolid(!b.isSolid());
			if (b.isSolid()) {
				_qt.add(b);
			} else {
				_qt.remove(b);
			}
		}
	}

	private void renderQuadTree(final Graphics g, final Quadtree<?> qt) {
		g.draw(qt.getBounds());
		if (qt.isSplit()) {
			for (final Quadtree<?> sub : qt.getNodes()) {
				renderQuadTree(g, sub);
			}
		}
	}
}
