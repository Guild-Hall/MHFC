package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

public class BlockWyverniaSand extends BlockFalling{

	
	public BlockWyverniaSand() {
		super();
		setBlockName("wyverniasand");
		setBlockTextureName("mhfc:wyverniasand");
		setHardness(1.3F);
		setResistance(2.0F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	
	
	public int quantityDropped(Random random){
		return 1;
	}
	
	public void registerIcons(IIconRegister par1IconRegister){
		blockIcon = par1IconRegister.registerIcon("mhfc:wyverniasand");
	}
}
