package mhfc.net.common.util.parsing.syntax;

import mhfc.net.common.util.parsing.syntax.ISyntaxRule.ISyntaxRuleInstance;

public interface ISyntaxRule<E extends ISyntaxRuleInstance> {
	public static interface ISyntaxRuleInstance {
		/**
		 * Match the input string. This will mutate this instance to contain
		 * whatever it found. Returns the amount of code-points matched.
		 * 
		 * @param toParse
		 * @return
		 */
		int match(CharSequence toParse);
	}

	E newInstance();
}
