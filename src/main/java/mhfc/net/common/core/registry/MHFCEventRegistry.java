package mhfc.net.common.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.eventhandler.MHFCGuiHandler;
import mhfc.net.common.eventhandler.entity.ModLivingEvent;
import mhfc.net.common.eventhandler.player.WorldPlayerEvent;
import mhfc.net.common.eventhandler.player.MHFCCapeEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class MHFCEventRegistry {

	public static void staticInit() {}

	public static void init() {
		registerGuiHandler();
		registerMiscEventHandlers();
	}

	private static void registerGuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(MHFCMain.instance(), MHFCGuiHandler.instance);
	}

	private static void registerMiscEventHandlers() {
		MinecraftForge.EVENT_BUS.register(MHFCCapeEventHandler.instance);
		MinecraftForge.EVENT_BUS.register(WorldPlayerEvent.instance);
		MinecraftForge.EVENT_BUS.register(ModLivingEvent.instance);

	}
}
