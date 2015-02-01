package mhfc.net.common.core;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.core.registry.MHFCCraftingRegistry;
import mhfc.net.common.core.registry.MHFCEntityRegistry;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry;
import mhfc.net.common.core.registry.MHFCEventRegistry;
import mhfc.net.common.core.registry.MHFCHunterCraftingRegistry;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.core.registry.MHFCPacketRegistry;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.core.registry.MHFCQuestsRegistry;
import mhfc.net.common.core.registry.MHFCSmeltingRegistry;
import mhfc.net.common.core.registry.MHFCTileRegistry;
import mhfc.net.common.util.MHFCStringDecode;

import org.apache.logging.log4j.Logger;

public class MHFCCommonRegistry {
	private final static Logger logger;

	static {
		MHFCMain.checkPreInitialized();
		logger = MHFCMain.logger;
	}

	public static void init() {
		addStringDecoders();
		addBlocks();
		addItems();
		addRecipes();
		addDimension();
		addSmelting();
		addTile();
		addMonsters();
		addPotion();
		addEvent();
		addPacket();
		addQuests();
	}

	private static void addStringDecoders() {
		MHFCStringDecode.init();
	}

	private static void addQuests() {
		MHFCQuestsRegistry.init();
		MHFCMain.logger.info("Quest Server registered");
	}

	private static void addBlocks() {
		MHFCBlockRegistry.init();
		logger.info("Blocks registered");
	}

	private static void addSmelting() {
		MHFCSmeltingRegistry.init();
		logger.info("Smelting registered");
	}

	private static void addPacket() {
		MHFCPacketRegistry.init();
		logger.info("Packets registered");
	}

	private static void addItems() {
		MHFCItemRegistry.init();
		logger.info("Items registered");
	}

	private static void addRecipes() {
		MHFCCraftingRegistry.init();
		MHFCHunterCraftingRegistry.init();
		MHFCEquipementRecipeRegistry.init();
		logger.info("Recipes registered");
	}

	private static void addDimension() {
		// MHFCDimensionRegistry.init();
		// logger.info("Dimension registered");
	}

	private static void addTile() {
		MHFCTileRegistry.init();
		logger.info("Tile Entities registered");
	}

	private static void addPotion() {
		MHFCPotionRegistry.init();
		logger.info("Potions registered");
	}
	private static void addEvent() {
		MHFCEventRegistry.init();
		logger.info("Events registered");
	}
	private static void addMonsters() {
		MHFCEntityRegistry.init();
		logger.info("Monsters registered");
	}
}
