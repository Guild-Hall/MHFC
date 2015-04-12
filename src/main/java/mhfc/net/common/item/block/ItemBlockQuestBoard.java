package mhfc.net.common.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockQuestBoard extends ItemBlock {

	public ItemBlockQuestBoard(Block block) {
		super(block);
		maxStackSize = 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_,
			@SuppressWarnings("rawtypes") List p_77624_3_, boolean p_77624_4_) {
		p_77624_3_.add("All running quests at your hands");
	}

}
