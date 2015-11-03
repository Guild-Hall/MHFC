package mhfc.net.common.util.parsing.expressions;

import java.util.Arrays;
import java.util.Objects;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.IExpression;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.valueholders.MemberFunctionCall;

public class FunctionCall implements IExpression {
	private static IValueHolder[] resolveAll(Context ctx, IExpression... exprs) {
		return Arrays.stream(exprs).map(e -> e.resolveAgainst(ctx)).toArray(IValueHolder[]::new);
	}

	private final IExpression instance;
	private final String name;
	private final IExpression[] arguments;

	public FunctionCall(IExpression instance, String name, IExpression... arguments) {
		this.instance = Objects.requireNonNull(instance);
		this.name = Objects.requireNonNull(name);
		mhfc.net.common.util.Objects.requireNonNullDeep(arguments);
		this.arguments = Arrays.copyOf(arguments, arguments.length);
	}

	@Override
	public IValueHolder resolveAgainst(Context context) {
		return MemberFunctionCall.makeFunctionCall(instance.resolveAgainst(context), name,
				resolveAll(context, arguments));
	}

}
