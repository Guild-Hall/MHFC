package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockCarbalite extends Block{

	public BlockCarbalite() {
		super(Material.rock);
		setBlockName("carbalite");
		setBlockTextureName("mhfc:carbalite");
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	
	
	public int quantityDropped(Random random){
		return 1;
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:carbalite");
	}
	
}
