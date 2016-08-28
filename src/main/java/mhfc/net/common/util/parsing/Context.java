package mhfc.net.common.util.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import mhfc.net.common.util.parsing.proxies.ContextWrapper;
import mhfc.net.common.util.parsing.proxies.StaticAccess;
import mhfc.net.common.util.parsing.valueholders.ValueHolders;

public class Context {
	public static String checkKeySyntax(String key) {
		if (key.isEmpty()) {
			throw new IllegalArgumentException("Key can't be the empty string");
		}
		return key;
	}

	private static <T> boolean put(Map<String, ? super T> map, String key, T item) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(item);
		key = checkKeySyntax(key);
		if (key.startsWith("_") && map.containsKey(key)) {
			return false;
		}
		map.put(key, item);
		return true;
	}

	private Map<String, IValueHolder> map = new HashMap<>();
	private ContextWrapper wrapper = new ContextWrapper(this);
	private BoundExpressionTranslator translator;
	private Object lock = new Object();

	/**
	 *
	 * @param key
	 * @return
	 * @see Map#get(Object)
	 */
	public IValueHolder getVar(String key) {
		if (!map.containsKey(key)) {
			return ValueHolders
					.throwing(() -> new IllegalArgumentException("No value named " + key + " in " + Context.this));
		}
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
	 * Puts a variable into the context. The key must not be empty, also the any shall not be <code>null</code>.<br>
	 * Keys that begin with an <code>'_'(LOW LINE U+005F)</code> are "internal". Once they are set, they can not be
	 * replaced. Don't use this if you don't exactly know what you're doing.
	 *
	 * @param key
	 * @param any
	 * @return if the any was successfully placed into the context
	 */
	public boolean putVar(String key, IValueHolder any) {
		return put(map, key, any);
	}

	/**
	 * Puts a class into the context, which static methods and members can be access.
	 *
	 * @param key
	 * @param clazz
	 * @return if it was successfully placed into the context
	 * @see #putVar(String, IValueHolder)
	 */
	public boolean putClass(String key, Class<?> clazz) {
		return put(map, key, Holder.valueOf(new StaticAccess(clazz)));
	}

	public BoundExpressionTranslator getTranslator() {
		if (translator == null) {
			synchronized (lock) {
				// Double-tap for performance
				if (translator == null) {
					translator = new BoundExpressionTranslator(this);
				}
			}
		}
		return translator;
	}

	/**
	 * The wrapper that is being accessed in the parsded expressions.
	 *
	 * @return
	 */
	public ContextWrapper getWrapper() {
		return wrapper;
	}
}
