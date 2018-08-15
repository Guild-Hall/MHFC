package mhfc.net.common.util.parsing.exceptions;

public class SyntaxErrorException extends IllegalArgumentException {

	/**
	 *
	 */
	private static final long serialVersionUID = -6676150622443075182L;

	public SyntaxErrorException() {
		super();
	}

	public SyntaxErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public SyntaxErrorException(String s) {
		super(s);
	}

	public SyntaxErrorException(Throwable cause) {
		super(cause);
	}

}
