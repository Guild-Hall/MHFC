package mhfc.net.common.util.parsing;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import mhfc.net.common.util.parsing.valueholders.Any;

public class Context {
	private Map<String, Any> set = new HashMap<String, Any>();

	public Any get(String key) {
		return set.get(key);
	}

	public boolean has(String key) {
		return set.containsKey(key);
	}

	public boolean put(String key, Any any) {
		return set
				.put(Objects.requireNonNull(key), Objects.requireNonNull(any)) == null;
	}
}
