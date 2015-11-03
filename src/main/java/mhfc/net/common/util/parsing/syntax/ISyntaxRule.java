package mhfc.net.common.util.parsing.syntax;

import mhfc.net.common.util.parsing.IExpression;

public interface ISyntaxRule {
	public static interface ISyntaxRuleInstance {
		int match(String toParse);

		IExpression representation();
	}

	ISyntaxRuleInstance newInstance();
}
