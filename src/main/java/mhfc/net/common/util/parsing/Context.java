package mhfc.net.common.util.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import mhfc.net.common.util.parsing.expressions.Arguments;

public class Context {
	private static <T> boolean put(Map<String, ? super T> map, String key, T item) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(item);
		if (key.isEmpty())
			throw new IllegalArgumentException("Key can't be the empty string");
		if (key.startsWith("_") && map.containsKey(key))
			return false;
		map.put(key, item);
		return true;
	}

	private Map<String, IValueHolder> map = new HashMap<>();
	private Map<String, Function<Arguments, IValueHolder>> funcMap = new HashMap<>();

	/**
	 * 
	 * @param key
	 * @return
	 * @see Map#get(Object)
	 */
	public IValueHolder getVar(String key) {
		return map.get(key);
	}

	/**
	 * 
	 * @param key
	 * @return
	 * @see Map#containsKey(Object)
	 */
	public boolean hasVar(String key) {
		return map.containsKey(key);
	}

	/**
	 * Puts a variable into the context. The key must not be empty, also the any
	 * shall not be <code>null</code>.<br>
	 * Keys that begin with an <code>'_'(LOW LINE U+005F)</code> are "internal".
	 * Once they are set, they can not be replaced. Don't use this if you don't
	 * exactly know what you're doing.
	 * 
	 * @param key
	 * @param any
	 * @return if the any was successfully placed into the context
	 */
	public boolean putVar(String key, IValueHolder any) {
		return put(map, key, any);
	}

	public Function<Arguments, IValueHolder> getFilter(String name) {
		return funcMap.get(name);
	}

	public boolean hasFilter(String name) {
		return funcMap.containsKey(name);
	}

	public boolean putFilter(String key, Function<Arguments, IValueHolder> filter) {
		return put(funcMap, key, filter);
	}

}
