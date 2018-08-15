package mhfc.net.common.item.block;

import java.util.List;

import mhfc.net.common.item.IItemSimpleModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBBQSpit extends ItemBlock implements IItemSimpleModel {
	// FIXME: this and similar belongs into ResourceInterface
	private static final ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(
			"mhfc:models/block/bbq_spit.mcmdl");

	public ItemBlockBBQSpit(Block getBlock) {
		super(getBlock);
		setMaxStackSize(1);
	}

	@Override
	public ModelResourceLocation getModel() {
		return MODEL_LOCATION;
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add("Hunters use this to cook during adventure");
	}

}
