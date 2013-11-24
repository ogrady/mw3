package logger;

public enum LogMessageType {
	// debug flags for generic messages. Those are activated per default in
	// newly instantiated loggers. Their use is highly deprecated as it
	// tempts to run everything under "GENERAL_XYZ" so that the logger
	// becomes basically useless when trying to deactive messages from other
	// developers. When you create a new area in the code
	// feel free to create a new debug-flag and activate it in your logger
	// to debug away. But you can use these flags when the
	// info/debug-message/error really is general and can be printed even in
	// the release.
	GENERAL_INFO, GENERAL_DEBUG, GENERAL_ERROR,
	// debug flags for specific implementation details (may be extended if
	// needed
	PHYSICS_DEBUG, INPUT_DEBUG, GFX_DEBUG
}
