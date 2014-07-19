package mhfc.net.common.core;

import mhfc.net.common.core.registry.MHFCRegBlock;
import mhfc.net.common.core.registry.MHFCRegCrafting;
import mhfc.net.common.core.registry.MHFCRegEntity;
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
import mhfc.net.common.core.registry.MHFCRegTile;

public class MHFCReg {
	public static void init() {
		/*
		 * addBlocks(); addItems(); addRender(); addRecipes(); addDimension();
		 * addSmelting(); addTile(); addMonsters(); addPotion(); addEvent();
		 * addPacket();
		 */
		addQuests();
	}

	public static void addBlocks() {
		MHFCRegBlock.init();
		System.out.println("[MHFC] Block is now initialize");
	}

	public static void addSmelting() {
		MHFCRegSmelting.init();
		System.out.println("[MHFC] Smelting is now initialize");
	}

	public static void addPacket() {
		MHFCRegPacket.init();
		System.out.println("[MHFC] Packet syncing !!");
	}

	public static void addItems() {
		MHFCRegItem.init();
		System.out.println("[MHFC] Items is now initialize");
	}

	public static void addRecipes() {
		MHFCRegCrafting.init();
		System.out.println("[MHFC] Recipes is now initialize");
	}

	public static void addDimension() {
		// MHFCRegDimension.init();
		// System.out.println("[MHFC] Dimension is now initialize");
	}

	public static void addTile() {
		MHFCRegTile.init();
		System.out.println("[MHFC] Tile Entity is now initialize");
	}

	public static void addRender() {
		addRenderTile();
		addRenderMob();
		addRenderWeapon();
		addRenderItem();
		addRenderID();
		System.out.println("[MHFC] Rendering is now initialize");
	}

	public static void addPotion() {
		MHFCRegPotion.init();
		System.out.println("[MHFC] Potion is now initialize");
	}

	public static void addEvent() {
		MHFCRegEvents.init();
		System.out.println("[MHFC] Events are now initialize");
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
		MHFCRegQuests.init();
		System.out.println("[MHFC] Quests is now initialized");
	}

}
