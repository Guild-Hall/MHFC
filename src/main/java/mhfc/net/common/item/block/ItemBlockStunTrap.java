package mhfc.net.common.item.block;

import java.util.List;

import javax.annotation.Nullable;

import mhfc.net.common.item.IItemColored;
import mhfc.net.common.item.ItemColor;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockStunTrap extends ItemBlock implements IItemColored {

	public ItemBlockStunTrap(Block getBlock) {
		super(getBlock);
		setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("A tool that temporarily renders a");
		tooltip.add("monster immobile and unable to attack.");
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int texIndex) {
		return ItemColor.GREEN.getRGB();
	}

}
