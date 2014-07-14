package mhfc.heltrato.common.block;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockOreEltalite extends Block{

	public BlockOreEltalite() {
		super(Material.rock);
		setBlockName("oreeltalite");
		setBlockTextureName("mhfc:oreeltalite");
		setHardness(1.7F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	
	
	public int quantityDropped(Random random){
		return 1;
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:oreeltalite");
	}
	
}
