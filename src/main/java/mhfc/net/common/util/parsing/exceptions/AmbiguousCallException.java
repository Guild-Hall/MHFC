package mhfc.net.common.util.parsing.exceptions;

public class AmbiguousCallException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -1847685382280895184L;

	public AmbiguousCallException() {
		super();
	}

	public AmbiguousCallException(String message, Throwable cause) {
		super(message, cause);
	}

	public AmbiguousCallException(String message) {
		super(message);
	}

	public AmbiguousCallException(Throwable cause) {
		super(cause);
	}

}
