package mhfc.net.common.network.packet;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.core.registry.MHFCRegEquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.tile.TileHunterBench;

public class MessageSetRecipe extends MessageTileLocation {

	private int recipeID;
	private int typeID;

	public MessageSetRecipe() {
	}

	public MessageSetRecipe(TileHunterBench bench, EquipmentRecipe r) {
		super(bench);
		this.typeID = MHFCRegEquipmentRecipe.getType(r);
		this.recipeID = MHFCRegEquipmentRecipe.getIDFor(r, typeID);
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