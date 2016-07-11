package mhfc.net.common.core.registry;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import mhfc.net.MHFCMain;
import mhfc.net.common.eventhandler.MHFCGuiHandler;
import mhfc.net.common.eventhandler.MHFCJobHandler;
import mhfc.net.common.eventhandler.MHFCTickHandler;
import mhfc.net.common.eventhandler.entity.PitfallEvent;
import mhfc.net.common.eventhandler.player.CombatEventHandler;
import mhfc.net.common.eventhandler.player.ConnectionEventHandler;
import mhfc.net.common.eventhandler.player.MHFCCapeEventHandler;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("deprecation")
public class MHFCEventRegistry {

	public static void staticInit() {
		MHFCTickHandler.staticInit();
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
		NetworkRegistry.INSTANCE.registerGuiHandler(MHFCMain.instance(), MHFCGuiHandler.instance);
	}

	private static void registerNetworkEventHandlers() {}

	private static void registerWorldEventHandlers() {
		FMLCommonHandler.instance().bus().register(ConnectionEventHandler.instance);
		FMLCommonHandler.instance().bus().register(CombatEventHandler.instance);
		MinecraftForge.EVENT_BUS.register(CombatEventHandler.instance);
	}

	private static void registerEntityEventHandlers() {}

	private static void registerPotionEventHandlers() {
		MinecraftForge.EVENT_BUS.register(PitfallEvent.instance);
	}

	private static void registerMiscEventHandlers() {
		MinecraftForge.EVENT_BUS.register(MHFCCapeEventHandler.instance);
	}

	private static void registerTickHandler() {
		FMLCommonHandler.instance().bus().register(MHFCTickHandler.instance);
		FMLCommonHandler.instance().bus().register(MHFCJobHandler.instance);
	}
}
