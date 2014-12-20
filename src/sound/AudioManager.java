package sound;

/**
 * Manages the sounds for ONE specific object. More specifically: it arranges
 * the action-sound-synapsis for one object. The {@link AudioManager} is
 * therefore part of the View and is interchangable.
 *
 * @author Daniel
 *
 * @param <T>
 *            type of objects this {@link AudioManager} manages the sounds for
 */
abstract public class AudioManager<T> {
	protected final T _target;

	/**
	 * Constructor
	 *
	 * @param target
	 *            the {@link AudioManager} manages the sounds for
	 */
	public AudioManager(final T target) {
		_target = target;
	}

}
