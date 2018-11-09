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

	public final ResourceLocation questA;
	public final ResourceLocation questB;
	public final ResourceLocation questC;
	public final ResourceLocation questD;
	public final ResourceLocation questE;
	public final ResourceLocation questF;
	public final ResourceLocation questG;
	public final ResourceLocation questH;
	public final ResourceLocation questI;

	private MHFCLootTableRegistry() {
		questA = register("quests/no_guts_no_glory");
		questB = register("quests/tigrex_roar");
		questC = register("quest/violent_wyvern");
		questD = register("quest/desert_rathalos");
		questE = register("quest/dangerous_abyss");
		questF = register("quest/leading_the_charge");
		questG = register("quest/phantom_of_the_mist");
		questH = register("quest/phantom_wyvern");
		questI = register("quest/wyvernia_coin");
		
	}

	private ResourceLocation register(String location) {
		return LootTableList.register(new ResourceLocation(ResourceInterface.main_modid, location));
	}

	public static MHFCLootTableRegistry getRegistry() {
		return serviceAccess.getService();
	}

}
