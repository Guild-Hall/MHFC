package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.core.registry.MHFCRegEquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.tile.TileHunterBench;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class MessageTileLocation {
	public static class SetRecipeMessage extends MessageTileLocation
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
			this.typeID = MHFCRegEquipmentRecipe.getType(r);
			this.recipeID = MHFCRegEquipmentRecipe.getIDFor(r, typeID);
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

	public static class BeginCraftingMessage extends MessageTileLocation
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