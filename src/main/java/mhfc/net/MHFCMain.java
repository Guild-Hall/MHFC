package mhfc.net;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sk89q.worldedit.forge.ForgeWorldEdit;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.common.configuration.MHFCConfig;
import mhfc.net.common.core.command.CommandExplore;
import mhfc.net.common.core.command.CommandMHFC;
import mhfc.net.common.core.command.CommandTpHunterDimension;
import mhfc.net.common.network.NetworkTracker;
import mhfc.net.common.system.UpdateSystem;
import mhfc.net.common.tab.MHFCTab;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.util.services.IPhaseAccess;
import mhfc.net.common.util.services.IPhaseKey;
import mhfc.net.common.util.services.IServiceAccess;
import mhfc.net.common.util.services.IServiceHandle;
import mhfc.net.common.util.services.IServicePhaseHandle;
import mhfc.net.common.util.services.Services;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

/**
 *
 * @author Heltrato, WorldSEnder
 * @license MHFModding Team Copyright (http://www.minecraftforum.net/topic/1977334 -164spmp-monster-hunter-frontier
 *          -craft-extreme-mob-hunting-adventure-15000-downloads/) Visit www.mhfrontiercraft.blogspot.com for more info.
 */

@Mod(modid = MHFCReference.main_modid)
public class MHFCMain {
	private static IPhaseAccess<FMLConstructionEvent, Void> constructedPhaseAccess = Services.instance
			.<FMLConstructionEvent, Void>registerPhase("mod constructed").setDefaultShutdownContext(null);
	public static final IPhaseKey<FMLConstructionEvent, Void> constructedPhase = constructedPhaseAccess;

	private static IPhaseAccess<FMLPreInitializationEvent, Void> preInitPhaseAccess = Services.instance
			.<FMLPreInitializationEvent, Void>registerPhase("mod pre initialized").setDefaultShutdownContext(null)
			.declareParent(constructedPhase);
	public static final IPhaseKey<FMLPreInitializationEvent, Void> preInitPhase = preInitPhaseAccess;

	private static IPhaseAccess<FMLInitializationEvent, Void> initPhaseAccess = Services.instance
			.<FMLInitializationEvent, Void>registerPhase("mod initialized").setDefaultShutdownContext(null)
			.declareParent(preInitPhase);
	public static final IPhaseKey<FMLInitializationEvent, Void> initPhase = initPhaseAccess;

	private static IPhaseAccess<FMLPostInitializationEvent, Void> postInitPhaseAccess = Services.instance
			.<FMLPostInitializationEvent, Void>registerPhase("mod post initialized").setDefaultShutdownContext(null)
			.declareParent(initPhase);
	public static final IPhaseKey<FMLPostInitializationEvent, Void> postInitPhase = postInitPhaseAccess;

	private static IPhaseAccess<FMLServerStartingEvent, FMLServerStoppedEvent> serverRunningPhaseAccess = Services.instance
			.<FMLServerStartingEvent, FMLServerStoppedEvent>registerPhase("server running")
			.declareParent(postInitPhase);
	/**
	 * Active while the a logical server is running
	 */
	public static final IPhaseKey<FMLServerStartingEvent, FMLServerStoppedEvent> serverRunningPhase = serverRunningPhaseAccess;
	private static IPhaseAccess<FMLServerStartedEvent, FMLServerStoppingEvent> serverActivePhaseAccess = Services.instance
			.<FMLServerStartedEvent, FMLServerStoppingEvent>registerPhase("server active")
			.declareParent(serverRunningPhase);
	/**
	 * Active while the a logical server is running and loaded
	 */
	public static final IPhaseKey<FMLServerStartedEvent, FMLServerStoppingEvent> serverActivePhase = serverActivePhaseAccess;

	private static <A, Z> IServicePhaseHandle<Object, A, Z> getSPHandleFor(String phase) {
		return new IServicePhaseHandle<Object, A, Z>() {
			@Override
			public void onPhaseStart(Object service, A startupContext) {
				logger().debug("Entering phase " + phase);
			}

			@Override
			public void onPhaseEnd(Object service, Z shutdownContext) {
				logger().debug("Exiting phase " + phase);
			}
		};
	}

	static {
		IServiceAccess<Object> sentinel = Services.instance
				.registerService("sentinel", IServiceHandle.<Object>noInit(), Object::new);
		sentinel.addTo(constructedPhase, getSPHandleFor("\"constructed\""));
		sentinel.addTo(preInitPhase, getSPHandleFor("\"pre-initialized\""));
		sentinel.addTo(initPhase, getSPHandleFor("\"initialized\""));
		sentinel.addTo(postInitPhase, getSPHandleFor("\"post-initialized\""));
		sentinel.addTo(serverRunningPhase, getSPHandleFor("\"server running\""));
		sentinel.addTo(serverActivePhase, getSPHandleFor("\"server active\""));
	}

	@SidedProxy(clientSide = "mhfc.net.client.MHFCClient", serverSide = "mhfc.net.server.MHFCServer")
	protected static ProxyBase proxy = null;

	public static ProxyBase getSidedProxy() {
		return proxy;
	}

	@Mod.Instance(MHFCReference.main_modid)
	protected static MHFCMain instance;

	public static MHFCMain instance() {
		return instance;
	}

	@Mod.Instance("worldedit")
	protected static ForgeWorldEdit worldedit;

	public static ForgeWorldEdit getWorldedit() {
		return worldedit;
	}

	public final static CreativeTabs mhfctabs = new MHFCTab(CreativeTabs.getNextID());
	private final static Logger logger = LogManager.getLogger(MHFCReference.main_modid);

	public static Logger logger() {
		return logger;
	}

	private MHFCConfig config;
	private NetworkTracker connectionTracker = NetworkTracker.instance;

	public static MHFCConfig config() {
		return instance.config;
	}

	private void staticInit() {
		MHFCMain.getSidedProxy().staticInit();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			constructedPhaseAccess.exitPhase(null);
		}));
	}

	@Mod.EventHandler
	protected void onCreation(FMLConstructionEvent event) {
		staticInit();
		constructedPhaseAccess.enterPhase(event);
	}

	@Mod.EventHandler
	protected void onPreInit(FMLPreInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(connectionTracker);
		MinecraftForge.EVENT_BUS.register(this);
		ForgeChunkManager.setForcedChunkLoadingCallback(this, this::chunkLoadingCallback);
		// MHFCConfig.init(pre);
		config = new MHFCConfig(event);
		MHFCMain.config().init();
		UpdateSystem.init();
		MHFCMain.logger().info("Starting MHFC v" + MHFCReference.getMetadata().version);
		MHFCMain.logger().info("Copyright (c) Guild Hall 2015");
		preInitPhaseAccess.enterPhase(event);
	}

	@Mod.EventHandler
	protected void onInit(FMLInitializationEvent event) {
		initPhaseAccess.enterPhase(event);
	}

	@Mod.EventHandler
	protected void onPostInit(FMLPostInitializationEvent event) {
		postInitPhaseAccess.enterPhase(event);
	}

	@Mod.EventHandler
	protected void onServerStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new CommandMHFC());
		event.registerServerCommand(new CommandTpHunterDimension());
		event.registerServerCommand(new CommandExplore());
		serverRunningPhaseAccess.enterPhase(event);
	}

	@Mod.EventHandler
	protected void onServerStarted(FMLServerStartedEvent event) {
		UpdateSystem.onServerStart(event);
		serverActivePhaseAccess.enterPhase(event);
	}

	@Mod.EventHandler
	protected void onServerStopping(FMLServerStoppingEvent event) {
		serverActivePhaseAccess.exitPhase(event);
	}

	@Mod.EventHandler
	protected void onServerStopped(FMLServerStoppedEvent event) {
		serverRunningPhaseAccess.exitPhase(event);
	}

	@SubscribeEvent
	protected void onWorldLoad(WorldEvent.Load event) {
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

	private void chunkLoadingCallback(List<Ticket> tickets, World world) {
		// No-op
		logger.debug("" + tickets + " " + world);
	}
}
