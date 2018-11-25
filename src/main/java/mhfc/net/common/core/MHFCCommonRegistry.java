package mhfc.net.common.core;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.*;
import mhfc.net.common.eventhandler.MHFCTickHandler;
import mhfc.net.common.world.area.AreaRegistry;
import org.apache.logging.log4j.Logger;

public class MHFCCommonRegistry {
	private final static Logger logger = MHFCMain.logger();

	public static void init() {
		addEvent();
		addPlayerProperties();
		addRecipes();
		addDimension();
		addSmelting();
		addTile();
		addPacket();
		addAreas();
		addQuests();
	}

	public static void staticInit() {
		MHFCPotionRegistry.staticInit();
		MHFCEntityRegistry.staticInit();
		MHFCQuestRegistry.staticInit();
		MHFCEventRegistry.staticInit();
		MHFCSoundRegistry.staticInit();

		// BLOCKS BEFORE ITEMS!!
		MHFCBlockRegistry.staticInit();
		MHFCItemRegistry.staticInit();
		MHFCLootTableRegistry.staticInit();
		MHFCTickHandler.staticInit();

		MHFCMain.initPhase.registerEntryCallback(e -> init());
	}

	private static void addPlayerProperties() {
		MHFCPlayerPropertiesRegistry.init();
		MHFCMain.logger().info("Custom player properties registered");
	}

	private static void addQuests() {
		MHFCQuestBuildRegistry.init();
		MHFCMain.logger().info("Quest Server registered");
	}

	private static void addSmelting() {
		MHFCSmeltingRegistry.init();
		MHFCCommonRegistry.logger.info("Smelting registered");
	}

	private static void addPacket() {
		MHFCPacketRegistry.init();
		MHFCCommonRegistry.logger.info("Packets registered");
	}

	private static void addRecipes() {
		MHFCCraftingRegistry.init();
		MHFCCommonRegistry.logger.info("Recipes registered");
	}

	private static void addDimension() {
		MHFCDimensionRegistry.init();
		MHFCCommonRegistry.logger.info("Dimension registered");
	}

	private static void addTile() {
		MHFCTileRegistry.init();
		MHFCCommonRegistry.logger.info("Tile Entities registered");
	}

	private static void addEvent() {
		MHFCEventRegistry.init();
		MHFCCommonRegistry.logger.info("Events registered");
	}

	private static void addAreas() {
		MHFCExplorationRegistry.init();
		AreaRegistry.init();
		MHFCExplorationRegistry.init();
		MHFCCommonRegistry.logger.info("Areas and exploration registered");
	}

}
