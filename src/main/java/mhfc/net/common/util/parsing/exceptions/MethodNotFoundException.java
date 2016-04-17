package mhfc.net.common.util.parsing.exceptions;

public class MethodNotFoundException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 3600338922448146835L;

	public MethodNotFoundException() {
		super();
	}

	public MethodNotFoundException(String message) {
		super(message);
	}

	public MethodNotFoundException(Throwable cause) {
		super(cause);
	}

	public MethodNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
