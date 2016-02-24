package mhfc.net;

import org.apache.logging.log4j.Logger;

import com.sk89q.worldedit.forge.ForgeWorldEdit;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.client.MHFCClient;
import mhfc.net.common.configuration.MHFCConfig;
import mhfc.net.common.core.command.CommandExplore;
import mhfc.net.common.core.command.CommandMHFC;
import mhfc.net.common.core.command.CommandTpHunterDimension;
import mhfc.net.common.system.UpdateSystem;
import mhfc.net.common.tab.MHFCTab;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

/**
 *
 * @author Heltrato, WorldSEnder
 * @license MHFModding Team Copyright (http://www.minecraftforum.net/topic/1977334 -164spmp-monster-hunter-frontier
 *          -craft-extreme-mob-hunting-adventure-15000-downloads/) Visit www.mhfrontiercraft.blogspot.com for more info.
 */

@Mod(modid = MHFCReference.main_modid, dependencies = ForgeWorldEdit.MOD_ID)
public class MHFCMain {

	@SidedProxy(clientSide = "mhfc.net.client.MHFCClient", serverSide = "mhfc.net.server.MHFCServer")
	public static ProxyBase proxy;

	@Mod.Instance(MHFCReference.main_modid)
	public static MHFCMain instance;

	@Mod.Instance(ForgeWorldEdit.MOD_ID)
	public static ForgeWorldEdit worldedit;

	public static ForgeWorldEdit worldedit() {
		return worldedit == null ? ForgeWorldEdit.inst : worldedit;
	}

	public static Logger logger;
	public static CreativeTabs mhfctabs = new MHFCTab(CreativeTabs.getNextID());
	public static MHFCConfig config;

	private boolean isPreInitialized = false;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent pre) {
		// MHFCConfig.init(pre);
		MHFCMain.logger = pre.getModLog();
		MHFCMain.config = new MHFCConfig(pre);
		MHFCMain.config.init();
		UpdateSystem.init();
		MHFCMain.logger.info("Starting MHFC v" + MHFCReference.getMetadata().version);
		MHFCMain.logger.info("Copyright (c) Guild Hall 2015");
		isPreInitialized = true;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		MHFCMain.proxy.register();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {}

	@Mod.EventHandler
	public void serverLoad(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandMHFC());
		event.registerServerCommand(new CommandTpHunterDimension());
		event.registerServerCommand(new CommandExplore());
	}

	@Mod.EventHandler
	public static void onServerStart(FMLServerStartedEvent sse) {
		UpdateSystem.onServerStart(sse);
	}

	public static boolean isPreInitiliazed() {
		return MHFCMain.instance == null ? false : MHFCMain.instance.isPreInitialized;
	}

	public static void checkPreInitialized() {
		if (!MHFCMain.isPreInitiliazed()) {
			throw new IllegalStateException("Initializing too early");
		}
	}

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event) {
		// Load a 3x3 around spawn to make sure that it populates and calls our hooks.
		if (!event.world.isRemote && event.world instanceof WorldServer) {
			WorldServer world = (WorldServer) event.world;
			int spawnX = (int) (event.world.getWorldInfo().getSpawnX() / world.provider.getMovementFactor() / 16);
			int spawnZ = (int) (event.world.getWorldInfo().getSpawnZ() / world.provider.getMovementFactor() / 16);
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					world.theChunkProviderServer.loadChunk(spawnX + x, spawnZ + z);
				}
			}
		}
	}

	public static boolean isClientSide() {
		return MHFCMain.proxy instanceof MHFCClient;
	}
}
