package mhfc.net.common.util.reflection;

import java.io.Serializable;
import java.util.*;

public class ClassHelper {
	// primitive class -> super types
	private final static Map<Class<?>, Set<Class<?>>> PRIMITIVE_SUPERTYPE_LOOKUP;
	// primitive class -> boxed
	private final static Map<Class<?>, Class<?>> BOXED_LOOKUP;
	// boxed -> primitve class
	private final static Map<Class<?>, Class<?>> UNBOXED_LOOKUP;

	private static void ADD_BOXING_RELATION(Class<?> primitive, Class<?> boxed) {
		BOXED_LOOKUP.put(primitive, boxed);
		UNBOXED_LOOKUP.put(boxed, primitive);
	}

	static {
		// 4.10.1
		PRIMITIVE_SUPERTYPE_LOOKUP = new HashMap<>();
		List<Class<?>> tempList = new ArrayList<>();
		PRIMITIVE_SUPERTYPE_LOOKUP.put(double.class, new HashSet<>(tempList));
		tempList.add(double.class);
		PRIMITIVE_SUPERTYPE_LOOKUP.put(float.class, new HashSet<>(tempList));
		tempList.add(float.class);
		PRIMITIVE_SUPERTYPE_LOOKUP.put(long.class, new HashSet<>(tempList));
		tempList.add(long.class);
		PRIMITIVE_SUPERTYPE_LOOKUP.put(int.class, new HashSet<>(tempList));
		tempList.add(int.class);
		PRIMITIVE_SUPERTYPE_LOOKUP.put(char.class, new HashSet<>(tempList));
		PRIMITIVE_SUPERTYPE_LOOKUP.put(short.class, new HashSet<>(tempList));
		tempList.add(short.class);
		PRIMITIVE_SUPERTYPE_LOOKUP.put(byte.class, new HashSet<>(tempList));
		PRIMITIVE_SUPERTYPE_LOOKUP.put(void.class, Collections.emptySet());

		BOXED_LOOKUP = new HashMap<>();
		UNBOXED_LOOKUP = new HashMap<>();
		ADD_BOXING_RELATION(boolean.class, Boolean.class);
		ADD_BOXING_RELATION(byte.class, Byte.class);
		ADD_BOXING_RELATION(short.class, Short.class);
		ADD_BOXING_RELATION(char.class, Character.class);
		ADD_BOXING_RELATION(int.class, Integer.class);
		ADD_BOXING_RELATION(long.class, Long.class);
		ADD_BOXING_RELATION(float.class, Float.class);
		ADD_BOXING_RELATION(double.class, Double.class);
	}

	/**
	 * §4.10, T <: S. Or in laymans terms: the class T is considered to be a subtype of class S, or if T is a primitive
	 * class, widening convertible. Every class is its own subtype.
	 *
	 * @param clazzS
	 * @param clazzT
	 * @return
	 */
	public static boolean isSubtype(Class<?> clazzS, Class<?> clazzT) {
		Objects.requireNonNull(clazzS);
		Objects.requireNonNull(clazzT);
		if (isIdentityConvertible(clazzS, clazzT)) {
			return true;
		}
		// 4.10.3
		if (clazzT.isArray()) {
			if (clazzS.isArray()) {
				return isSubtype(clazzS.getComponentType(), clazzT.getComponentType());
			}
			return isSubtype(Object.class, clazzS) || isSubtype(Cloneable.class, clazzS)
					|| isSubtype(Serializable.class, clazzS);
		}
		// 4.10.2
		if (!clazzT.isPrimitive()) {
			return isWideningReferenceConvertible(clazzS, clazzT);
		}
		// 4.10.1
		return isWideningPrimitiveConvertible(clazzS, clazzT);
	}

	/**
	 * §5.1.1 T is the class to convert from, S the class to convert to
	 *
	 * @param clazzS
	 * @param clazzT
	 * @return
	 */
	public static boolean isIdentityConvertible(Class<?> clazzS, Class<?> clazzT) {
		return clazzS.equals(clazzT);
	}

	/**
	 * §5.1.2 T is the class to convert from, S the class to convert to
	 *
	 * @param clazzS
	 * @param clazzT
	 * @return
	 */
	public static boolean isWideningPrimitiveConvertible(Class<?> clazzS, Class<?> clazzT) {
		return clazzT.isPrimitive() && PRIMITIVE_SUPERTYPE_LOOKUP.get(clazzT).contains(clazzS);
	}

	/**
	 * §5.1.5 T is the class to convert from, S the class to convert to
	 *
	 * @param clazzS
	 * @param clazzT
	 * @return
	 */
	public static boolean isWideningReferenceConvertible(Class<?> clazzS, Class<?> clazzT) {
		return !clazzT.isPrimitive() && clazzS.isAssignableFrom(clazzT);
	}

	public static Optional<Class<?>> asBoxed(Class<?> primitive) {
		return BOXED_LOOKUP.containsKey(primitive) ? Optional.of(BOXED_LOOKUP.get(primitive)) : Optional.empty();
	}

	public static Optional<Class<?>> asUnboxed(Class<?> boxed) {
		return UNBOXED_LOOKUP.containsKey(boxed) ? Optional.of(UNBOXED_LOOKUP.get(boxed)) : Optional.empty();
	}

	/**
	 * §5.3 T is the class to convert from, S the class to convert to
	 *
	 * @param clazzS
	 * @param clazzT
	 * @return
	 */
	public static boolean isMethodInvocationConvertible(Class<?> clazzS, Class<?> clazzT) {
		if (isIdentityConvertible(clazzS, clazzT) //
				|| isWideningPrimitiveConvertible(clazzS, clazzT) //
				|| isWideningReferenceConvertible(clazzS, clazzT)) {
			return true;
		}
		Optional<Class<?>> boxedT = asBoxed(clazzT);
		if (boxedT.isPresent()) {
			return isWideningReferenceConvertible(clazzS, boxedT.get());
		}
		Optional<Class<?>> unboxedT = asUnboxed(clazzT);
		if (unboxedT.isPresent()) {
			return isWideningPrimitiveConvertible(clazzS, unboxedT.get());
		}
		return false;
	}
}
