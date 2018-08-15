package mhfc.net.common.item.block;

import mhfc.net.common.item.IItemSimpleModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;

public class ItemBlockIceCrystal extends ItemBlock implements IItemSimpleModel {
	private static final ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(
			"mhfc:models/block/icecrystal.mcmdl");

	public ItemBlockIceCrystal(Block getBlock) {
		super(getBlock);
	}

	@Override
	public ModelResourceLocation getModel() {
		return MODEL_LOCATION;
	}

}
