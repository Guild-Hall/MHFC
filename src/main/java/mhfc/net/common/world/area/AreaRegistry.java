package mhfc.net.common.world.area;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import mhfc.net.common.world.types.AreaDesert;
import mhfc.net.common.world.types.AreaGreenValley;
import mhfc.net.common.world.types.AreaSandy;
import mhfc.net.common.world.types.AreaTypePlayfield;
import mhfc.net.common.world.types.ArenaType;
import mhfc.net.common.world.types.TestAreaType;
import mhfc.net.common.world.types.VillagePokeType;

public class AreaRegistry {

	public static AreaRegistry instance = new AreaRegistry();
	public static final String NAME_PLAYFIELD = "playfield";
	public static final String NAME_PLAYFIELD_MEDIUM = "playfield_medium";
	public static final String NAME_PLAYFIELD_BIG = "playfield_big";
	public static final String NAME_TEST_SCHEMATIC = "test_schematic";
	public static final String NAME_ARENA = "arena";
	public static final String NAME_DESERT = "desert";
	
	public static final String NAME_VILLAGE_POKE = "village_poke";
	
	// New 1.11
	public static final String NAME_SANDY = "sandy";
	public static final String NAME_GREENVALLEY = "greenvalley";

	public static void init() {
		AreaRegistry.register(NAME_PLAYFIELD, AreaTypePlayfield.PLAYFIELD_TYPE);
		AreaRegistry.register(NAME_PLAYFIELD_MEDIUM, AreaTypePlayfield.PLAYFIELD_MEDIUM);
		AreaRegistry.register(NAME_PLAYFIELD_BIG, AreaTypePlayfield.PLAYFIELD_BIG);
		AreaRegistry.register(NAME_TEST_SCHEMATIC, TestAreaType.INSTANCE);
		AreaRegistry.register(NAME_ARENA, ArenaType.INSTANCE);
		AreaRegistry.register(NAME_VILLAGE_POKE, VillagePokeType.INSTANCE);
		AreaRegistry.register(NAME_DESERT, AreaDesert.INSTANCE);
		
		//1.11
		
		AreaRegistry.register(NAME_SANDY, AreaSandy.INSTANCE);
		AreaRegistry.register(NAME_GREENVALLEY, AreaGreenValley.INSTANCE);
	}

	private Map<String, IAreaType> stringToType = new HashMap<>();
	private Map<IAreaType, String> typeToString = new HashMap<>();

	private AreaRegistry() {}

	public static void register(String name, IAreaType type) {
		AreaRegistry.instance.registerArea(name, type);
	}

	public void registerArea(String name, IAreaType type) {
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

	public Set<String> getAllRegisteredTypeNames() {
		return stringToType.keySet();
	}

	public static Set<String> getTypeNames() {
		return AreaRegistry.instance.getAllRegisteredTypeNames();
	}

}
