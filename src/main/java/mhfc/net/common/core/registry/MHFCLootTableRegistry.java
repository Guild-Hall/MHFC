package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.services.IServiceKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class MHFCLootTableRegistry {

	public static void staticInit() {}

	private static final IServiceKey<MHFCLootTableRegistry> serviceAccess = RegistryWrapper
			.registerService("loot table registry", MHFCLootTableRegistry::new, MHFCMain.preInitPhase);

	public final ResourceLocation questsNoGutsNoGlory;

	private MHFCLootTableRegistry() {
		questsNoGutsNoGlory = register("quests/no_guts_no_glory");
	}

	private ResourceLocation register(String location) {
		return LootTableList.register(new ResourceLocation(ResourceInterface.main_modid, location));
	}

	public static MHFCLootTableRegistry getRegistry() {
		return serviceAccess.getService();
	}

}
