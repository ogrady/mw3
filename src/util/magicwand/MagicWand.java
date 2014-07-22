package util.magicwand;

import java.util.ArrayList;
import java.util.List;

import level.Block;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Path;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * The magic wand is a tool derived from the tool as known from GFX-editors,
 * such as Photoshop, to select adjacent, similar pixels. In contrast to the
 * classic magic wand, it doesn't select arbitrary pixels, but separates
 * transparent from intransparent pixels and computes the minimum bounding path
 * around the intransparent pixels. This is mainly used to derive hitboxes from
 * tiles of arbitrary shape for the map.<br>
 * The computed hitbox might sometimes be off by one pixel or such, but that
 * should be okay for our cases.<br>
 * The algorithm for the determination is pretty naive and this tool should
 * therefore be used with care in regards to running time. It might prove to be
 * reasonable to store hitboxes we have already computed, instead of computing
 * them multiple times (e.g. when using the same tile of a tileset for multiple
 * {@link Block}s).
 *
 * @author Daniel
 *
 */
public class MagicWand {
	private static final Criterion DEFAULT_CRITERION = new Criterion() {

		@Override
		public boolean accepted(final Color p) {
			return p.getAlpha() > 50;
		}
	};
	/**
	 * Criterion to check for pixels inside the selection
	 */
	private final Criterion _criterion;

	/**
	 * Constructor<br>
	 * Uses the {@link #DEFAULT_CRITERION}
	 */
	public MagicWand() {
		this(DEFAULT_CRITERION);
	}

	/**
	 * Constructor
	 *
	 * @param criterion
	 *            the {@link Criterion} after which we select pixels for the
	 *            selection
	 */
	public MagicWand(final Criterion criterion) {
		_criterion = criterion == null ? DEFAULT_CRITERION : criterion;
	}

	/**
	 * Determines the bounding shape of the passed image. That is the border
	 * around the solid pixels of the image, pictured with as few points as
	 * possible. Having a completely transparent (empty) image will result in an
	 * empty rectangle as determined shape.
	 *
	 * @param img
	 *            the image to determine the border for
	 * @return the outer border around all solid pixels of the passed image or
	 *         an empty rectangle, if the image is empty
	 */
	public Shape getBoundingShape(final Image img) {
		final List<Vector2f> corners = getCorners(img);
		return corners.isEmpty() ? new Rectangle(0, 0, 0, 0) : connect(corners);
	}

	/**
	 * Connects a list of points with each other in a manner that two points are
	 * as close as possible
	 *
	 * @param points
	 *            the list of points to connect
	 * @return a closes shape, spanning around all passed points
	 */
	private Path connect(final List<Vector2f> points) {
		final Vector2f first = points.remove(0);
		Vector2f next = first;
		final Path p = new Path(next.x, next.y);
		while (!points.isEmpty()) {
			next = getClosest(next, points);
			p.lineTo(next.x, next.y);
			points.remove(next);
		}
		p.close();
		return p;
	}

	/**
	 * Determines the closest vector in a list, relative to the passed vector
	 *
	 * @param of
	 *            the vector to get the nearest other vector for
	 * @param others
	 *            other vectors, not including the "of"
	 * @return the closest vector in "others" relative to "of"
	 */
	private Vector2f getClosest(final Vector2f of, final List<Vector2f> others) {
		assert !others.contains(of);
		Vector2f closest = others.get(0);
		for (int i = 0; i < others.size(); i++) {
			if (others.get(i).distance(of) < closest.distance(of)) {
				closest = others.get(i);
			}
		}
		return closest;
	}

	/**
	 * Determines the corners of an image. A corner is a pixel, that has less
	 * than 3 solid neighbours.
	 *
	 * @param img
	 *            the image to select the corners from
	 * @return a list of all determined corners
	 */
	private List<Vector2f> getCorners(final Image img) {
		final List<Vector2f> corners = new ArrayList<Vector2f>();
		for (int x = 0; x < img.getWidth(); x++) {
			for (int y = 0; y < img.getHeight(); y++) {
				if (validAndSolid(x, y, img)
						&& countSolidNeigbours(x, y, img) < 3) {
					corners.add(new Vector2f(x, y));
				}
			}
		}
		return corners;
	}

	/**
	 * Counts how many solid, adjacent neighbours a given pixel has.
	 *
	 * @param x
	 *            the x-coordinate
	 * @param y
	 *            the y-coordinate
	 * @param img
	 *            the image to check the pixel on
	 * @return the number of solid, adjacent pixels to the passed pixel 0 <=
	 *         result <= 4
	 */
	private int countSolidNeigbours(final int x, final int y, final Image img) {
		int solids = 0;
		if (validAndSolid(x + 1, y, img)) {
			solids++;
		}
		if (validAndSolid(x - 1, y, img)) {
			solids++;
		}
		if (validAndSolid(x, y + 1, img)) {
			solids++;
		}
		if (validAndSolid(x, y - 1, img)) {
			solids++;
		}
		return solids;
	}

	/**
	 * Checks, whether a pixel at a given position is valid and solid.
	 *
	 * @param x
	 *            x-coordinate of the pixel
	 * @param y
	 *            y-coordinate of the pixel
	 * @param img
	 *            image to get the pixel from
	 * @return true, if the passed coordinate is in bounds of the image and the
	 *         criterion accepts the pixel
	 */
	private boolean validAndSolid(final int x, final int y, final Image img) {
		return x < img.getWidth() && x >= 0 && y < img.getHeight() && y >= 0
				&& _criterion.accepted(img.getColor(x, y));
	}
}
