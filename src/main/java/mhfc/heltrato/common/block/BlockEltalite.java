package mhfc.heltrato.common.block;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockEltalite extends Block{
	

	public BlockEltalite() {
		super(Material.rock);
		setBlockName("eltalite");
		setBlockTextureName("mhfc:eltalite");
		setHardness(1.5F);
		setResistance(1.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	
	
	public int quantityDropped(Random random){
		return 1;
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:eltalite");
	}
}
