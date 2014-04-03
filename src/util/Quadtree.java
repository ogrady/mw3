package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import environment.IBounding;

/**
 * Quadtrees are two-dimensional indices that allow you to retrieve elements
 * from the indexed collection, that are near a passed other element. This
 * allows for more efficient checks for collisions, by omitting elements, that
 * are not near our passed element, narrowing down the search-space.<br>
 * They effectively divide the search-space into smaller pieces, when it gets to
 * crowded, allowing for a binary search for possible collision-candidates.<br>
 * <p>
 * Implementation taken from <a href=
 * "http://gamedevelopment.tutsplus.com/tutorials/quick-tip-use-quadtrees-to-
 * detect-likely-collisions-in-2d-space--gamedev-374">Steven Lambert's blog</a>
 * </p>
 * <p>
 * Refinement in regards to adding and removing elements and distributing
 * elements over the subtrees upon splits.<br>
 * This enables us to reuse the tree, instead of re-building it in every
 * iteration.<br>
 * On the downside, this implementation has a far bigger usage of space, as, in
 * contrast to the original implementation, every tree keeps book over all the
 * elements contained inside the subtrees. This leads to more efficient HAS and
 * SIZE operation, as the collection to hold the elements is a {@link HashSet}
 * and avoids unnecessary descends into all subtrees, to remove elements.
 * </p>
 * <p>
 * When removing elements from the tree, subtrees are still not being joined
 * yet, if possible. This could lead to severe fracturing of the tree into very
 * small pieces that contain only one element, which basically renders this
 * structure useless. For now, rebuilding the index after heavy modification
 * should suffice. But in future implementations, re-joining trees should
 * seriously be considered and good criterions for doing so should be found.
 * </p>
 * 
 * @author Steven Lambert, Daniel
 * 
 * @param <E>
 *            type of elements, this {@link Quadtree} can hold. Has to implement
 *            {@link IBounding}, to do intersection-checks with the subtrees
 */
public class Quadtree<E extends IBounding> implements Collection<E> {
	/**
	 * how many elements a tree holds before it splits
	 */
	private final static int MAX_PER_LEVEL = 10;
	/**
	 * how deep a tree can become by splitting
	 */
	private final static int MAX_LEVEL = 5;

	private final int _level;
	private final Collection<E> _elements;
	private final Rectangle _bounds;
	private final Quadtree<E>[] _nodes;
	private boolean _split;

	/**
	 * @return the bounding box of the tree
	 */
	public Rectangle getBounds() {
		return _bounds;
	}

	/**
	 * @return the four subtrees, if split. Results in an empty array, if not
	 *         split
	 */
	public Quadtree<E>[] getNodes() {
		return _nodes;
	}

	/**
	 * @return whether the tree has subtrees or not
	 */
	public boolean isSplit() {
		return _split;
	}

	/**
	 * @return the depth of the tree within other trees
	 */
	public int getLevel() {
		return _level;
	}

	/**
	 * @return the elements this tree holds. Contains all elements that are
	 *         contained within the subtrees as well
	 */
	public Collection<E> getElements() {
		return _elements;
	}

	@SuppressWarnings("unchecked")
	public Quadtree(final int level, final Rectangle bounds) {
		_level = level;
		_elements = new HashSet<E>();
		_bounds = bounds;
		_nodes = new Quadtree[4];
		_split = false;
	}

	/**
	 * Retrieves a list of objects from the tree, the passed element
	 * <strong>could</strong> collide. This doesn't actually do any
	 * collision-check. It only narrows down the area in which we look for
	 * candidates.
	 * 
	 * @param returnObjects
	 *            the list of candidates, element could collide with
	 * @param element
	 *            the element to check for collisions
	 * @return the passed list for chaining
	 */
	public List<E> retrieve(final List<E> returnObjects, final IBounding element) {
		if (_split) {
			final List<Integer> indices = getIndices(element.getHitbox());
			for (final int i : indices) {
				_nodes[i].retrieve(returnObjects, element);
			}
		} else {
			returnObjects.addAll(_elements);
		}
		return returnObjects;
	}

	/**
	 * Redistributes all contained elements to the subtrees. If the quadtree is
	 * not split yet, it will be split now. Each element will still be contained
	 * in the parent-tree and can afterwards be contained in more than one
	 * subtree.
	 */
	private void redistribute() {
		if (!_split) {
			split();
		}
		assert _split;
		for (final E element : _elements) {
			final List<Integer> indices = getIndices(element.getHitbox());
			// if it was contained in the parent, it's contained in at least one
			// subtree
			assert indices.size() != 0;
			for (final int i : indices) {
				_nodes[i].add(element);
			}
		}
	}

	/**
	 * Splits the tree into 4 equally large subtrees. Doesn't do the
	 * redistribution of the elements yet!
	 */
	private void split() {
		final float width = _bounds.getWidth() / 2;
		final float height = _bounds.getHeight() / 2;
		final float x = _bounds.getX();
		final float y = _bounds.getY();

		_nodes[0] = new Quadtree<E>(_level + 1, new Rectangle(x, y, width,
				height));
		_nodes[1] = new Quadtree<E>(_level + 1, new Rectangle(x + width, y,
				width, height));
		_nodes[2] = new Quadtree<E>(_level + 1, new Rectangle(x, y + height,
				width, height));
		_nodes[3] = new Quadtree<E>(_level + 1, new Rectangle(x + width, y
				+ height, width, height));
		_split = true;
	}

	/**
	 * Generates a list of indices of all subtrees the passed {@link Shape}
	 * intersected with.
	 * 
	 * @param s
	 *            the {@link Shape} to check for
	 * @return a list of indices, ranging from 0 (doesn't touch the tree at all)
	 *         to 4 (touches all four subtrees) elements. Those are the indices
	 *         for the {@link #_nodes}.
	 */
	private List<Integer> getIndices(final Shape s) {
		final List<Integer> indices = new ArrayList<Integer>();
		if (_split) {
			for (int i = 0; i < _nodes.length; i++) {
				if (_nodes[i].getBounds().intersects(s)) {
					indices.add(i);
				}
			}
		}
		return indices;
	}

	/**
	 * Adds an element recursively. That means, each element is contained within
	 * the parent tree, as well in all subtrees, the element intersects with.
	 */
	@Override
	public boolean add(final E e) {
		// add it to the outer node in any case, for keeping the recursive
		// deletion going
		_elements.add(e);
		if (_split) {
			final List<Integer> indices = getIndices(e.getHitbox());
			for (final int i : indices) {
				_nodes[i].add(e);
			}
		} else {
			if (_elements.size() > MAX_PER_LEVEL && _level < MAX_LEVEL) {
				assert !_split;
				redistribute();
			}
		}
		return true;
	}

	@Override
	public boolean addAll(final Collection<? extends E> c) {
		boolean addedAll = true;
		for (final E e : c) {
			addedAll = addedAll && add(e);
		}
		return addedAll;
	}

	/**
	 * Clears the element-list and all subtrees, Then un-splits the tree.
	 */
	@Override
	public void clear() {
		_elements.clear();
		for (int i = 0; i < _nodes.length; i++) {
			if (_nodes[i] != null) {
				_nodes[i].clear();
				_nodes[i] = null;
			}
		}
		_split = false;
	}

	/**
	 * Contained, if the toplevel-tree knows about the element.
	 */
	@Override
	public boolean contains(final Object o) {
		return _elements.contains(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c) {
		boolean containsAll = true;
		final Iterator<?> it = c.iterator();
		while (it.hasNext() && containsAll) {
			containsAll = contains(it.next());
		}
		return containsAll;
	}

	/**
	 * Empty, when the tree contains no elements and the nodes don't hold any
	 * elements either
	 */
	@Override
	public boolean isEmpty() {
		return _elements.isEmpty();
	}

	/**
	 * Iterator of the toplevel-tree contains all elements. Makes no guarantee
	 * of any order!
	 */
	@Override
	public Iterator<E> iterator() {
		return _elements.iterator();
	}

	/**
	 * Doesn't bother trying, if the passed object is not even of type E. If so,
	 * cast once and use the other {@link #remove(IBounding)}-method, to avoid
	 * casting every time we descent deeper into the tree.
	 */
	@Override
	public boolean remove(final Object o) {
		if (o instanceof IBounding) {
			return remove((IBounding) o);
		}
		return false;
	}

	/**
	 * Removes an element from the tree. If it is split, it has to be in at
	 * least one subtree, from which it will be removed as well.
	 * 
	 * @param b
	 *            the element to remove
	 * @return true, if the element could successfully be removed from the tree
	 *         and all subtrees containing it
	 */
	private boolean remove(final IBounding b) {
		boolean removed = _elements.remove(b);
		if (removed && _split) {
			final List<Integer> indices = getIndices(b.getHitbox());
			for (final int i : indices) {
				removed = removed && _nodes[i].remove(b);
			}
		}
		return removed;
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		boolean allRemoved = true;
		for (final Object e : c) {
			allRemoved = allRemoved && remove(e);
		}
		return allRemoved;
	}

	@Override
	public boolean retainAll(final Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return _elements.size();
	}

	@Override
	public Object[] toArray() {
		return _elements.toArray();
	}

	@Override
	public <T> T[] toArray(final T[] a) {
		return _elements.toArray(a);
	}
}
