package mhfc.net.common.util.parsing.expressions;

import mhfc.net.common.util.parsing.Context;

public class UnresolvedContextVariableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5253485676324424830L;

	public UnresolvedContextVariableException(String message) {
		super(message);
	}

	public UnresolvedContextVariableException(String name, Context ctx) {
		super(name + " can't be found in the context.");
	}
}
