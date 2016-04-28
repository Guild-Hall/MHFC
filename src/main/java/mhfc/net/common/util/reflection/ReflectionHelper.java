package mhfc.net.common.util.reflection;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public class ReflectionHelper {
	public static final MethodHandles.Lookup LOOKUP = MethodHandles.publicLookup();

	public static boolean isStatic(Member m) {
		return Modifier.isStatic(m.getModifiers());
	}
}
