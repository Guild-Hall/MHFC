package mhfc.net.common.util.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Context {
	private Map<String, IValueHolder> map = new HashMap<String, IValueHolder>();

	public IValueHolder getVar(String key) {
		return map.get(key);
	}

	public boolean hasVar(String key) {
		return map.containsKey(key);
	}

	public boolean putVar(String key, IValueHolder any) {
		Objects.requireNonNull(key);
		Objects.requireNonNull(any);
		if (key.isEmpty())
			throw new IllegalArgumentException("Key can't be the empty string");
		return map.put(key, any) == null;
	}

}
