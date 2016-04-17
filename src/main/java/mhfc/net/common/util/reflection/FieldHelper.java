package mhfc.net.common.util.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;

public class FieldHelper {
	/**
	 * Searches for an accessible, non-static of the name on the class.
	 *
	 * @param clazz
	 * @param name
	 * @return An empty Optional if no such Field was found, otherwise the Field
	 */
	public static Optional<Field> findMatching(Class<?> clazz, String name) {
		try {
			Field f = clazz.getField(name);
			if (Modifier.isStatic(f.getModifiers())) {
				return Optional.empty();
			}
			return Optional.of(f);
		} catch (NoSuchFieldException nsef) {
			return Optional.empty();
		}
	}
}
