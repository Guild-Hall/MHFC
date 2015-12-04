package mhfc.net.common.core.registry;

import java.util.Set;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.EquipmentRecipeRegistryData;
import mhfc.net.common.core.directors.DirectorEquipmentRecipes;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe.RecipeType;
import mhfc.net.common.network.message.bench.*;
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
			RecipeType recipeType = message.getRecipeType();
			EquipmentRecipe rec = getRecipeFor(message.getRecipeID(),
				recipeType);
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
			TileEntity bench = world.getTileEntity(message.getX(), message
				.getY(), message.getZ());
			if (!(bench instanceof TileHunterBench)) {
				MHFCMain.logger.error(
					"No tile entity for a block hunter bench found");
				return null;
			}
			return (TileHunterBench) bench;
		}
		return null;
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

	public static EquipmentRecipe getRecipeFor(int recipeID,
		RecipeType recipeType) {
		return dataObject.getRecipeFor(recipeID, recipeType);
	}

}
