package mhfc.net.common.util.parsing.expressions;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IExpression;
import mhfc.net.common.util.parsing.IValueHolder;

public class Noop implements IExpression {
	public static final Noop instance = new Noop();

	private Noop() {
	}

	@Override
	public IValueHolder resolveAgainst(Context context) {
		return Holder.empty();
	}

}
