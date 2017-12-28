package mhfc.net;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.forge.ForgeWorldEdit;

import mhfc.net.common.core.Config;
import mhfc.net.common.core.command.CommandExplore;
import mhfc.net.common.core.command.CommandMHFC;
import mhfc.net.common.core.command.CommandPortableSchematic;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.network.NetworkTracker;
import mhfc.net.common.system.UpdateSystem;
import mhfc.net.common.tab.MHFCTab;
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
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 *
 * @author Heltrato, WorldSEnder
 * @license MHFModding Team Copyright (http://www.minecraftforum.net/topic/1977334 -164spmp-monster-hunter-frontier
 *          -craft-extreme-mob-hunting-adventure-15000-downloads/) Visit www.mhfrontiercraft.blogspot.com for more info.
 */

@Mod(
		modid = ResourceInterface.main_modid,
		updateJSON = "https://raw.githubusercontent.com/Guild-Hall/MHFC/updates/update.json")
public class MHFCMain {
	private static IPhaseAccess<FMLConstructionEvent, Void> constructedPhaseAccess = Services.instance
			.<FMLConstructionEvent, Void>registerPhase("mod constructed").setDefaultShutdownContext(null);
	public static final IPhaseKey<FMLConstructionEvent, Void> constructedPhase = constructedPhaseAccess;

	private static IPhaseAccess<FMLPreInitializationEvent, Void> preInitPhaseAccess = Services.instance
			.<FMLPreInitializationEvent, Void>registerPhase("mod pre initialized").setDefaultShutdownContext(null)
			.declareRequirement(constructedPhase);
	public static final IPhaseKey<FMLPreInitializationEvent, Void> preInitPhase = preInitPhaseAccess;

	private static IPhaseAccess<FMLInitializationEvent, Void> initPhaseAccess = Services.instance
			.<FMLInitializationEvent, Void>registerPhase("mod initialized").setDefaultShutdownContext(null)
			.declareRequirement(preInitPhase);
	public static final IPhaseKey<FMLInitializationEvent, Void> initPhase = initPhaseAccess;

	private static IPhaseAccess<FMLPostInitializationEvent, Void> postInitPhaseAccess = Services.instance
			.<FMLPostInitializationEvent, Void>registerPhase("mod post initialized").setDefaultShutdownContext(null)
			.declareRequirement(initPhase);
	public static final IPhaseKey<FMLPostInitializationEvent, Void> postInitPhase = postInitPhaseAccess;

	private static IPhaseAccess<FMLServerStartingEvent, FMLServerStoppedEvent> serverRunningPhaseAccess = Services.instance
			.<FMLServerStartingEvent, FMLServerStoppedEvent>registerPhase("server running")
			.declareRequirement(postInitPhase);
	/**
	 * Active while the a logical server is running
	 */
	public static final IPhaseKey<FMLServerStartingEvent, FMLServerStoppedEvent> serverRunningPhase = serverRunningPhaseAccess;
	private static IPhaseAccess<FMLServerStartedEvent, FMLServerStoppingEvent> serverActivePhaseAccess = Services.instance
			.<FMLServerStartedEvent, FMLServerStoppingEvent>registerPhase("server active")
			.declareRequirement(serverRunningPhase);
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

			@Override
			public void onPhaseEndExceptionally(Object service, Throwable cause) {
				logger().debug("Exiting phase " + phase + " exceptionally");
			}
		};
	}

	static {
		IServiceAccess<Object> sentinel = Services.instance
				.registerService("sentinel", IServiceHandle.noInit(Object::new));
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

	@Mod.Instance(ResourceInterface.main_modid)
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
	private final static Logger logger = LogManager.getLogger(ResourceInterface.main_modid);

	public static Logger logger() {
		return logger;
	}

	private Config config;
	private NetworkTracker connectionTracker = NetworkTracker.instance;
	private ModContainer mhfcContainer;

	public static Config config() {
		return instance.config;
	}

	public static ModContainer getModContainer() {
		return instance.mhfcContainer;
	}

	private static void staticInit() {
		MHFCMain.getSidedProxy().staticInit();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			constructedPhaseAccess.exitPhase(null);
		}));
	}

	@Mod.EventHandler
	protected void onCreation(FMLConstructionEvent event) {
		staticInit();
		this.mhfcContainer = Loader.instance().getIndexedModList().get(ResourceInterface.main_modid);
		if (mhfcContainer.getMod() != this) {
			Message errorMessage = logger().getMessageFactory()
					.newMessage("Mod container found for 'mhfc' is {}, not {}", mhfcContainer.getMod(), this);
			logger.fatal(errorMessage, new IllegalStateException(errorMessage.getFormattedMessage()));
		}
		constructedPhaseAccess.enterPhase(event);
	}

	@Mod.EventHandler
	protected void onPreInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(connectionTracker);
		MinecraftForge.EVENT_BUS.register(this);
		ForgeChunkManager.setForcedChunkLoadingCallback(this, this::chunkLoadingCallback);
		// MHFCConfig.init(pre);
		config = new Config(event);
		MHFCMain.config().init();
		UpdateSystem.init();
		MHFCMain.logger().info("Starting MHFC ( " + ResourceInterface.getMetadata().version + " ) ");
		MHFCMain.logger().info("Copyright (c) Heltrato | WorldSEnder 2017");
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
		event.registerServerCommand(new CommandExplore());
		event.registerServerCommand(new CommandPortableSchematic(WorldEdit.getInstance()));
		UpdateSystem.onServerStarting(event);
		serverRunningPhaseAccess.enterPhase(event);
	}

	@Mod.EventHandler
	protected void onServerStarted(FMLServerStartedEvent event) {
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
		if (!event.getWorld().isRemote && event.getWorld() instanceof WorldServer) {
			WorldServer world = (WorldServer) event.getWorld();
			int spawnX = (int) (world.getWorldInfo().getSpawnX() / world.provider.getMovementFactor() / 16);
			int spawnZ = (int) (world.getWorldInfo().getSpawnZ() / world.provider.getMovementFactor() / 16);
			for (int x = -1; x <= 1; x++) {
				for (int z = -1; z <= 1; z++) {
					world.getChunkProvider().loadChunk(spawnX + x, spawnZ + z);
				}
			}
		}
	}

	private void chunkLoadingCallback(List<Ticket> tickets, World world) {
		// No-op
		logger.debug("" + tickets + " " + world);
	}
}
