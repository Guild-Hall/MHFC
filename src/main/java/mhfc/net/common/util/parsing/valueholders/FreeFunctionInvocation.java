package mhfc.net.common.util.parsing.valueholders;

import java.util.Objects;
import java.util.function.Function;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.expressions.Arguments;

public class FreeFunctionInvocation implements IValueHolder {
	private final Function<Arguments, IValueHolder> function;
	private final Arguments args;

	public FreeFunctionInvocation(Function<Arguments, IValueHolder> function, IValueHolder... args) {
		this.function = Objects.requireNonNull(function);
		this.args = new Arguments(args);
	}

	private IValueHolder applied() {
		return this.function.apply(this.args);
	}

	@Override
	public Holder snapshot() {
		return applied().snapshot();
	}

	@Override
	public IValueHolder snapshotClass() {
		return applied().snapshotClass();
	}

	@Override
	public Class<?> getType() {
		return applied().getType();
	}
}
