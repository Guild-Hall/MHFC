package mhfc.net.common.util.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
	public static Optional<OverloadedMethod> findMatching(Class<?> clazz, String name) {
		return findMatching(clazz, name, m -> !Modifier.isStatic(m.getModifiers()));
	}

	public static Optional<OverloadedMethod> findMatching(Class<?> clazz, String name, Predicate<Method> predicate) {
		Method[] methods = clazz.getMethods();
		List<Method> foundMethods = new ArrayList<>();
		boolean anyWithName = false;
		for (Method m : methods) {
			if (m.getName().equals(name)) {
				anyWithName = true;
				if (predicate.test(m)) {
					foundMethods.add(m);
				}
			}
		}
		return anyWithName ? Optional.of(new OverloadedMethod(foundMethods)) : Optional.empty();
	}
}
