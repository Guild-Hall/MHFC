package mhfc.net.common.util.parsing.expressions;

import mhfc.net.common.util.parsing.Context;

public abstract class UnresolvedContextVariableException extends RuntimeException {
	public static class UnresolvedVariableException extends UnresolvedContextVariableException {
		/**
		 * 
		 */
		private static final long serialVersionUID = -6905348223715872735L;

		public UnresolvedVariableException(String name, Context ctx) {
			super(name, ctx);
		}
	}

	public static class UnresolvedFunctionException extends UnresolvedContextVariableException {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3380258168443277288L;

		public UnresolvedFunctionException(String name, Context ctx) {
			super(name, ctx);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5253485676324424830L;

	public final String name;
	public final Context ctx;

	public UnresolvedContextVariableException(String name, Context ctx) {
		super(name + " couldn't be found in the context.");
		this.name = name;
		this.ctx = ctx;
	}
}
