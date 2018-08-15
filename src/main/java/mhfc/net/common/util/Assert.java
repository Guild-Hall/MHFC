package mhfc.net.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Assert {
	private static final Logger LOGGER = LogManager.getLogger();

	public static AssertionError createUnreachableError(String reason, Object... params) {
		String formattedReason = LOGGER.getMessageFactory().newMessage(reason, params).getFormattedMessage();
		AssertionError error = new AssertionError("Unreachable code reached: " + formattedReason);
		return error;
	}

	public static void unreachable(String reason, Object... params) {
		throw createUnreachableError(reason, params);
	}

	public static void logUnreachable(String reason, Object... params) {
		LOGGER.fatal("Unreachable code reached", createUnreachableError(reason, params));
	}

}
