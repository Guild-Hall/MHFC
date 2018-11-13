package mhfc.net.common.item.block;

import java.util.List;

import javax.annotation.Nullable;

import mhfc.net.common.item.IItemSimpleModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockQuestBoard extends ItemBlock implements IItemSimpleModel {
	private static final ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(
			"mhfc:models/block/quest_board.mcmdl");

	public ItemBlockQuestBoard(Block block) {
		super(block);
		setMaxStackSize(1);
	}

	@Override
	public ModelResourceLocation getModel() {
		return MODEL_LOCATION;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("All running quests at your hands");
	}

}
