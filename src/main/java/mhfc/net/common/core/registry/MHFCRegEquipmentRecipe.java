package mhfc.net.common.core.registry;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class MHFCRegEquipmentRecipe {

	private static class LocationMessage {
		protected int x;
		protected int y;
		protected int z;
		protected int worldID;

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getZ() {
			return z;
		}

		public int getDimensionID() {
			return worldID;
		}
	}

	public static class SetRecipeMessage extends LocationMessage
			implements
				IMessage {

		private int recipeID;
		private int typeID;

		public SetRecipeMessage() {
		}

		public SetRecipeMessage(TileHunterBench bench, EquipmentRecipe r) {
			this.x = bench.xCoord;
			this.y = bench.yCoord;
			this.z = bench.zCoord;
			this.worldID = bench.getWorldObj().getWorldInfo()
					.getVanillaDimension();
			this.typeID = getType(r);
			this.recipeID = getIDFor(r, typeID);
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			x = buf.readInt();
			y = buf.readInt();
			z = buf.readInt();
			worldID = buf.readInt();
			recipeID = buf.readInt();
			typeID = buf.readInt();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(x);
			buf.writeInt(y);
			buf.writeInt(z);
			buf.writeInt(worldID);
			buf.writeInt(recipeID);
			buf.writeInt(typeID);
		}

		public int getRecipeID() {
			return recipeID;
		}

		public int getTypeID() {
			return typeID;
		}

	}

	public static class BeginCraftingMessage extends LocationMessage
			implements
				IMessage {

		public BeginCraftingMessage() {
		}

		public BeginCraftingMessage(TileHunterBench bench) {
			this.x = bench.xCoord;
			this.y = bench.yCoord;
			this.z = bench.zCoord;
			this.worldID = bench.getWorldObj().getWorldInfo()
					.getVanillaDimension();
		}

		@Override
		public void fromBytes(ByteBuf buf) {
			x = buf.readInt();
			y = buf.readInt();
			z = buf.readInt();
			worldID = buf.readInt();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(x);
			buf.writeInt(y);
			buf.writeInt(z);
			buf.writeInt(worldID);
		}
	}

	public static class SetRecipeHandler
			implements
				IMessageHandler<SetRecipeMessage, IMessage> {
		@Override
		public IMessage onMessage(SetRecipeMessage message, MessageContext ctx) {
			TileHunterBench b = getHunterBench(message);
			EquipmentRecipe rec = getRecipeFor(message.getRecipeID(),
					message.getTypeID());
			b.setRecipe(rec);
			return null;
		}
	}

	public static class BeginCraftingHandler
			implements
				IMessageHandler<BeginCraftingMessage, IMessage> {
		@Override
		public IMessage onMessage(BeginCraftingMessage message,
				MessageContext ctx) {
			TileHunterBench b = getHunterBench(message);
			b.beginCrafting();
			return null;
		}
	}

	private static TileHunterBench getHunterBench(LocationMessage message) {
		int id = message.getDimensionID();
		MinecraftServer server = FMLCommonHandler.instance()
				.getMinecraftServerInstance();
		if (server == null) {
			return null;
		}
		WorldServer world = server.worldServerForDimension(id);
		TileEntity bench = world.getTileEntity(message.getX(), message.getY(),
				message.getZ());
		if (!(bench instanceof TileHunterBench)) {
			System.out.println("No hunter bench on server");
			return null;
		}
		return (TileHunterBench) bench;
	}

	// FIXME mod wide discriminators
	private static int DISCRIMINATOR_BEGIN_CRAFTING = 120;
	private static int DISCRIMINATOR_SET_RECIPE = 121;

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

	public static void init() {
		for (int i = 0; i < 15; i++) {
			mapOfRecipeSets.put(new Integer(i),
					new LinkedHashSet<EquipmentRecipe>());
			mapOfListOfRecipes.put(new Integer(i),
					new LinkedList<EquipmentRecipe>());

		}
		List<ItemStack> listReq = new ArrayList<ItemStack>();
		for (int a = 0; a < 7; a++) {
			listReq.add(new ItemStack(MHFCRegItem.MHFCItemTigrexScale, 4));
		}
		new EquipmentRecipe(new ItemStack(MHFCRegItem.mhfcitemkirinhelm),
				listReq, 200, 600);
		// FIXME mod wide wrapper
		MHFCRegQuests.networkWrapper.registerMessage(
				BeginCraftingHandler.class, BeginCraftingMessage.class,
				DISCRIMINATOR_BEGIN_CRAFTING, Side.SERVER);
		MHFCRegQuests.networkWrapper.registerMessage(SetRecipeHandler.class,
				SetRecipeMessage.class, DISCRIMINATOR_SET_RECIPE, Side.SERVER);
	}

	public static int getType(EquipmentRecipe recipe) {
		if (recipe == null)
			return -1;
		Item it = recipe.getRecipeOutput().getItem();
		if (it instanceof ItemArmor) {
			return ((ItemArmor) it).armorType;
		}
		// TODO long else if
		return 5;
	}

	public static Set<EquipmentRecipe> getRecipesFor(int type) {
		return mapOfRecipeSets.get(new Integer(type));
	}

	public static void register(EquipmentRecipe recipe, int type) {
		if (type < 0 | type > 14)
			return;
		boolean inserted = mapOfRecipeSets.get(new Integer(type)).add(recipe);
		if (inserted)
			mapOfListOfRecipes.get(new Integer(type)).add(recipe);
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
