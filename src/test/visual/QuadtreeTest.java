package test.visual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

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
import org.newdawn.slick.geom.Vector2f;

import util.Const;
import util.QuadTree;
import environment.IBounding;
import environment.Hitbox;

/**
 * Testclass for testing quadtrees. To be removed in final release.<br>
 * By default, blocks are solid as stored in the map-object.<br>
 * By right-clicking a block, its solidness can be toggled<br>
 * Left-clicking creates a rectangle at the click-position to check for
 * candidates.<br>
 * Also spawns particles flying around as stresstest for the FPS
 *
 * @author Daniel
 *
 */
public class QuadtreeTest extends BasicGame {
	public static final int PARTICLES = 100;
	private World _world;
	private QuadTree<Block> _qt;
	private Rectangle _r;
	private Hitbox hitbox;
	private Collection<Block> _collisions;

	public QuadtreeTest() {
		super("Quadtree Test");
	}

	public static void main(final String[] args) {

		try {
			final AppGameContainer app = new AppGameContainer(
					new QuadtreeTest());
			app.setDisplayMode(800, 800, false);
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
				for(Shape s : b.getHitbox().getShapes()) {
					g.fill(s);
				}
			}
		}
		Particle.drawAll(g);

	}

	@Override
	public void init(final GameContainer arg0) throws SlickException {
		_world = MapLoader.load(Const.RSC_PATH + "map/tm3.tmx");
		_qt = new QuadTree<Block>(null, new Rectangle(0, 0, _world.getWidth()
				* _world.getBlockWidth(), _world.getHeight()
				* _world.getBlockHeight()));
		_qt.addAll(Block.solidBlocks);
		Particle.create(arg0, PARTICLES, _qt);
		System.out.println("solid blocks: " + Block.solidBlocks.size());
		System.out.println("tree has size: " + _qt.size());
		assert Block.solidBlocks.size() == _qt.size();
		arg0.getInput().addMouseListener(this);
		_r = new Rectangle(0, 0, 0, 0);
		hitbox = new Hitbox(new Shape[]{_r});
	}

	@Override
	public void update(final GameContainer arg0, final int arg1)
			throws SlickException {
		final HashSet<Block> collisions = _qt.getCandidates(new IBounding() {

			@Override
			public Hitbox getHitbox() {
				return hitbox;
			}
		});
		_collisions = collisions;
		Particle.updateAll();
	}

	@Override
	public void mouseClicked(final int button, final int x, final int y,
			final int clickCount) {
		if (button == 0) {
			new ArrayList<Block>();
			_r = new Rectangle(x, y, 30, 30);
			hitbox = new Hitbox(new Shape[]{_r});
		} else {
			final Block b = _world.getBlockAt(x, y);
			b.setSolid(!b.isSolid());
			if (b.isSolid()) {
				_qt.add(b);
			} else {
				b.destroy();
				_qt.remove(b);
			}
		}
	}

	/**
	 * Renders a quadtree in white
	 *
	 * @param g
	 * @param qt
	 */
	public static void renderQuadTree(final Graphics g, final QuadTree<?> qt) {
		g.draw(qt.getBounds());
		if (qt.isSplit()) {
			for (final QuadTree<?> sub : qt.getNodes()) {
				renderQuadTree(g, sub);
			}
		}
	}

	static public class Particle implements IBounding {
		private static final CopyOnWriteArrayList<Particle> PARTICLES = new CopyOnWriteArrayList<Particle>();
		private static final int DISTANCE = 500;
		private static float FACTOR = 5f;
		Vector2f _p, _v;
		float _traveled;
		QuadTree<Block> _qt;
		GameContainer _gc;

		/**
		 * Creates a given amount of particles
		 *
		 * @param gc
		 *            game container
		 * @param amount
		 *            amount of particles to create
		 * @param qt
		 *            quadtree to check for collisions
		 */
		public static void create(final GameContainer gc, final int amount,
				final QuadTree<Block> qt) {
			for (int i = 0; i < amount; i++) {
				new Particle(qt, gc);
			}
		}

		/**
		 * Makes all particles move
		 */
		public static void updateAll() {
			for (final Particle p : PARTICLES) {
				p.update();
			}
		}

		/**
		 * Draws all particles
		 *
		 * @param g
		 */
		public static void drawAll(final Graphics g) {
			for (final Particle p : PARTICLES) {
				p.draw(g);
			}
		}

		/**
		 * Constructor
		 *
		 * @param qt
		 *            quadtree to check for collisions
		 * @param gc
		 *            gamecontainer to derive the width and height from
		 */
		public Particle(final QuadTree<Block> qt, final GameContainer gc) {
			final Random r = new Random();
			_qt = qt;
			_gc = gc;
			_p = new Vector2f(r.nextInt(gc.getWidth()), r.nextInt(gc
					.getHeight()));
			_v = new Vector2f(r.nextFloat() * FACTOR, r.nextFloat() * FACTOR);
			PARTICLES.add(this);

		}

		/**
		 * Makes the particle move. If it has traveled its maximum distance, it
		 * will be replaced with one randomly spawned new particle
		 */
		public void update() {
			_p.add(_v);
			_traveled += _v.length();
			if (_traveled >= DISTANCE) {
				PARTICLES.remove(this);
				create(_gc, 1, _qt);
			}
		}

		/**
		 * Draws the particle at its current position and marks all blocks it
		 * collides with
		 *
		 * @param g
		 */
		public void draw(final Graphics g) {
			g.setColor(Color.cyan);
			g.drawRect(_p.x, _p.y, 2, 2);
			final Collection<Block> col = _qt.getCandidates(this);
			g.setColor(Color.magenta);
			for (final Block b : col) {
				g.drawRect(b.getPosition().x, b.getPosition().y, b.getWidth(),
						b.getHeight());
			}

		}

		@Override
		public Hitbox getHitbox() {
			return new Hitbox(new Shape[]{new Rectangle(_p.x, _p.y, 2, 2)});
		}
	}
}
