package util;

/**
 * Timer, that counts down from a value to zero.
 * 
 * @author Daniel
 * 
 */
public class Countdown {
	private long _current;
	private final long _max;

	/**
	 * The remaining milliseconds, until the countdown runs out
	 * 
	 * @return
	 */
	public long getCurrent() {
		return _current;
	}

	/**
	 * Maxvalue of the countdown. That's the initial value and the value, that
	 * will be set when the countdown is reset
	 * 
	 * @return
	 */
	public long getMax() {
		return _max;
	}

	/**
	 * Costructor
	 * 
	 * @param max
	 *            initial value
	 */
	public Countdown(final long max) {
		_max = max;
		_current = max;
	}

	/**
	 * @return true, if the countdown has reached zero already
	 */
	public boolean isTimedOut() {
		return _current <= 0;
	}

	/**
	 * Count down the remaining time. Will never drop below 0
	 * 
	 * @param delta
	 *            the milliseconds to substract from the remaining time
	 */
	public void tick(final int delta) {
		_current = Math.max(_current - delta, 0);
	}

	/**
	 * Resets the countdown back to max
	 */
	public void reset() {
		_current = _max;
	}

}
