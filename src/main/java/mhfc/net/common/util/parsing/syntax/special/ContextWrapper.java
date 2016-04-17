package mhfc.net.common.util.parsing.syntax.special;

import java.util.Objects;

import mhfc.net.common.util.parsing.Context;
import mhfc.net.common.util.parsing.Holder;

public class ContextWrapper implements ISpecialMember {
	private final Context reference;

	public ContextWrapper(Context ctx) {
		this.reference = Objects.requireNonNull(ctx);
	}

	@Override
	public Holder __getattr__(String name) {
		return reference.getVar(name).snapshot();
	}
}
