package mhfc.net.common.util.parsing.syntax.literals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.valueholders.FunctionCall;

public class FunctionCallLiteral implements IExpression {
	private List<IExpression> arguments;
	private IExpression callee;

	public FunctionCallLiteral(IExpression callee, IExpression... expressions) {
		this.callee = callee;
		this.arguments = new ArrayList<>(Arrays.asList(expressions));
	}

	public FunctionCallLiteral(IExpression callee, List<IExpression> expressions) {
		this.callee = callee;
		this.arguments = expressions;
	}

	public IExpression getCallee() {
		return callee;
	}

	public List<IExpression> getArgs() {
		return arguments;
	}

	@Override
	public void prettyPrint(Formatter formatter) {
		callee.prettyPrint(formatter);
		formatter.format("(");
		for (Iterator<IExpression> argIt = arguments.iterator(); argIt.hasNext();) {
			IExpression arg = argIt.next();
			arg.prettyPrint(formatter);
			if (argIt.hasNext()) {
				formatter.format(", ");
			}
		}
		formatter.format(")");
	}

	@Override
	public IValueHolder asValue(IValueHolder ctx) {
		return FunctionCall.makeFunctionCall(
				callee.asValue(ctx),
				arguments.stream().map(e -> e.asValue(ctx)).toArray(IValueHolder[]::new));
	}

}
