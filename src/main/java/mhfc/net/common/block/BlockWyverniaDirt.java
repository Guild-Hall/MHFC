package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockWyverniaDirt extends Block{

	public BlockWyverniaDirt() {
		super(Material.ground);
		setBlockName("wyverniadirt");
		setBlockTextureName("mhfc:wyverniadirt");
		setHardness(0.8F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	
	
	public int quantityDropped(Random random){
		return 1;
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:wyverniadirt");
	}
	
}
