package mhfc.heltrato;

import mhfc.heltrato.common.MHFCCommon;
import mhfc.heltrato.common.network.message.MessageAIAnim;
import mhfc.heltrato.common.network.message.MessageAIKirin;
import mhfc.heltrato.common.network.message.MessageAIRathalos;
import mhfc.heltrato.common.network.message.MessageAITigrex;
import mhfc.heltrato.common.tab.MHFCTab;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;


/**
 * @author <Heltrato>
 * @license MHFModding Team Copyright (http://www.minecraftforum.net/topic/1977334-164spmp-monster-hunter-frontier-craft-extreme-mob-hunting-adventure-15000-downloads/) 
 * Visit www.facebook.com/mhfcraft or follow @heltrato for more info.
 * 
 * NOTE: Martin/Andreas if im succeed of bypassing GL issues with some sort of stuff. We will update this to 1.7.10
 * -Heltrato.
 * 
 */

@Mod(modid = MHFCReference.main_modid, name = MHFCReference.main_name, version = MHFCReference.main_version)

public class MHFCMain {
	
	@SidedProxy(clientSide = "mhfc.heltrato.client.MHFCClient", serverSide = "mhfc.heltrato.common.MHFCCommon")
	public static MHFCCommon proxy;
	
	@Mod.Instance("mhfc")
	public static MHFCMain instance;
	public static SimpleNetworkWrapper network;
	public static final String[] fTimer;
	public static CreativeTabs mhfctabs = new MHFCTab(CreativeTabs.getNextID(),"MHFC Tab");
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent pre){
		//MHFCConfig.init(pre);
		pre.getModMetadata().logoFile = "MHFCLogo.png";
	}
	
	@Mod.EventHandler
	public void load(FMLInitializationEvent event){
	network = NetworkRegistry.INSTANCE.newSimpleChannel("mhfc");
	network.registerMessage(MessageAIRathalos.PacketAIRathalosHandler.class, MessageAIRathalos.class, 0, Side.CLIENT);
	network.registerMessage(MessageAITigrex.PacketAITigrexHandler.class, MessageAITigrex.class, 1, Side.CLIENT);
	network.registerMessage(MessageAIAnim.PacketAIAnimHandler.class, MessageAIAnim.class, 2, Side.CLIENT);
	network.registerMessage(MessageAIKirin.PacketAIKirinHandler.class, MessageAIKirin.class, 3, Side.CLIENT);
	proxy.regSounds();
	proxy.regStuff();
	proxy.regTimer();
	proxy.regTick();
	proxy.regCapes();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
	}
	
	public static void sendPacketToAll(EntityPlayer player, Packet packet) {
	   if (isEffectiveClient()) return;
	   ((EntityPlayerMP)player).mcServer.getConfigurationManager().sendPacketToAllPlayers(packet);
	}
	
	public static boolean isClient(){
		return FMLCommonHandler.instance().getSide().isClient();
	}
	public static boolean isEffectiveClient(){
		return FMLCommonHandler.instance().getEffectiveSide().isClient();
	}
	static {
		fTimer = new String[] {"field_71428_T", "S", "timer"};
	}
	public static void onRenderTick() {}
	public static void onClientTick() {}
	public static void onServerTick() {}
}
