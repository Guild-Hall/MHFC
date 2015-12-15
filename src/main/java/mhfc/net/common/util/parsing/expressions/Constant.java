package mhfc.net.common.util.parsing.expressions;

import java.util.Objects;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IExpression;

public class Constant implements IExpression {
	private final Holder value;

	public Constant(Holder holder) {
		this.value = Objects.requireNonNull(holder);
	}

	@Override
	public Holder resolveAgainst(Context context) {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
