package mhfc.net.common.util.parsing.syntax;

import mhfc.net.common.util.parsing.IExpression;

public class ConstantExpr implements ISyntaxRule {
	public static class ConstantExprParse implements ISyntaxRuleInstance {
		private StringBuilder encountered = new StringBuilder();
		private boolean done = false;

		@Override
		public int match(String toMatch) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public IExpression representation() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static final ConstantExpr instance = new ConstantExpr();

	public static ISyntaxRuleInstance createInstance() {
		return instance.newInstance();
	}

	@Override
	public ConstantExprParse newInstance() {
		return new ConstantExprParse();
	}

}
