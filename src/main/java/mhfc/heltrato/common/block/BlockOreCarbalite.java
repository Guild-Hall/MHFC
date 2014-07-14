package mhfc.heltrato.common.block;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockOreCarbalite extends Block{

	public BlockOreCarbalite() {
		super(Material.rock);
		setBlockName("orecarbalite");
		setBlockTextureName("mhfc:orecarbalite");
		setHardness(1.3F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	
	
	public int quantityDropped(Random random){
		return 1;
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:orecarbalite");
	}
	
}
