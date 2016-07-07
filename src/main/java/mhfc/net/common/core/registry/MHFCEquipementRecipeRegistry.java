package mhfc.net.common.core.registry;

import java.util.Set;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.EquipmentRecipeRegistryData;
import mhfc.net.common.core.directors.DirectorEquipmentRecipes;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe.RecipeType;
import mhfc.net.common.network.message.bench.MessageBeginCrafting;
import mhfc.net.common.network.message.bench.MessageBenchRefreshRequest;
import mhfc.net.common.network.message.bench.MessageCancelRecipe;
import mhfc.net.common.network.message.bench.MessageCraftingUpdate;
import mhfc.net.common.network.message.bench.MessageSetRecipe;
import mhfc.net.common.network.packet.MessageTileLocation;
import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MHFCEquipementRecipeRegistry {

	public static class BenchRefreshHandler
			implements
			IMessageHandler<MessageBenchRefreshRequest, MessageCraftingUpdate> {

		@Override
		public MessageCraftingUpdate onMessage(MessageBenchRefreshRequest message, MessageContext ctx) {
			TileHunterBench b = getHunterBenchServer(message);
			return new MessageCraftingUpdate(b);
		}

	}

	public static class SetRecipeHandler implements IMessageHandler<MessageSetRecipe, MessageCraftingUpdate> {
		@Override
		public MessageCraftingUpdate onMessage(MessageSetRecipe message, MessageContext ctx) {
			TileHunterBench b = getHunterBenchServer(message);
			RecipeType recipeType = message.getRecipeType();
			EquipmentRecipe rec = getRecipeFor(message.getRecipeID(), recipeType);
			b.changeRecipe(rec);
			return new MessageCraftingUpdate(b);
		}
	}

	public static class CancelRecipeHandler implements IMessageHandler<MessageCancelRecipe, MessageCraftingUpdate> {

		@Override
		public MessageCraftingUpdate onMessage(MessageCancelRecipe message, MessageContext ctx) {
			TileHunterBench b = getHunterBenchServer(message);
			b.cancelRecipe();
			return new MessageCraftingUpdate(b);
		}
	}

	public static class BeginCraftingHandler implements IMessageHandler<MessageBeginCrafting, MessageCraftingUpdate> {
		@Override
		public MessageCraftingUpdate onMessage(MessageBeginCrafting message, MessageContext ctx) {
			TileHunterBench b = getHunterBenchServer(message);
			b.beginCrafting();
			return new MessageCraftingUpdate(b);
		}
	}

	public static class CraftingUpdateHandler implements IMessageHandler<MessageCraftingUpdate, IMessage> {
		@Override
		public IMessage onMessage(MessageCraftingUpdate message, MessageContext ctx) {
			TileHunterBench b = getHunterBenchClient(message);
			if (b != null) {
				b.readCustomUpdate(message.getNBTTag());
			}
			return null;
		}

	}

	public static TileHunterBench getHunterBenchServer(MessageTileLocation message) {
		World world = null;
		int id = message.getDimensionID();
		MinecraftServer mcserver = FMLCommonHandler.instance().getMinecraftServerInstance();
		if (mcserver == null) {
			return null;
		}
		world = mcserver.worldServerForDimension(id);
		if (world == null) {
			return null;
		}
		TileEntity bench = world.getTileEntity(message.getX(), message.getY(), message.getZ());
		if (!(bench instanceof TileHunterBench)) {
			MHFCMain.logger().error("No tile entity for a block hunter bench found");
			return null;
		}
		return (TileHunterBench) bench;
	}

	@SideOnly(Side.CLIENT)
	public static TileHunterBench getHunterBenchClient(MessageTileLocation message) {
		WorldClient clientW = FMLClientHandler.instance().getWorldClient();
		if (clientW.provider.dimensionId != message.getDimensionID())
			return null;
		TileEntity bench = clientW.getTileEntity(message.getX(), message.getY(), message.getZ());
		if (!(bench instanceof TileHunterBench)) {
			MHFCMain.logger().error("No tile entity for a block hunter bench found");
			return null;
		}
		return (TileHunterBench) bench;
	}

	private static EquipmentRecipeRegistryData dataObject;
	private static DirectorEquipmentRecipes recipeDirector;

	static {
		dataObject = new EquipmentRecipeRegistryData();
		recipeDirector = new DirectorEquipmentRecipes();
		recipeDirector.construct(dataObject);
	}

	public static Set<EquipmentRecipe> getRecipesForType(RecipeType type) {
		return dataObject.getRecipesForType(type);
	}

	public static int getIDFor(EquipmentRecipe recipe) {
		return dataObject.getIDFor(recipe);
	}

	public static EquipmentRecipe getRecipeFor(int recipeID, RecipeType recipeType) {
		return dataObject.getRecipeFor(recipeID, recipeType);
	}

}
