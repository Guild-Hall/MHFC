package mhfc.net.common.util.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class MethodHelper {
	/**
	 * Only searches for non-static, accessible objects.
	 *
	 * @param clazz
	 *            the clazz to search (including parent classes)
	 * @param name
	 *            the name to search for.
	 * @return an empty Optional if no method with that name is found
	 */
	public static Optional<OverloadedMethod> find(Class<?> clazz, String name) {
		return findMatching(clazz, name, f -> !ReflectionHelper.isStatic(f));
	}

	public static Optional<OverloadedMethod> findStatic(Class<?> clazz, String name) {
		return findMatching(clazz, name, ReflectionHelper::isStatic);
	}

	public static Optional<OverloadedMethod> findMatching(Class<?> clazz, String name, Predicate<Method> predicate) {
		Method[] methods = clazz.getMethods();
		List<MethodHandle> foundMethods = new ArrayList<>();
		boolean anyWithName = false;
		for (Method m : methods) {
			if (!m.getName().equals(name)) {
				continue;
			}
			anyWithName = true;
			if (!predicate.test(m)) {
				continue;
			}
			try {
				foundMethods.add(ReflectionHelper.LOOKUP.unreflect(m));
			} catch (IllegalAccessException e) {
				continue;
			}
		}
		return anyWithName ? Optional.of(new OverloadedMethod(foundMethods)) : Optional.empty();
	}
}
