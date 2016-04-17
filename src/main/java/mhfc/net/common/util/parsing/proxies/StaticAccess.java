package mhfc.net.common.util.parsing.proxies;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
		this.clazz = clazz;
	}

	@Override
	public Holder __getattr__(String member) {
		Optional<Field> f = FieldHelper.findMatching(clazz, member);
		if (f.isPresent()) {
			Field field = f.get();
			Class<?> fieldClazz = field.getType();
			return Holder.catching(IllegalAccessException.class, () -> {
				Object fieldValue = field.get(null);
				return Holder.makeUnboxer(fieldClazz).apply(fieldValue);
			});
		}
		Optional<OverloadedMethod> methods = MethodHelper
				.findMatching(clazz, member, m -> Modifier.isStatic(m.getModifiers()));
		if (methods.isPresent()) {
			return Holder.valueOf(new MemberMethodProxy(methods.get(), null));
		}
		throw new MethodNotFoundException("static" + clazz.getName() + "." + member + " not found");
	}
}
