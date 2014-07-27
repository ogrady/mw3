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
 * </p>
 *
 * @author Steven Lambert, Daniel
 *
 * @param <E>
 *            type of elements, this {@link QuadTree} can hold. Has to implement
 *            {@link IBounding}, to do intersection-checks with the subtrees
 */
public class QuadTree<E extends IBounding> implements Collection<E> {
	/**
	 * how many elements a tree has to hold before rejoining
	 */
	private final static int MIN_PER_TREE = 5;
	/**
	 * how many elements a tree holds before it splits
	 */
	private final static int MAX_PER_LEVEL = 20;
	/**
	 * how deep a tree can become by splitting
	 */
	private final static int MAX_LEVEL = 5;

	private final int _level;
	private int _size;
	private boolean _split;
	private Collection<E> _elements;
	private final Rectangle _bounds;
	private final QuadTree<E> _parent;
	private final QuadTree<E>[] _nodes;

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
	public QuadTree<E>[] getNodes() {
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
	 * @return the elements this tree holds. Contains only the elements that are
	 *         stored in this particular tree, not in the subtrees
	 */
	public Collection<E> getElements() {
		return _elements;
	}

	/**
	 * Collects all elements from the tree, including possible elements in the
	 * subtree. This makes this operation rather costly and it should be used
	 * with care.
	 *
	 * @return all elements in the tree, including the ones in all subtrees (no
	 *         duplicates)
	 */
	public Collection<E> getAllElements() {
		final HashSet<E> all = new HashSet<E>(_size);
		collectInto(all);
		return all;
	}

	public QuadTree() {
		this(null, new Rectangle(0, 0, 0, 0));
	}

	@SuppressWarnings("unchecked")
	public QuadTree(final QuadTree<E> parent, final Rectangle bounds) {
		_parent = parent;
		_level = parent == null ? 0 : parent.getLevel() + 1;
		_elements = new HashSet<E>();
		_bounds = bounds;
		_nodes = new QuadTree[4];
		_split = false;
	}

	/**
	 * Retrieves a list of objects from the tree, the passed element
	 * <strong>could</strong> collide with. This doesn't actually do any
	 * collision-check. It only narrows down the area in which we look for
	 * candidates.
	 *
	 * @param element
	 *            the element to check for collisions
	 * @return the list of candidates, the passed element could collide with
	 */
	public HashSet<E> getCandidates(final IBounding element) {
		final HashSet<E> candidates = new HashSet<E>();
		retrieve(candidates, element);
		return candidates;
	}

	/**
	 * Recursively browses through subtrees to find candidates. Entrance point
	 * is {@link #getCandidates(IBounding)}.
	 *
	 * @param returnObjects
	 *            the list of candidates, element could collide with. Filled by
	 *            reference
	 * @param element
	 *            the element to check for collisions
	 */
	private void retrieve(final HashSet<E> returnObjects,
			final IBounding element) {
		if (_split) {
			for (final int i : getIndices(element.getHitbox())) {
				_nodes[i].retrieve(returnObjects, element);
			}
		} else {
			returnObjects.addAll(_elements);
		}
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
		// after redistribution, each element is in one childnode. The parent
		// doesn't hold any further reference to the elements
		_elements.clear();
	}

	/**
	 * Joins the quadtree. This is accomplished by collecting all remaining
	 * elements from the subtrees and putting them back in this tree. This
	 * method should only be called after making sure that the tree must be
	 * joined, as it doesn't make guarantees that the tree will then be valid.<br>
	 * That means you can join a tree at any time while the subtrees are still
	 * full, which will result in an overflowed tree.
	 */
	private void join() {
		assert _size < MIN_PER_TREE;
		if (_split) {
			final Collection<E> bucket = new HashSet<E>(_size);
			collectInto(bucket);
			clear();
			_elements = bucket;
			// size is the same as before! But clear set it to 0
			_size = _elements.size();
		}
	}

	/**
	 * Puts all elements in this tree into the passed bucket (filled by
	 * reference). If split, all elements from the subtrees are added as well.
	 *
	 * @param bucket
	 *            the list to put the elements in
	 */
	private void collectInto(final Collection<E> bucket) {
		if (!_split) {
			bucket.addAll(_elements);
		} else {
			for (final QuadTree<E> _node : _nodes) {
				_node.collectInto(bucket);
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

		_nodes[0] = new QuadTree<E>(this, new Rectangle(x, y, width, height));
		_nodes[1] = new QuadTree<E>(this, new Rectangle(x + width, y, width,
				height));
		_nodes[2] = new QuadTree<E>(this, new Rectangle(x, y + height, width,
				height));
		_nodes[3] = new QuadTree<E>(this, new Rectangle(x + width, y + height,
				width, height));
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
		final List<Integer> indices = new ArrayList<Integer>(4);
		if (_split) {
			for (int i = 0; i < _nodes.length; i++) {
				// in contrast to rectangles, paths don't yield
				// intersect = true if they are fully contained. So we have to
				// check that too
				if (_nodes[i].getBounds().intersects(s)
						|| _nodes[i].getBounds().contains(s)) {
					indices.add(i);
				}
			}
		}
		/*
		if (indices.isEmpty()) {
			System.out.println("quadtree.getindices " + s);
		}*/
		return indices;
	}

	/**
	 * Adds an element recursively. That means, each element is contained within
	 * the parent tree, as well in all subtrees, the element intersects with.
	 */
	@Override
	public boolean add(final E e) {
		boolean added = false;
		if (_split) {
			for (final int i : getIndices(e.getHitbox())) {
				added = _nodes[i].add(e) || added;
			}
			if (added) {
				_size++;
			}
		} else {
			added = _elements.add(e);
			if (added) {
				_size++;
				if (_size > MAX_PER_LEVEL && _level < MAX_LEVEL) {
					assert !_split;
					redistribute();
				}
			}
		}
		return added;
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
		if (_split) {
			assert _elements.isEmpty();
			for (int i = 0; i < _nodes.length; i++) {
				_nodes[i].clear();
				_nodes[i] = null;
			}
		} else {
			_elements.clear();
		}
		_size = 0;
		_split = false;
	}

	/**
	 * Contained, if the toplevel-tree knows about the element.
	 */
	@Override
	public boolean contains(final Object o) {
		if (o instanceof IBounding) {
			return contains((IBounding) o);
		}
		return false;
	}

	public boolean contains(final IBounding b) {
		boolean contains = false;
		// if the object intersects with the bounds of the quadtree, it MIGHT be
		// in it. In that case, check if it is contained in the list of elements
		// or, if split, look for the subtrees if they have it. We can stop our
		// search as soon as one child contains the object.
		if (_bounds.intersects(b.getHitbox())) {
			if (!_split) {
				contains = _elements.contains(b);
			} else {
				final int i = 0;
				while (i < _nodes.length && !contains) {
					contains = _nodes[i].contains(b);
				}
			}
		}
		return contains;
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
		return _size == 0;
	}

	/**
	 * Uses the expensive {@link #getAllElements()}
	 */
	@Override
	public Iterator<E> iterator() {
		return getAllElements().iterator();
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
	 * least one subtree, from which it will be removed as well. If removing an
	 * element causes the tree to underflow and the tree is not the root, it
	 * will cause its parent to rejoin and redistribute all elements.
	 *
	 * @param b
	 *            the element to remove
	 * @return true, if the element could successfully be removed from the tree
	 *         and all subtrees containing it
	 */
	private boolean remove(final IBounding b) {
		boolean removed = true;
		if (!_split) {
			removed = _elements.remove(b);
		} else {
			for (final int i : getIndices(b.getHitbox())) {
				removed = removed && _nodes[i].remove(b);
			}
		}
		if (removed) {
			_size--;
			if (_parent != null && _size < MIN_PER_TREE) {
				join();
			}
		}
		assert _parent == null || _size >= MIN_PER_TREE || !_split;
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
		return _size;
	}

	/**
	 * Uses the costly {@link #getAllElements()}
	 */
	@Override
	public Object[] toArray() {
		return getAllElements().toArray();
	}

	/**
	 * Uses the costly {@link #getAllElements()}
	 */
	@Override
	public <T> T[] toArray(final T[] a) {
		return getAllElements().toArray(a);
	}
}
