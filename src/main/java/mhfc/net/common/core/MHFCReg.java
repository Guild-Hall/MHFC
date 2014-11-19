package mhfc.net.common.core;

import mhfc.net.MHFCMain;
import mhfc.net.client.quests.MHFCRegQuestVisual;
import mhfc.net.common.core.registry.MHFCRegBlock;
import mhfc.net.common.core.registry.MHFCRegCrafting;
import mhfc.net.common.core.registry.MHFCRegCraftingHunter;
import mhfc.net.common.core.registry.MHFCRegEntity;
import mhfc.net.common.core.registry.MHFCRegEquipmentRecipe;
import mhfc.net.common.core.registry.MHFCRegEvents;
import mhfc.net.common.core.registry.MHFCRegItem;
import mhfc.net.common.core.registry.MHFCRegPacket;
import mhfc.net.common.core.registry.MHFCRegPotion;
import mhfc.net.common.core.registry.MHFCRegQuests;
import mhfc.net.common.core.registry.MHFCRegRenderEntity;
import mhfc.net.common.core.registry.MHFCRegRenderID;
import mhfc.net.common.core.registry.MHFCRegRenderItem;
import mhfc.net.common.core.registry.MHFCRegRenderTile;
import mhfc.net.common.core.registry.MHFCRegRenderWeapon;
import mhfc.net.common.core.registry.MHFCRegSmelting;
import mhfc.net.common.core.registry.MHFCRegStringDecode;
import mhfc.net.common.core.registry.MHFCRegTile;

public class MHFCReg {
	public static void init() {
		addBlocks();
		addItems();
		addRender();
		addRecipes();
		addDimension();
		addSmelting();
		addTile();
		addMonsters();
		addPotion();
		addEvent();
		addPacket();
		addQuests();
		addStringDecoders();
		addCraftingRecipes();
	}

	public static void addCraftingRecipes() {
		MHFCRegCraftingHunter.craftAll();
		MHFCRegEquipmentRecipe.init();
		MHFCMain.logger.info("[MHFC] Recipes are now inserted");
	}

	public static void addStringDecoders() {
		MHFCRegStringDecode.init();
		MHFCMain.logger.info("[MHFC] Client-Server string decoding initialize");
	}

	public static void addBlocks() {
		MHFCRegBlock.init();
		MHFCMain.logger.info("[MHFC] Block is now initialize");
	}

	public static void addSmelting() {
		MHFCRegSmelting.init();
		MHFCMain.logger.info("[MHFC] Smelting is now initialize");
	}

	public static void addPacket() {
		MHFCRegPacket.init();
		MHFCMain.logger.info("[MHFC] Packet syncing !!");
	}

	public static void addItems() {
		MHFCRegItem.init();
		MHFCMain.logger.info("[MHFC] Items is now initialize");
	}

	public static void addRecipes() {
		MHFCRegCrafting.init();
		MHFCMain.logger.info("[MHFC] Recipes is now initialize");
	}

	public static void addDimension() {
		// MHFCRegDimension.init();
		// MHFCMain.logger.info("[MHFC] Dimension is now initialize");
	}

	public static void addTile() {
		MHFCRegTile.init();
		MHFCMain.logger.info("[MHFC] Tile Entity is now initialize");
	}

	public static void addRender() {
		addRenderTile();
		addRenderMob();
		addRenderWeapon();
		addRenderItem();
		addRenderID();
		MHFCMain.logger.info("[MHFC] Rendering is now initialize");
	}

	public static void addPotion() {
		MHFCRegPotion.init();
		MHFCMain.logger.info("[MHFC] Potion is now initialize");
	}

	public static void addEvent() {
		MHFCRegEvents.init();
		MHFCMain.logger.info("[MHFC] Events are now initialize");
	}

	public static void addRenderTile() {
		MHFCRegRenderTile.render();
	}

	public static void addRenderMob() {
		MHFCRegRenderEntity.render();
	}

	public static void addMonsters() {
		MHFCRegEntity.render();
	}

	public static void addRenderWeapon() {
		MHFCRegRenderWeapon.render();
	}

	public static void addRenderItem() {
		MHFCRegRenderItem.render();
	}

	public static void addRenderID() {
		MHFCRegRenderID.renderID();
	}

	public static void addQuests() {
		if (MHFCMain.isClient()) {
			MHFCRegQuestVisual.init();
			MHFCMain.logger.info("[MHFC] Quest Client is now initialized");
		}
		MHFCRegQuests.init();
		MHFCMain.logger.info("[MHFC] Quest Server is now initialized");

		MHFCMain.logger.info("[MHFC] Quests is now initialized");
	}

}
