package mhfc.net.common.block;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWyverniaPlank extends Block {

	
	@SideOnly(Side.CLIENT)
	private IIcon[] texture;
	
	
	public BlockWyverniaPlank() {
		super(Material.wood);
		setBlockName("wyverniaplanks");
		setHardness(0.6f);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister regIcon){
		texture = new IIcon[MHFCReference.plankBlocks.length];
		
		for(int i = 0; i < MHFCReference.plankBlocks.length; i++){
			texture[i] = regIcon.registerIcon("mhfc:" + MHFCReference.plankBlocks[i]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item block, CreativeTabs creativetabs, List list){
		for(int i = 0; i < MHFCReference.plankBlocks.length; i++){
			list.add(new ItemStack(block, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return texture[meta];
	}
	
	public int damageDropped(int meta){
		return meta;
	}
	
	

}
