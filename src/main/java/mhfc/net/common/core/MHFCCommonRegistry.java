package mhfc.net.common.core;

import org.apache.logging.log4j.Logger;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.core.registry.MHFCCraftingRegistry;
import mhfc.net.common.core.registry.MHFCDimensionRegistry;
import mhfc.net.common.core.registry.MHFCEntityRegistry;
import mhfc.net.common.core.registry.MHFCEventRegistry;
import mhfc.net.common.core.registry.MHFCHunterCraftingRegistry;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.core.registry.MHFCPacketRegistry;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.core.registry.MHFCQuestBuildRegistry;
import mhfc.net.common.core.registry.MHFCQuestRegistry;
import mhfc.net.common.core.registry.MHFCSmeltingRegistry;
import mhfc.net.common.core.registry.MHFCTileRegistry;
import mhfc.net.common.util.MHFCStringDecode;

public class MHFCCommonRegistry {
	private final static Logger logger;

	static {
		MHFCMain.checkPreInitialized();
		logger = MHFCMain.logger;
	}

	public static void init() {
		MHFCCommonRegistry.addStringDecoders();
		MHFCCommonRegistry.addBlocks();
		MHFCCommonRegistry.addItems();
		MHFCCommonRegistry.addRecipes();
		MHFCCommonRegistry.addDimension();
		MHFCCommonRegistry.addSmelting();
		MHFCCommonRegistry.addTile();
		MHFCCommonRegistry.addMonsters();
		MHFCCommonRegistry.addPotion();
		MHFCCommonRegistry.addEvent();
		MHFCCommonRegistry.addPacket();
		MHFCCommonRegistry.addQuests();
	}

	private static void addStringDecoders() {
		MHFCStringDecode.init();
	}

	private static void addQuests() {
		MHFCQuestRegistry.init();
		MHFCQuestBuildRegistry.init();
		MHFCMain.logger.info("Quest Server registered");
	}

	private static void addBlocks() {
		MHFCBlockRegistry.init();
		MHFCCommonRegistry.logger.info("Blocks registered");
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
		MHFCItemRegistry.init();
		MHFCCommonRegistry.logger.info("Items registered");
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
		MHFCPotionRegistry.init();
		MHFCCommonRegistry.logger.info("Potions registered");
	}

	private static void addEvent() {
		MHFCEventRegistry.init();
		MHFCCommonRegistry.logger.info("Events registered");
	}

	private static void addMonsters() {
		MHFCEntityRegistry.init();
		MHFCCommonRegistry.logger.info("Monsters registered");
	}
}
