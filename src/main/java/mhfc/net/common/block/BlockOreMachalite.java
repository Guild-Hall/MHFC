package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockOreMachalite extends Block{

	public BlockOreMachalite() {
		super(Material.rock);
		setBlockName("oremachalite");
		setBlockTextureName("mhfc:oremachalite");
		setHardness(1.2F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	
	
	public int quantityDropped(Random random){
		return 2;
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:oremachalite");
	}
	
}
