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

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List infos, boolean advanced) {
		infos.add("All running quests at your hands");
	}

}
