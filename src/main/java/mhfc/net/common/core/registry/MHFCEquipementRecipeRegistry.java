package mhfc.net.common.core.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mhfc.net.MHFCMain;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.item.ItemType;
import mhfc.net.common.item.materials.ItemDeviljho;
import mhfc.net.common.item.materials.ItemIngot;
import mhfc.net.common.item.materials.ItemKirin;
import mhfc.net.common.item.materials.ItemRathalos;
import mhfc.net.common.item.materials.ItemRathalos.RathalosSubType;
import mhfc.net.common.item.materials.ItemRemobra;
import mhfc.net.common.item.materials.ItemSac.SacSubType;
import mhfc.net.common.item.materials.ItemTigrex;
import mhfc.net.common.network.message.bench.MessageBeginCrafting;
import mhfc.net.common.network.message.bench.MessageBenchRefreshRequest;
import mhfc.net.common.network.message.bench.MessageCancelRecipe;
import mhfc.net.common.network.message.bench.MessageCraftingUpdate;
import mhfc.net.common.network.message.bench.MessageSetRecipe;
import mhfc.net.common.network.packet.MessageTileLocation;
import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MHFCEquipementRecipeRegistry {

	public static class BenchRefreshHandler
			implements
				IMessageHandler<MessageBenchRefreshRequest, MessageCraftingUpdate> {

		@Override
		public MessageCraftingUpdate onMessage(
				MessageBenchRefreshRequest message, MessageContext ctx) {
			TileHunterBench b = getHunterBench(message);
			return new MessageCraftingUpdate(b);
		}

	}

	public static class SetRecipeHandler
			implements
				IMessageHandler<MessageSetRecipe, MessageCraftingUpdate> {
		@Override
		public MessageCraftingUpdate onMessage(MessageSetRecipe message,
				MessageContext ctx) {
			TileHunterBench b = getHunterBench(message);
			ItemType type = ItemType.values()[message.getTypeID()];
			EquipmentRecipe rec = getRecipeFor(message.getRecipeID(), type);
			b.changeRecipe(rec);
			return new MessageCraftingUpdate(b);
		}
	}

	public static class CancelRecipeHandler
			implements
				IMessageHandler<MessageCancelRecipe, MessageCraftingUpdate> {

		@Override
		public MessageCraftingUpdate onMessage(MessageCancelRecipe message,
				MessageContext ctx) {
			TileHunterBench b = getHunterBench(message);
			b.cancelRecipe();
			return new MessageCraftingUpdate(b);
		}
	}

	public static class BeginCraftingHandler
			implements
				IMessageHandler<MessageBeginCrafting, MessageCraftingUpdate> {
		@Override
		public MessageCraftingUpdate onMessage(MessageBeginCrafting message,
				MessageContext ctx) {
			TileHunterBench b = getHunterBench(message);
			b.beginCrafting();
			return new MessageCraftingUpdate(b);
		}
	}

	public static class CraftingUpdateHandler
			implements
				IMessageHandler<MessageCraftingUpdate, IMessage> {
		@Override
		public IMessage onMessage(MessageCraftingUpdate message,
				MessageContext ctx) {
			TileHunterBench b = getHunterBench(message, false);
			if (b != null) {
				b.readCustomUpdate(message.getNBTTag());
			}
			return null;
		}

	}

	private static TileHunterBench getHunterBench(MessageTileLocation message) {
		return MHFCEquipementRecipeRegistry.getHunterBench(message, true);
	}

	public static TileHunterBench getHunterBench(MessageTileLocation message,
			boolean server) {
		World world = null;
		if (server) {
			int id = message.getDimensionID();
			MinecraftServer mcserver = FMLCommonHandler.instance()
					.getMinecraftServerInstance();
			if (mcserver == null) {
				return null;
			}
			world = mcserver.worldServerForDimension(id);
		} else {
			WorldClient clientW = FMLClientHandler.instance().getWorldClient();
			if (clientW.provider.dimensionId == message.getDimensionID())
				world = clientW;
			else
				world = null;
		}
		if (world != null) {
			TileEntity bench = world.getTileEntity(message.getX(),
					message.getY(), message.getZ());
			if (!(bench instanceof TileHunterBench)) {
				MHFCMain.logger
						.error("No tile entity for a block hunter bench found");

				return null;
			}
			return (TileHunterBench) bench;
		}
		return null;
	}
	
	//Armors
	public static final EquipmentRecipe recipe_tigrex_helm;
	public static final EquipmentRecipe recipe_tigrex_chest;
	public static final EquipmentRecipe recipe_tigrex_legs;
	public static final EquipmentRecipe recipe_tigrex_boots;
	public static final EquipmentRecipe recipe_kirin_helm;
	
	//Weapons
	public static final EquipmentRecipe recipe_gs_rathalos_firesword;
	public static final EquipmentRecipe recipe_gs_kirin_thundersword;
	public static final EquipmentRecipe recipe_gs_berserker;
	public static final EquipmentRecipe recipe_gs_agito;
	public static final EquipmentRecipe recipe_gs_bone;
	public static final EquipmentRecipe recipe_hm_war;
	public static final EquipmentRecipe recipe_hm_warplus;
	public static final EquipmentRecipe recipe_hm_warslammer;
	public static final EquipmentRecipe recipe_hm_tigrex;
	public static final EquipmentRecipe recipe_hm_kirin;
	public static final EquipmentRecipe recipe_hm_rathalos;
	public static final EquipmentRecipe recipe_hm_devilsdue;
	public static final EquipmentRecipe recipe_ls_ironkatana;
	public static final EquipmentRecipe recipe_ls_darkvipern;
	public static final EquipmentRecipe recipe_hh_ivoryhorn;
	public static final EquipmentRecipe recipe_hh_metalbagpipe;
	public static final EquipmentRecipe recipe_hh_tigrex;
	

	private static Map<ItemType, Set<EquipmentRecipe>> mapOfRecipeSets = new HashMap<ItemType, Set<EquipmentRecipe>>();
	private static Map<ItemType, List<EquipmentRecipe>> mapOfListOfRecipes = new HashMap<ItemType, List<EquipmentRecipe>>();

	static {
		ItemType[] types = ItemType.values();
		for (int i = 0; i < types.length; i++) {
			mapOfRecipeSets.put(types[i], new LinkedHashSet<EquipmentRecipe>());
			mapOfListOfRecipes.put(types[i], new LinkedList<EquipmentRecipe>());

		}

		List<ItemStack> listReq = new ArrayList<ItemStack>();
		ItemStack s;
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 3,
				ItemTigrex.TigrexSubType.SKULLSHELL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 3,
				ItemTigrex.TigrexSubType.SCALE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 2,
				ItemTigrex.TigrexSubType.SHELL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 2,
				ItemIngot.IngotsSubType.MACHALTIE.ordinal());
		recipe_tigrex_helm = new EquipmentRecipe(new ItemStack(
				MHFCItemRegistry.armor_tigrex_helm), listReq, 200, 400);
		MHFCEquipementRecipeRegistry.register(recipe_tigrex_helm);

		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 2,
				ItemTigrex.TigrexSubType.FANG.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 3,
				ItemTigrex.TigrexSubType.CLAW.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 3,
				ItemTigrex.TigrexSubType.SHELL.ordinal());
		recipe_tigrex_chest = new EquipmentRecipe(new ItemStack(
				MHFCItemRegistry.armor_tigrex_chest), listReq, 200, 600);
		MHFCEquipementRecipeRegistry.register(recipe_tigrex_chest);

		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 3,
				ItemTigrex.TigrexSubType.CLAW.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 2,
				ItemTigrex.TigrexSubType.SCALE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 2,
				ItemTigrex.TigrexSubType.FANG.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 5,
				ItemIngot.IngotsSubType.MACHALTIE.ordinal());
		recipe_tigrex_legs = new EquipmentRecipe(new ItemStack(
				MHFCItemRegistry.armor_tigrex_legs), listReq, 200, 400);
		MHFCEquipementRecipeRegistry.register(recipe_tigrex_legs);

		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 3,
				ItemTigrex.TigrexSubType.FANG.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 3,
				ItemTigrex.TigrexSubType.SCALE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 2,
				ItemTigrex.TigrexSubType.SHELL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 2,
				ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		recipe_tigrex_boots = new EquipmentRecipe(new ItemStack(
				MHFCItemRegistry.armor_tigrex_boots), listReq, 200, 300);
		MHFCEquipementRecipeRegistry.register(recipe_tigrex_boots);

		listReq = new ArrayList<ItemStack>();
		for (int a = 0; a < 2; a++) {
			s = new ItemStack(MHFCItemRegistry.mhfcitemkirin, 4);
			s.setItemDamage(ItemKirin.KirinSubType.MANE.ordinal());
			listReq.add(s);
		}
		s = new ItemStack(MHFCItemRegistry.mhfcitemkirin, 4);
		s.setItemDamage(ItemKirin.KirinSubType.THUNDERTAIL.ordinal());
		listReq.add(s);
		recipe_kirin_helm = new EquipmentRecipe(new ItemStack(
				MHFCItemRegistry.armor_kirin_helm), listReq, 500, 300);
		MHFCEquipementRecipeRegistry.register(recipe_kirin_helm);

		/*@Andreas: Im sorry to adjust the formating code here but i can understand this a little bit more. Thanks -Heltrato.
		 * **/
		
		//GreatSword
		
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, Item.getItemById(352), 6, 0);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemlumberbar, 4, 0);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemmoldediron, 2, 0);
		recipe_gs_bone = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_gs_bone), listReq, 100, 500);
		MHFCEquipementRecipeRegistry.register(recipe_gs_bone);
//dont mind// 
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemrathalos, 2,RathalosSubType.WING.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemsac, 2,SacSubType.FIRE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemrathalos, 5,RathalosSubType.MARROW.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemfirestone, 3, 0);
		recipe_gs_rathalos_firesword = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_gs_rathalosfire), listReq, 300, 900);
		MHFCEquipementRecipeRegistry.register(recipe_gs_rathalos_firesword);
		//
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemkirin, 7,ItemKirin.KirinSubType.MANE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 5,ItemIngot.IngotsSubType.CARBALITE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemkirin, 4,ItemKirin.KirinSubType.LIGHTCRYSTAL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemkirin, 2, ItemKirin.KirinSubType.PURECRYSTAL.ordinal());
		recipe_gs_kirin_thundersword = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_gs_kirinthunders), listReq, 300, 900);
		MHFCEquipementRecipeRegistry.register(recipe_gs_kirin_thundersword);
		//
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 6, ItemTigrex.TigrexSubType.SCALE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 7, ItemTigrex.TigrexSubType.FANG.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 3, ItemTigrex.TigrexSubType.SHELL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 4, ItemTigrex.TigrexSubType.SCALE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 1, ItemTigrex.TigrexSubType.SKULLSHELL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 2, ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		recipe_gs_agito = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_gs_tigrex), listReq, 500, 1000);
		MHFCEquipementRecipeRegistry.register(recipe_gs_agito);
		//
		listReq = new ArrayList<ItemStack>();
		
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemdeviljho, 7, ItemDeviljho.DeviljhoSubType.FANG.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemdeviljho, 7, ItemDeviljho.DeviljhoSubType.SCALE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemdeviljho, 5, ItemDeviljho.DeviljhoSubType.TALON.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemdeviljho, 3, ItemDeviljho.DeviljhoSubType.SCALP.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 5, ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		recipe_gs_berserker = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_gs_berserkers), listReq, 600, 1500);
		MHFCEquipementRecipeRegistry.register(recipe_gs_berserker);
		//
		
		//Hammer Class
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, Item.getItemById(265), 7, 0);// 265 is iron ingot
		addItemStackToList(listReq,	Item.getItemFromBlock(MHFCBlockRegistry.mhfcblockdiskstone), 2, 0);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemsteelbar, 4, 0);
		recipe_hm_war = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hm_warhammer), listReq, 100, 200);
		MHFCEquipementRecipeRegistry.register(recipe_hm_war);
		//
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.weapon_hm_warhammer, 1, 0);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemsteelbar, 2, 0);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemmoldediron, 1, 0);
		addItemStackToList(listReq, Item.getItemById(265), 5, 0);// 265 is iron
		recipe_hm_warplus = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hm_warhammerplus), listReq, 100, 250);
		MHFCEquipementRecipeRegistry.register(recipe_hm_warplus);
		//
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.weapon_hm_warhammerplus, 1, 0);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemsteelbar, 7, 0);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemmoldediron, 3, 0);
		recipe_hm_warslammer = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hm_warslammer), listReq, 150, 300);
		MHFCEquipementRecipeRegistry.register(recipe_hm_warslammer);
		//
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemrathalos, 8, ItemRathalos.RathalosSubType.SHELL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemrathalos, 2, ItemRathalos.RathalosSubType.MARROW.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemfirestone, 1, 0);
		recipe_hm_rathalos = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hm_rathalos), listReq, 300, 500);
		MHFCEquipementRecipeRegistry.register(recipe_hm_rathalos);
		//
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 3, ItemTigrex.TigrexSubType.CLAW.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 5, ItemTigrex.TigrexSubType.SCALE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 5, ItemTigrex.TigrexSubType.SHELL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.MHFCItemWyvernCoin, 10, 0);
		recipe_hm_tigrex = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hm_tigrex), listReq, 400, 700);
		MHFCEquipementRecipeRegistry.register(recipe_hm_tigrex);
		//
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemkirin,10, ItemKirin.KirinSubType.MANE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemkirin, 6, ItemKirin.KirinSubType.PLATINUMMANE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemkirin, 5, ItemKirin.KirinSubType.LIGHTCRYSTAL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemkirin, 3, ItemKirin.KirinSubType.PURECRYSTAL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemkirin, 1, ItemKirin.KirinSubType.THUNDERTAIL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 4, ItemIngot.IngotsSubType.ELTALITE.ordinal());
		recipe_hm_kirin = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hm_kirinspark), listReq, 750, 1200);
		MHFCEquipementRecipeRegistry.register(recipe_hm_kirin);
		//
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemdeviljho, 10, ItemDeviljho.DeviljhoSubType.FANG.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemdeviljho, 4, ItemDeviljho.DeviljhoSubType.SCALE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemdeviljho, 6, ItemDeviljho.DeviljhoSubType.SCALP.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 4, ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		recipe_hm_devilsdue = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hm_devilsdue), listReq, 750, 1800);
		MHFCEquipementRecipeRegistry.register(recipe_hm_devilsdue);
		//
		//Longsword class
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemsteelbar, 7, 0);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemlumberbar, 2, 0);
		recipe_ls_ironkatana = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_ls_ironkatana), listReq, 150, 600);
		MHFCEquipementRecipeRegistry.register(recipe_ls_ironkatana);
		//
		listReq = new ArrayList<ItemStack>();
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemremobra, 6, ItemRemobra.RemobraSubType.SKIN.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemremobra, 6, ItemRemobra.RemobraSubType.STRIPE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemremobra, 2, ItemRemobra.RemobraSubType.WING.ordinal());
		recipe_ls_darkvipern = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_ls_darkvipern), listReq, 500, 750);
		MHFCEquipementRecipeRegistry.register(recipe_ls_darkvipern);
		//
		//Hunting Horn Class
		listReq = new ArrayList<ItemStack>();
		recipe_hh_ivoryhorn = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hh_ivoryhorn), listReq, 100, 250);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 3, ItemIngot.IngotsSubType.MACHALTIE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemlumberbar, 1, 0);
		addItemStackToList(listReq, Item.getItemById(352), 6, 0);
		MHFCEquipementRecipeRegistry.register(recipe_hh_ivoryhorn);
		
		listReq = new ArrayList<ItemStack>();
		recipe_hh_metalbagpipe = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hh_metalbagpipe), listReq, 200, 400);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 3, ItemIngot.IngotsSubType.MACHALTIE.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemmoldediron, 2, 0);
		MHFCEquipementRecipeRegistry.register(recipe_hh_metalbagpipe);
		
		listReq = new ArrayList<ItemStack>();
		recipe_hh_tigrex = new EquipmentRecipe(new ItemStack(MHFCItemRegistry.weapon_hh_tigrex), listReq, 350, 600);
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 5, ItemTigrex.TigrexSubType.SHELL.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemtigrex, 7, ItemTigrex.TigrexSubType.FANG.ordinal());
		addItemStackToList(listReq, MHFCItemRegistry.mhfcitemingot, 10, ItemIngot.IngotsSubType.DRAGONITE.ordinal());
		MHFCEquipementRecipeRegistry.register(recipe_hh_tigrex);
	}

	public static void addItemStackToList(List<ItemStack> list, Item item,
			int stackSize, int subID) {
		ItemStack s = new ItemStack(item, stackSize);
		s.setItemDamage(subID);
		list.add(s);
	}

	public static ItemType getType(EquipmentRecipe recipe) {
		if (recipe == null)
			return ItemType.NO_OTHER;
		ItemStack it = recipe.getRecipeOutput();
		return ItemType.getTypeOf(it);
	}

	public static Set<EquipmentRecipe> getRecipesForType(ItemType type) {
		return mapOfRecipeSets.get(type);
	}

	private static boolean register(EquipmentRecipe recipe, ItemType type) {
		boolean inserted = mapOfRecipeSets.get(type).add(recipe);
		if (inserted) {
			mapOfListOfRecipes.get(type).add(recipe);
		}
		return inserted;
	}

	public static void register(EquipmentRecipe recipe) {
		register(recipe, getType(recipe));
	}

	public static int getIDFor(EquipmentRecipe recipe, ItemType type) {
		List<EquipmentRecipe> list = mapOfListOfRecipes.get(type);
		if (list == null)
			return -1;
		return list.indexOf(recipe);
	}

	public static EquipmentRecipe getRecipeFor(int id, ItemType type) {
		List<EquipmentRecipe> list = mapOfListOfRecipes.get(type);
		if (list == null)
			return null;
		if (id < 0 || id >= list.size())
			return null;
		return list.get(id);
	}
}
