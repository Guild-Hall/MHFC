package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.eventhandler.MHFCGuiHandler;
import mhfc.net.common.eventhandler.MHFCJobHandler;
import mhfc.net.common.eventhandler.MHFCTickHandler;
import mhfc.net.common.eventhandler.player.MHFCCapeEventHandler;
import mhfc.net.common.eventhandler.player.ConnectionEventHandler;
import mhfc.net.common.eventhandler.potion.PotionPitfallEventHandler;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class MHFCEventRegistry {
	private static MHFCMain mod;

	static {
		MHFCMain.checkPreInitialized();
		mod = MHFCMain.instance;
	}

	public static void init() {
		registerGuiHandler();
		registerNetworkEventHandlers();
		registerWorldEventHandlers();
		registerEntityEventHandlers();
		registerPotionEventHandlers();
		registerMiscEventHandlers();
		registerMiscEventHandlers();
		registerTickHandler();
	}

	private static void registerGuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(mod,
				MHFCGuiHandler.instance);
	}

	private static void registerNetworkEventHandlers() {}

	private static void registerWorldEventHandlers() {
		FMLCommonHandler.instance().bus()
				.register(ConnectionEventHandler.instance);
	}

	private static void registerEntityEventHandlers() {}

	private static void registerPotionEventHandlers() {
		MinecraftForge.EVENT_BUS.register(PotionPitfallEventHandler.instance);
	}

	private static void registerMiscEventHandlers() {
		MinecraftForge.EVENT_BUS.register(MHFCCapeEventHandler.instance);
	}

	private static void registerTickHandler() {
		FMLCommonHandler.instance().bus().register(MHFCTickHandler.instance);
		FMLCommonHandler.instance().bus().register(MHFCJobHandler.instance);
	}
}
