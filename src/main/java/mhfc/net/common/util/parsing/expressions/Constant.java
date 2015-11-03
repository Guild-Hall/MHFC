package mhfc.net.common.util.parsing.expressions;

import java.util.Objects;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.IExpression;
import mhfc.net.common.util.parsing.IValueHolder;

public class Constant implements IExpression {
	private final IValueHolder value;

	public Constant(IValueHolder holder) {
		this.value = Objects.requireNonNull(holder);
	}

	@Override
	public IValueHolder resolveAgainst(Context context) {
		return value;
	}
}
