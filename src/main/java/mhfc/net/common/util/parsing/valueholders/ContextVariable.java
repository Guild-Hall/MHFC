package mhfc.net.common.util.parsing.valueholders;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.IValueHolder;

public class ContextVariable implements IValueHolder {
	private Context context;
	private String name;

	public ContextVariable(Context context, String name) {
		this.context = context;
		this.name = name;
	}

	private IValueHolder get() {
		return context.getVar(name);
	}

	public String getName() {
		return name;
	}

	@Override
	public Holder snapshot() throws Throwable {
		return get().snapshot();
	}
}
