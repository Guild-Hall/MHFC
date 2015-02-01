package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.eventhandler.MHFCGuiHandler;
import mhfc.net.common.eventhandler.MHFCTickHandler;
import mhfc.net.common.eventhandler.potion.PotionPitfallEventHandler;
import net.minecraftforge.common.MinecraftForge;
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

	private static void registerWorldEventHandlers() {}

	private static void registerEntityEventHandlers() {}

	private static void registerPotionEventHandlers() {
		MinecraftForge.EVENT_BUS.register(PotionPitfallEventHandler.instance);
	}

	private static void registerMiscEventHandlers() {}

	private static void registerTickHandler() {
		MinecraftForge.EVENT_BUS.register(MHFCTickHandler.instance);
	}
}
