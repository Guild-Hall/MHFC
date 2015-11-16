package mhfc.net.common.util.parsing.valueholders;

import java.util.function.Function;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;
import mhfc.net.common.util.parsing.expressions.Arguments;

public class FreeFunctionInvocation implements IValueHolder {

	private Function<Arguments, IValueHolder> function;

	// private

	@Override
	public Holder snapshot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IValueHolder snapshotClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getContainedClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
