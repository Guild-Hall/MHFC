package mhfc.net.common.system;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ReflectionSystem
{   
	
	/*
	 * Java Reflection extension or removal of final statics for better intger increments and IDS for minecraft
	 * final setted values like Potion and other stuff. -Heltrato
	 * */
	public static <T> Field removeFinal(Class <? super T > classToAccess, T instance, String... fieldNames)
	{
    	Field field = ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));
    	
    	try
    	{
    		Field modifiersField = Field.class.getDeclaredField("modifiers");
    		modifiersField.setAccessible(true);
    		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    	
    	return field;
	}
	
    public static <T, E> void setPrivateFinalValue(Class <? super T > classToAccess, T instance, E value, String... fieldNames)
    {
    	Field field = ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));
    	
    	try
    	{
    		Field modifiersField = Field.class.getDeclaredField("modifiers");
    		modifiersField.setAccessible(true);
    		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

    		field.set(instance, value);
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
}
