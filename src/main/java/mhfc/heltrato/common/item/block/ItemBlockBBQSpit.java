package mhfc.heltrato.common.item.block;

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
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add("Hunters use this to cook during adventure");
	}

}
