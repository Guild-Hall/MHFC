package mhfc.net.common.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockBBQSpit extends ItemBlock {

	public ItemBlockBBQSpit(Block getBlock) {
		super(getBlock);
		maxStackSize = 1;
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add("Hunters use this to cook during adventure");
	}

}
