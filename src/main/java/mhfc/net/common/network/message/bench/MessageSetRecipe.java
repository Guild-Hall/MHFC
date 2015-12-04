package mhfc.net.common.network.message.bench;

import io.netty.buffer.ByteBuf;
import mhfc.net.common.core.registry.MHFCEquipementRecipeRegistry;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe;
import mhfc.net.common.crafting.recipes.equipment.EquipmentRecipe.RecipeType;
import mhfc.net.common.item.ItemType;
import mhfc.net.common.network.packet.MessageTileLocation;
import mhfc.net.common.tile.TileHunterBench;

public class MessageSetRecipe extends MessageTileLocation {

	private RecipeType recipeTypeID;
	private ItemType outputTypeID;
	private int recipeID;

	public MessageSetRecipe() {
	}

	public MessageSetRecipe(TileHunterBench bench, EquipmentRecipe r) {
		super(bench);
		if (r != null) {
			this.recipeTypeID = r.getRecipeType();
			this.outputTypeID = r.getOutputType();
			this.recipeID = MHFCEquipementRecipeRegistry.getIDFor(r);
		} else {
			recipeTypeID = RecipeType.MHFC;
			outputTypeID = ItemType.NO_OTHER;
			recipeID = -1;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		recipeTypeID = RecipeType.values()[buf.readInt()];
		outputTypeID = ItemType.values()[buf.readInt()];
		recipeID = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(recipeTypeID.ordinal());
		buf.writeInt(outputTypeID.ordinal());
		buf.writeInt(recipeID);
	}

	public int getRecipeID() {
		return recipeID;
	}

	public RecipeType getRecipeType() {
		return recipeTypeID;
	}

	public ItemType getOutputType() {
		return outputTypeID;
	}
}
