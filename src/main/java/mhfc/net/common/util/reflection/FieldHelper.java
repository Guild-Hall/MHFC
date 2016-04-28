package mhfc.net.common.util.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.function.Predicate;

public class FieldHelper {
	/**
	 * Searches for an accessible, non-static of the name on the class.
	 *
	 * @param clazz
	 * @param name
	 * @return An empty Optional if no such Field was found, otherwise the Field
	 */
	public static Optional<MethodHandle> find(Class<?> clazz, String name) {
		return findMatching(clazz, name, f -> !ReflectionHelper.isStatic(f));
	}

	public static Optional<MethodHandle> findStatic(Class<?> clazz, String name) {
		return findMatching(clazz, name, ReflectionHelper::isStatic);
	}

	public static Optional<MethodHandle> findMatching(Class<?> clazz, String name, Predicate<Field> predicate) {
		try {
			Field f = clazz.getField(name);
			if (!predicate.test(f)) {
				return Optional.empty();
			}
			return Optional.of(ReflectionHelper.LOOKUP.unreflectGetter(f));
		} catch (NoSuchFieldException | IllegalAccessException nsef) {
			return Optional.empty();
		}
	}
}
