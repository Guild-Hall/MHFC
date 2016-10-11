package mhfc.net.common.item.block;

import java.util.List;

import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.ItemColor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockStunTrap extends ItemBlock implements IItemColored {

	public ItemBlockStunTrap(Block getBlock) {
		super(getBlock);
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			List<String> par3List,
			boolean par4) {
		par3List.add("A tool that temporarily renders a");
		par3List.add("monster immobile and unable to attack.");
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int texIndex) {
		return ItemColor.GREEN.getRGB();
	}

}
