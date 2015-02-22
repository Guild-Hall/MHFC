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
import mhfc.net.common.item.materials.ItemKirin;
import mhfc.net.common.item.materials.ItemTigrex;
import mhfc.net.common.network.message.bench.MessageBeginCrafting;
import mhfc.net.common.network.message.bench.MessageBenchRefreshRequest;
import mhfc.net.common.network.message.bench.MessageCancelRecipe;
import mhfc.net.common.network.message.bench.MessageCraftingUpdate;
import mhfc.net.common.network.message.bench.MessageSetRecipe;
import mhfc.net.common.network.packet.MessageTileLocation;
import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
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
			EquipmentRecipe rec = getRecipeFor(message.getRecipeID(),
					message.getTypeID());
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

	public static final EquipmentRecipe recipe_tigrex_helm;
	public static final EquipmentRecipe recipe_tigrex_boots;
	public static final EquipmentRecipe recipe_kirin_helm;
	public static final EquipmentRecipe recipe_gs_rathalos_firesword;

	public static int TYPE_ARMOR_HEAD = 0;
	public static int TYPE_ARMOR_BODY = 1;
	public static int TYPE_ARMOR_PANTS = 2;
	public static int TYPE_ARMOR_BOOTS = 3;
	public static int TYPE_WEAPON_GREAT_SWORD = 4;
	public static int TYPE_WEAPON_LONG_SWORD = 5;
	public static int TYPE_WEAPON_HAMMER = 6;
	public static int TYPE_WEAPON_HUNTING_HORN = 7;
	public static int TYPE_WEAPON_SWORD_AND_SHIELD = 8;
	public static int TYPE_WEAPON_DOUBLE_SWORD = 9;
	public static int TYPE_WEAPON_LANCE = 10;
	public static int TYPE_WEAPON_GUNLANCE = 11;
	public static int TYPE_WEAPON_BOW = 12;
	public static int TYPE_WEAPON_SMALL_BOWGUN = 13;
	public static int TYPE_WEAPON_BIG_BOWGUN = 14;

	private static Map<Integer, Set<EquipmentRecipe>> mapOfRecipeSets = new HashMap<Integer, Set<EquipmentRecipe>>();
	private static Map<Integer, List<EquipmentRecipe>> mapOfListOfRecipes = new HashMap<Integer, List<EquipmentRecipe>>();

	static {
		for (int i = 0; i < 15; i++) {
			mapOfRecipeSets.put(new Integer(i),
					new LinkedHashSet<EquipmentRecipe>());
			mapOfListOfRecipes.put(new Integer(i),
					new LinkedList<EquipmentRecipe>());

		}

		List<ItemStack> listReq = new ArrayList<ItemStack>();
		ItemStack s;
		for (int a = 0; a < 3; a++) {
			s = new ItemStack(MHFCItemRegistry.MHFCItemtigrex, 4);
			s.setItemDamage(ItemTigrex.TigrexSubType.SCALE.ordinal());
			listReq.add(s);
		}
		s = new ItemStack(MHFCItemRegistry.MHFCItemtigrex, 1);
		s.setItemDamage(ItemTigrex.TigrexSubType.SHELL.ordinal());
		listReq.add(s);
		recipe_tigrex_helm = new EquipmentRecipe(new ItemStack(
				MHFCItemRegistry.armor_tigrex_helm), listReq, 200, 600);
		MHFCEquipementRecipeRegistry.register(recipe_tigrex_helm);

		listReq = new ArrayList<ItemStack>();
		for (int a = 0; a < 3; a++) {
			s = new ItemStack(MHFCItemRegistry.MHFCItemtigrex, 4);
			s.setItemDamage(ItemTigrex.TigrexSubType.SHELL.ordinal());
			listReq.add(s);
		}
		s = new ItemStack(MHFCItemRegistry.MHFCItemtigrex, 1);
		s.setItemDamage(ItemTigrex.TigrexSubType.SCALE.ordinal());
		listReq.add(s);
		recipe_tigrex_boots = new EquipmentRecipe(new ItemStack(
				MHFCItemRegistry.armor_tigrex_chest), listReq, 150, 300);
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
				MHFCItemRegistry.armor_kirin_helm), listReq, 300, 300);
		MHFCEquipementRecipeRegistry.register(recipe_kirin_helm);

		listReq = new ArrayList<ItemStack>();
		for (int a = 0; a < 1; a++) {
			s = new ItemStack(MHFCItemRegistry.mhfcitemkirin, 2);
			s.setItemDamage(ItemKirin.KirinSubType.MANE.ordinal());
			listReq.add(s);
		}
		s = new ItemStack(MHFCItemRegistry.mhfcitemkirin, 6);
		s.setItemDamage(ItemKirin.KirinSubType.THUNDERTAIL.ordinal());
		listReq.add(s);
		recipe_gs_rathalos_firesword = new EquipmentRecipe(new ItemStack(
				MHFCItemRegistry.weapon_gs_rathalosfire), listReq, 300, 900);
		MHFCEquipementRecipeRegistry.register(recipe_gs_rathalos_firesword);
	}

	public static int getType(EquipmentRecipe recipe) {
		if (recipe == null)
			return -1;
		Item it = recipe.getRecipeOutput().getItem();
		if (it instanceof ItemArmor) {
			return ((ItemArmor) it).armorType;
		}
		String name = it.getUnlocalizedName();
		return 5;
	}

	public static Set<EquipmentRecipe> getRecipesFor(int type) {
		return mapOfRecipeSets.get(new Integer(type));
	}

	private static boolean register(EquipmentRecipe recipe, int type) {
		if (type < 0 || type > 14)
			return false;
		boolean inserted = mapOfRecipeSets.get(new Integer(type)).add(recipe);
		if (inserted) {
			mapOfListOfRecipes.get(new Integer(type)).add(recipe);
		}
		return inserted;
	}

	public static void register(EquipmentRecipe recipe) {
		register(recipe, getType(recipe));
	}

	public static int getIDFor(EquipmentRecipe recipe, int type) {
		List<EquipmentRecipe> list = mapOfListOfRecipes.get(new Integer(type));
		if (list == null)
			return -1;
		return list.indexOf(recipe);
	}

	public static EquipmentRecipe getRecipeFor(int id, int type) {
		List<EquipmentRecipe> list = mapOfListOfRecipes.get(new Integer(type));
		if (list == null)
			return null;
		if (id < 0 || id >= list.size())
			return null;
		return list.get(id);
	}
}
