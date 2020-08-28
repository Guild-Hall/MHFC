package mhfc.net.common.world.area;

import java.util.Set;

import mhfc.net.common.world.types.AreaGreenValley;
import mhfc.net.common.world.types.AreaSandy;
import mhfc.net.common.world.types.AreaTypePlayfield;
import mhfc.net.common.world.types.ArenaType;
import mhfc.net.common.world.types.TestAreaType;
import mhfc.net.common.world.types.VillagePokeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public class AreaRegistry {

	private static IForgeRegistry<IAreaType> REGISTRY;

	@SubscribeEvent
	public static void onRegistryCreation(RegistryEvent.NewRegistry event) {
		REGISTRY = new RegistryBuilder<IAreaType>()
			.setType(IAreaType.class)
			.setName(new ResourceLocation("mhfc:areatypes"))
			.allowModification()
			.create();
	}

	@SubscribeEvent
	public static void registerDefaultAreas(RegistryEvent.Register<IAreaType> event) {
		event.getRegistry().registerAll(
			AreaTypePlayfield.PLAYFIELD_TYPE,
			AreaTypePlayfield.PLAYFIELD_MEDIUM,
			AreaTypePlayfield.PLAYFIELD_BIG,
			TestAreaType.INSTANCE,
			ArenaType.INSTANCE,
			VillagePokeType.INSTANCE,
			// 1.11
			AreaSandy.INSTANCE,
			AreaGreenValley.INSTANCE
		);
	}

	// public static final String NAME_DESERT = "desert";
	// public static final String NAME_TREEPEAK = "treepeak";
	// public static final String NAME_SNOWYMOUNTAINS = "snowymountains";

	public static String getName(IAreaType type) {
		if(!REGISTRY.containsValue(type)) {
			throw new IllegalArgumentException("area is not registered");
		}
		return type.getRegistryName().toString();
	}

	public static IAreaType getType(ResourceLocation name) {
		return REGISTRY.getValue(name);
	}

	public static Set<ResourceLocation> getTypeNames() {
		return REGISTRY.getKeys();
	}

	public static IForgeRegistry<IAreaType> getRegistry() {
		return REGISTRY;
	}
}
