package logger;

import java.io.PrintStream;

import util.EnumBitmask;

/**
 * When it comes to putting output to the console it can be annoying to have
 * remainders of debug-messages from other portions of the code (e.g. debugging
 * the physics and being confronted with a truckload of debug-messages from the
 * input-handling). Therefore instead of using System.out.println() a logger
 * should be issued.<br>
 * Loggers can be sensitive to several kinds of messages that can be activated
 * or deactived on the fly.<br>
 * When printing something to the logger it will check whether the given flag is
 * activated and print the message to stream if so or discard it otherwise. So
 * when it comes to debugging messages from other code-portions can simply be
 * turned off.<br>
 * Per default the logger prints to the default output-stream (System.out) but
 * can be reconfigured to put all output to a logfile.
 * 
 * @author Daniel
 * 
 */
public class Logger {
	private PrintStream _sink;
	private final EnumBitmask<LogMessageType> _accepted;

	/**
	 * Constructor which creates a default logger that prints to System.out<br>
	 * See {@link Logger#Logger(PrintStream)} for the activated default-flags
	 */
	public Logger() {
		this(System.out);
	}

	/**
	 * Constructor which creates a Logger that prints to the given stream.<br>
	 * Per default the following message-types are activated:<br>
	 * <ul>
	 * <li>GENERAL_DEBUG for general debug messages (discouraged)</li>
	 * <li>GENERAL_ERROR for unspecified errors, like printing out an exception</li>
	 * <li>GENERAL_INFO for info message like loadout</li>
	 * </ul>
	 * 
	 * @param sink
	 */
	public Logger(final PrintStream sink) {
		_accepted = new EnumBitmask<LogMessageType>();
		setSink(sink);
		accept(LogMessageType.GENERAL_DEBUG, LogMessageType.GENERAL_ERROR,
				LogMessageType.GENERAL_INFO);
	}

	/**
	 * Prints a message of the given type
	 * 
	 * @param message
	 *            message to print
	 * @param type
	 *            type under which the message should be printed. Message will
	 *            be discarded if the flag is not in the list of accepted flags
	 *            (see Logger{@link #accept(LogMessageType...)})
	 */
	public void print(final String message, final LogMessageType type) {
		if (_accepted.has(type)) {
			_sink.println(String.format("%s: %s", type, message));
		}
	}

	/**
	 * Prints a message under the GENERAL_DEBUG flag. Should only be used for
	 * quick debugging and replaced with a specific flag afterwards
	 * 
	 * @param message
	 *            the message to print
	 */
	@Deprecated
	public void print(final String message) {
		print(message, LogMessageType.GENERAL_DEBUG);
	}

	/**
	 * Sets a new sink which has to be null and un-errored. May be used to print
	 * to files
	 * 
	 * @param sink
	 *            the new sink aka output-stream
	 */
	public void setSink(final PrintStream sink) {
		if (sink != null && !sink.checkError()) {
			_sink = sink;
		}
	}

	/**
	 * Accepts a set of message-types
	 * 
	 * @param logMessageTypes
	 *            message-types to add to the list of accepted message-types
	 */
	public void accept(final LogMessageType... logMessageTypes) {
		for (final LogMessageType type : logMessageTypes) {
			_accepted.add(type);
		}
	}

	/**
	 * Ignores a set of message-types
	 * 
	 * @param logMessageTypes
	 *            message-types which should be ignored from now on
	 */
	public void ignore(final LogMessageType... logMessageTypes) {
		for (final LogMessageType type : logMessageTypes) {
			_accepted.remove(type);
		}
	}
}
