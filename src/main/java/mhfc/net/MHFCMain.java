package mhfc.net;

import mhfc.net.common.MHFCCommon;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.message.MessageAIAttack;
import mhfc.net.common.network.message.MessageAttackHandler;
import mhfc.net.common.tab.MHFCTab;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.creativetab.CreativeTabs;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Heltrato, WorldSEnder
 * @license MHFModding Team Copyright
 *          (http://www.minecraftforum.net/topic/1977334
 *          -164spmp-monster-hunter-frontier
 *          -craft-extreme-mob-hunting-adventure-15000-downloads/) Visit
 *          www.mhfrontiercraft.blogspot.com for more info.
 */

@Mod(modid = MHFCReference.main_modid, name = MHFCReference.main_name, version = MHFCReference.main_version)
public class MHFCMain {

	@SidedProxy(clientSide = "mhfc.net.client.MHFCClient", serverSide = "mhfc.net.common.MHFCCommon")
	public static MHFCCommon proxy;

	@Mod.Instance(MHFCReference.main_modid)
	public static MHFCMain instance;

	public static Logger logger;
	public static CreativeTabs mhfctabs = new MHFCTab(CreativeTabs.getNextID());

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent pre) {
		// MHFCConfig.init(pre);
		logger = pre.getModLog();
		PacketPipeline.registerPacket(MessageAttackHandler.class,
				MessageAIAttack.class, Side.CLIENT);
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.regSounds();
		proxy.regStuff();
		proxy.regTimer();
		proxy.regTick();
		proxy.regCapes();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {}

	public static boolean isClient() {
		return proxy.getSide() == Side.CLIENT;
	}

	@Deprecated
	public static boolean isEffectiveClient() {
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
}
