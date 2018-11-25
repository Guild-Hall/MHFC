package mhfc.net.common.util.parsing.syntax.operators;

import mhfc.net.common.util.parsing.syntax.literals.FunctionCallLiteral;
import mhfc.net.common.util.parsing.syntax.literals.IExpression;

import java.util.List;

/**
 * "call : ex"
 *
 * @author WorldSEnder
 *
 */
public class ArgumentContinuationOperator
		implements
		IBinaryOperator<FunctionCallLiteral, IExpression, FunctionCallLiteral> {

	@Override
	public FunctionCallLiteral with(FunctionCallLiteral call, IExpression valueW) {
		List<IExpression> args = call.getArgs();
		args.add(valueW);
		return new FunctionCallLiteral(call.getCallee(), args);
	}

}
