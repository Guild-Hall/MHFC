package mhfc.net.common.util.parsing.syntax.operators;

import mhfc.net.common.util.parsing.syntax.literals.FunctionCallLiteral;
import mhfc.net.common.util.parsing.syntax.literals.IExpression;

/**
 * "ex1 | ex2"
 *
 * @author WorldSEnder
 *
 */
public class FunctionOperator implements IBinaryOperator<IExpression, IExpression, FunctionCallLiteral> {

	@Override
	public FunctionCallLiteral with(IExpression valueV, IExpression valueW) {
		return new FunctionCallLiteral(valueW, valueV);
	}

}
