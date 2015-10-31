package mhfc.net.common.util.parsing.expressions;

import java.util.Objects;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IExpression;
import mhfc.net.common.util.parsing.IValueHolder;

public class Variable implements IExpression {
	private final String name;

	public Variable(String name) {
		this.name = Objects.requireNonNull(name);
	}

	@Override
	public IValueHolder resolveAgainst(Context context) {
		return context.hasVar(name) ? context.getVar(name) : Holder.empty();
	}

}
