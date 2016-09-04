package mhfc.net.common.util.parsing;

import mhfc.net.common.util.parsing.exceptions.SyntaxErrorException;

public class BoundExpressionTranslator {

	private IValueHolder contextVar;
	private ExpressionTranslator translator;

	public BoundExpressionTranslator(Context ctx) {
		contextVar = Holder.valueOf(ctx.getWrapper());
		translator = new ExpressionTranslator();
	}

	public IValueHolder parse(String expression) throws SyntaxErrorException {
		return translator.parse(expression, contextVar);
	}
}
