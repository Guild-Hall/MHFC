package mhfc.net;

import mhfc.net.common.MHFCCommon;
import mhfc.net.common.network.PacketPipeline;
import mhfc.net.common.network.packet.PacketAIAnim;
import mhfc.net.common.network.packet.PacketAIKirin;
import mhfc.net.common.network.packet.PacketAIRathalos;
import mhfc.net.common.network.packet.PacketAITigrex;
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
	public static final PacketPipeline packetPipeline = new PacketPipeline();
	public static CreativeTabs mhfctabs = new MHFCTab(CreativeTabs.getNextID());

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent pre) {
		// MHFCConfig.init(pre);
		logger = pre.getModLog();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		packetPipeline.initialize();
		packetPipeline.registerPacket(PacketAITigrex.class);
		packetPipeline.registerPacket(PacketAIAnim.class);
		packetPipeline.registerPacket(PacketAIKirin.class);
		packetPipeline.registerPacket(PacketAIRathalos.class);
		proxy.regSounds();
		proxy.regStuff();
		proxy.regTimer();
		proxy.regTick();
		proxy.regCapes();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		packetPipeline.postInitialize();
	}

	public static boolean isClient() {
		return FMLCommonHandler.instance().getSide().isClient();
	}
	public static boolean isEffectiveClient() {
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
}
