package mhfc.net.common.util.parsing.expressions;

import java.util.Objects;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.IExpression;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.valueholders.MemberAccess;

public class Access implements IExpression {
	private final IExpression instance;
	private final String name;

	public Access(IExpression instance, String name) {
		this.instance = Objects.requireNonNull(instance);
		this.name = Objects.requireNonNull(name);
	}

	@Override
	public IValueHolder resolveAgainst(Context context) {
		return new MemberAccess(instance.resolveAgainst(context), name);
	}

}
