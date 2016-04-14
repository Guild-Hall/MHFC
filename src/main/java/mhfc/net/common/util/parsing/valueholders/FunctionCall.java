package mhfc.net.common.util.parsing.valueholders;

import java.util.Iterator;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class FunctionCall implements IValueHolder {

	@Override
	public Holder snapshot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}

	public static IValueHolder makeFunctionCall(IValueHolder callee, Iterator<IValueHolder> argumentsIt) {
		// TODO;
		return Holder.empty();
	}

}
