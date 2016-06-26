package mhfc.net.common.util.parsing.proxies;

import java.lang.invoke.MethodHandle;
import java.util.Objects;
import java.util.Optional;

import mhfc.net.common.util.parsing.Holder;
import mhfc.net.common.util.parsing.exceptions.MethodNotFoundException;
import mhfc.net.common.util.parsing.syntax.special.ISpecialMember;
import mhfc.net.common.util.reflection.FieldHelper;
import mhfc.net.common.util.reflection.MethodHelper;
import mhfc.net.common.util.reflection.OverloadedMethod;

public class StaticAccess implements ISpecialMember {
	private Class<?> clazz;

	public StaticAccess(Class<?> clazz) {
		this.clazz = Objects.requireNonNull(clazz);
	}

	@Override
	public Holder __getattr__(String member) throws Throwable {
		Optional<MethodHandle> f = FieldHelper.findStatic(clazz, member);
		if (f.isPresent()) {
			MethodHandle field = f.get();
			Class<?> fieldClazz = field.type().returnType();
			Object fieldValue = field.invoke();
			return Holder.makeUnboxer(fieldClazz).apply(fieldValue);
		}
		Optional<OverloadedMethod> methods = MethodHelper.findStatic(clazz, member);
		if (methods.isPresent()) {
			return Holder.valueOf(new MemberMethodProxy(methods.get()));
		}
		throw new MethodNotFoundException("static" + clazz.getName() + "." + member + " not found");
	}
}
