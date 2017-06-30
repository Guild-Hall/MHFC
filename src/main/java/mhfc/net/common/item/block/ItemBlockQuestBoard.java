package mhfc.net.common.item.block;

import java.util.List;

import mhfc.net.common.item.IItemSimpleModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

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
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> infos, boolean advanced) {
		infos.add("All running quests at your hands");
	}

}
