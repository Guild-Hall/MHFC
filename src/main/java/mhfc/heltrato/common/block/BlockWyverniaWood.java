package mhfc.heltrato.common.block;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWyverniaWood extends Block{
	
	
	@SideOnly(Side.CLIENT)
    private IIcon[] tree_side;
    @SideOnly(Side.CLIENT)
    private IIcon[] tree_top;
    
	public BlockWyverniaWood(){
		super(Material.wood);
		setBlockName("wyverniawood");
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs creativetabs, List list){
		for(int i = 0; i < MHFCReference.logBlocks.length; i++){
			list.add(new ItemStack(block, 1, i));
		}
	}
	
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		tree_side = new IIcon[MHFCReference.logBlocks.length];
		tree_top = new IIcon[MHFCReference.logBlocks.length];
			
		for (int i = 0; i < MHFCReference.logBlocks.length; i++)
		{
			tree_side[i] = iconRegister.registerIcon("mhfc:"+ MHFCReference.logBlocks[i] + "side");
			tree_top[i] = iconRegister.registerIcon("mhfc:"+ MHFCReference.logBlocks[i] + "top");
		}

	}
	
	public IIcon getIcon(int par1, int par2)
	 {
	    return (par1 == 1) || (par1 == 0) ? this.tree_top[par2] : this.tree_side[par2];
	  }


}
