package mhfc.net.common.core;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.core.registry.MHFCCraftingRegistry;
import mhfc.net.common.core.registry.MHFCEntityRegistry;
import mhfc.net.common.core.registry.MHFCEventRegistry;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.core.registry.MHFCPacketRegistry;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.core.registry.MHFCRegTile;
import mhfc.net.common.core.registry.MHFCSmeltingRegistry;

import org.apache.logging.log4j.Logger;

public class MHFCCommonRegistry {
	private final static Logger logger;

	static {
		MHFCMain.checkPreInitialized();
		logger = MHFCMain.logger;
	}

	public static void init() {
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
	}

	public static void addBlocks() {
		MHFCBlockRegistry.init();
		logger.info("Blocks registered");
	}

	public static void addSmelting() {
		MHFCSmeltingRegistry.init();
		logger.info("Smelting registered");
	}

	public static void addPacket() {
		MHFCPacketRegistry.init();
		logger.info("Packets registered");
	}

	public static void addItems() {
		MHFCItemRegistry.init();
		logger.info("Items registered");
	}

	public static void addRecipes() {
		MHFCCraftingRegistry.init();
		logger.info("Recipes registered");
	}

	public static void addDimension() {
		// MHFCRegDimension.init();
		// logger.info("Dimension registered");
	}

	public static void addTile() {
		MHFCRegTile.init();
		logger.info("Tile Entities registered");
	}

	public static void addPotion() {
		MHFCPotionRegistry.init();
		logger.info("Potions registered");
	}
	public static void addEvent() {
		MHFCEventRegistry.init();
		logger.info("Events registered");
	}
	public static void addMonsters() {
		MHFCEntityRegistry.init();
		logger.info("Monsters registered");
	}
}
