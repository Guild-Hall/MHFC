package mhfc.net.common.util.parsing.exceptions;

public class FieldNotFoundException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -7266214114317769310L;

	public FieldNotFoundException() {
		super();
	}

	public FieldNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FieldNotFoundException(String message) {
		super(message);
	}

	public FieldNotFoundException(Throwable cause) {
		super(cause);
	}

}
