package mhfc.net.common.util;

public class Objects {

	public static <T> T requireNonNullDeep(T obj) {
		java.util.Objects.requireNonNull(obj);
		Class<?> clazz = obj.getClass();
		if (!clazz.isArray() || clazz.getComponentType().isPrimitive()) {
			return obj;
		}
		Object[] asObjAr = (Object[]) obj;
		for (Object o : asObjAr) {
			requireNonNullDeep(o);
		}
		return obj;
	}

}
