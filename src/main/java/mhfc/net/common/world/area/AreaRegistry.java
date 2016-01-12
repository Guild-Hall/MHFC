package mhfc.net.common.world.area;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AreaRegistry {

	public static AreaRegistry instance = new AreaRegistry();

	private Map<String, IAreaType> stringToType = new HashMap<>();
	private Map<IAreaType, String> typeToString = new HashMap<>();

	private AreaRegistry() {}

	public void register(String name, IAreaType type) {
		type = Objects.requireNonNull(type);
		name = Objects.requireNonNull(name);
		if (name.isEmpty()) {
			throw new IllegalArgumentException("Name can't be an empty String");
		}
		if (stringToType.containsKey(name)) {
			throw new IllegalArgumentException("An areatype for the name " + name + " is already registered");
		}
		if (typeToString.containsKey(type)) {
			throw new IllegalArgumentException("This area type has already been registered for a different name");
		}
		stringToType.put(name, type);
		typeToString.put(type, name);
	}

	public String getName(IAreaType type) {
		return typeToString.get(type);
	}

	public IAreaType getType(String name) {
		return stringToType.get(name);
	}

}
