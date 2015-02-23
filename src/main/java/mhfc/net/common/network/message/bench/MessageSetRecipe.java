package mhfc.net.common.network.message.bench;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.item.ItemType;
import mhfc.net.common.network.packet.MessageTileLocation;
import mhfc.net.common.tile.TileHunterBench;

public class MessageSetRecipe extends MessageTileLocation {

	private int recipeID;
	private int typeID;

	public MessageSetRecipe() {
	}

	public MessageSetRecipe(TileHunterBench bench, EquipmentRecipe r) {
		super(bench);
		ItemType type = MHFCEquipementRecipeRegistry.getType(r);
		this.typeID = type.ordinal();
		this.recipeID = MHFCEquipementRecipeRegistry.getIDFor(r, type);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		recipeID = buf.readInt();
		typeID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
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
