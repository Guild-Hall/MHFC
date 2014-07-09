package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockLosGable extends Block{

	public BlockLosGable() {
		super(Material.rock);
		setBlockName("losgable");
		setHardness(1.5F);
		setResistance(1.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	
	
	public int quantityDropped(Random random){
		return 1;
	}
	
	public void registerBlockIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:losgable");
	}
	
}
