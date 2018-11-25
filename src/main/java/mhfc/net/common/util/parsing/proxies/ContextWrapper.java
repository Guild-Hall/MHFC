package mhfc.net.common.util.parsing.proxies;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.syntax.special.ISpecialMember;

import java.util.Objects;

public class ContextWrapper implements ISpecialMember {
	private final Context reference;

	public ContextWrapper(Context ctx) {
		this.reference = Objects.requireNonNull(ctx);
	}

	@Override
	public Holder __getattr__(String name) throws Throwable {
		return reference.getVar(name).snapshot();
	}
}
