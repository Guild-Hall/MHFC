package mhfc.net.common.util.parsing.proxies;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.valueholders.Arguments;
import mhfc.net.common.util.reflection.OverloadedMethod;

public class MemberMethodProxy {
	private OverloadedMethod methods;
	private Object instance;

	public MemberMethodProxy(OverloadedMethod methodSet, Object instance) {
		this.methods = methodSet;
		this.instance = instance;
	}

	public Holder __call__(Arguments arguments) {
		return methods.call(instance, arguments);
	}
}
