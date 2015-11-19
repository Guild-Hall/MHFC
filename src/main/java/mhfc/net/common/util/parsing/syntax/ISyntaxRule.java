package mhfc.net.common.util.parsing.syntax;

import mhfc.net.common.util.parsing.IExpression;

public interface ISyntaxRule {
	public static interface ISyntaxRuleInstance {
		/**
		 * Match the input string. This will mutate this instance to contain
		 * whatever it found. Returns the amount of code-points matched.
		 * 
		 * @param toParse
		 * @return
		 */
		int match(String toParse);

		IExpression representation();
	}

	ISyntaxRuleInstance newInstance();
}
