package mhfc.net.common.core;

import org.apache.logging.log4j.Logger;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.core.registry.MHFCCraftingRegistry;
import mhfc.net.common.core.registry.MHFCDimensionRegistry;
import mhfc.net.common.core.registry.MHFCEntityRegistry;
import mhfc.net.common.core.registry.MHFCEventRegistry;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.core.registry.MHFCHunterCraftingRegistry;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.core.registry.MHFCPacketRegistry;
import mhfc.net.common.core.registry.MHFCPlayerPropertiesRegistry;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.core.registry.MHFCQuestRegistry;
import mhfc.net.common.core.registry.MHFCSmeltingRegistry;
import mhfc.net.common.core.registry.MHFCTileRegistry;
import mhfc.net.common.world.area.AreaRegistry;

public class MHFCCommonRegistry {
	private final static Logger logger = MHFCMain.logger();

	public static void init() {
		MHFCCommonRegistry.addEvent();
		MHFCCommonRegistry.addPlayerProperties();
		MHFCCommonRegistry.addRecipes();
		MHFCCommonRegistry.addDimension();
		MHFCCommonRegistry.addSmelting();
		MHFCCommonRegistry.addTile();
		MHFCCommonRegistry.addPacket();
		MHFCCommonRegistry.addAreas();
		MHFCCommonRegistry.addQuests();
	}

	public static void staticInit() {
		MHFCCommonRegistry.addPotion();
		MHFCCommonRegistry.addBlocks();
		MHFCCommonRegistry.addMonsters();
		MHFCCommonRegistry.addItems();
		MHFCQuestRegistry.staticInit();
		MHFCEventRegistry.staticInit();

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

	private static void addBlocks() {
		MHFCBlockRegistry.staticInit();
	}

	private static void addSmelting() {
		MHFCSmeltingRegistry.init();
		MHFCCommonRegistry.logger.info("Smelting registered");
	}

	private static void addPacket() {
		MHFCPacketRegistry.init();
		MHFCCommonRegistry.logger.info("Packets registered");
	}

	private static void addItems() {
		MHFCItemRegistry.staticInit();
	}

	private static void addRecipes() {
		MHFCCraftingRegistry.init();
		MHFCHunterCraftingRegistry.init();
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

	private static void addPotion() {
		MHFCPotionRegistry.staticInit();
	}

	private static void addEvent() {
		MHFCEventRegistry.init();
		MHFCCommonRegistry.logger.info("Events registered");
	}

	private static void addMonsters() {
		MHFCEntityRegistry.staticInit();
	}

	private static void addAreas() {
		MHFCExplorationRegistry.init();
		AreaRegistry.init();
		MHFCExplorationRegistry.init();
		MHFCCommonRegistry.logger.info("Areas and exploration registered");
	}

}
