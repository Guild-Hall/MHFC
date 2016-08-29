package mhfc.net.common.util.parsing.proxies;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.valueholders.Arguments;
import mhfc.net.common.util.reflection.OverloadedMethod;

public class MemberMethodProxy {
	private OverloadedMethod methods;

	public MemberMethodProxy(OverloadedMethod methodSet) {
		this.methods = methodSet;
	}

	public Holder __call__(Arguments arguments) throws Throwable {
		Holder result = methods.call(arguments);
		return result;
	}

	@Override
	public String toString() {
		return methods.toString();
	}
}
