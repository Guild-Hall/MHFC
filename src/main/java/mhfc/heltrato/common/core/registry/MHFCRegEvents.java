package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.eventhandler.MHFCGuiHandler;
import mhfc.heltrato.common.eventhandler.MHFCTickHandler;
import mhfc.heltrato.common.eventhandler.player.MHFCCapeEvent;
import mhfc.heltrato.common.eventhandler.player.MHFCPlayerEvent;
import mhfc.heltrato.common.eventhandler.potion.PotionPitfallEventHandler;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class MHFCRegEvents {
	
	private static MHFCMain mod = MHFCMain.instance;
	
	public static void init() {
		registerNetworkGuiHandler();
		registerNetworkEventHandlers();
		registerWorldEventHandlers();
		registerEntityEventHandlers();
		registerPotionEventHandlers();
		registerGUIEventHandlers();
		registerMiscEventHandlers();
		registerMiscEventHandlers();
		registerTickHandler();
	}
	
	private static void registerNetworkGuiHandler() {
		getNetworkGuiHandler(new MHFCGuiHandler());
	}
	
	private static void registerNetworkEventHandlers()
	{
		
	}
	
	private static void registerWorldEventHandlers()
	{
		FMLCommonHandler.instance().bus().register(new MHFCPlayerEvent());
	}
	
	private static void registerEntityEventHandlers()
	{
	}
	
	private static void registerPotionEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(PotionPitfallEventHandler.class);
	}
	
	private static void registerGUIEventHandlers()
	{
		
	}
	
	private static void registerMiscEventHandlers()
	{
		MinecraftForge.EVENT_BUS.register(new MHFCCapeEvent());
	}
	
	private static void registerTickHandler()
	{
		getTickHandler(MHFCTickHandler.class);
	}
	
	private static void getTickHandler(Class handler)
	{
		MinecraftForge.EVENT_BUS.register(handler);
		FMLCommonHandler.instance().bus().register(handler);
	}

	
	private static void getNetworkGuiHandler(IGuiHandler param){
		NetworkRegistry.INSTANCE.registerGuiHandler(mod, param);
	}
	
	
}
