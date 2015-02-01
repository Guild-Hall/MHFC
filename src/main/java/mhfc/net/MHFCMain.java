package mhfc.net;

import mhfc.net.common.tab.MHFCTab;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.creativetab.CreativeTabs;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Heltrato, WorldSEnder
 * @license MHFModding Team Copyright
 *          (http://www.minecraftforum.net/topic/1977334
 *          -164spmp-monster-hunter-frontier
 *          -craft-extreme-mob-hunting-adventure-15000-downloads/) Visit
 *          www.mhfrontiercraft.blogspot.com for more info.
 */

@Mod(modid = MHFCReference.main_modid, name = MHFCReference.main_name,
		version = MHFCReference.main_version)
public class MHFCMain {

	@SidedProxy(clientSide = "mhfc.net.client.MHFCClient",
			serverSide = "mhfc.net.server.MHFCServer")
	public static ProxyBase proxy;

	@Mod.Instance(MHFCReference.main_modid)
	public static MHFCMain instance;

	public static Logger logger;
	public static CreativeTabs mhfctabs = new MHFCTab(CreativeTabs.getNextID());

	private boolean isPreInitialized = false;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent pre) {
		// MHFCConfig.init(pre);
		logger = pre.getModLog();
		isPreInitialized = true;
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.register();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {}

	public static boolean isPreInitiliazed() {
		return instance == null ? false : instance.isPreInitialized;
	}

	public static void checkPreInitialized() {
		if (!MHFCMain.isPreInitiliazed())
			throw new IllegalStateException("Initializing too early");
	}
}
