package mhfc.heltrato.common.item.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockWyverniaFlower extends ItemBlock {
	
	
	public ItemBlockWyverniaFlower(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
		maxStackSize = 4;
		
	}
	
	

	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if(i<0||i >= MHFCReference.flowerBlocks.length) {
			i = 0;
		}
		
		return super.getUnlocalizedName() + "." + MHFCReference.flowerBlocks[i];
	}
	
	public int getMetadata(int meta) {
		return meta;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public IIcon getIconFromDamage(int meta)
	{
		//TODO: block		  getIcon()
		return field_150939_a.getIcon(0, meta);
	}

	
	

}
