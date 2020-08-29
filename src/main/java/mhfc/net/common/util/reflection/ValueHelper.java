package mhfc.net.common.util.reflection;

import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ValueHelper {

	public static <T, E> void setPrivateFinalValue(
			Class<? super T> classToAccess,
			T instance,
			E value,
			String... fieldNames) {
		@SuppressWarnings("deprecation")
		Field field = ReflectionHelper.findField(
				classToAccess,
				ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));

		try {
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.set(instance, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
