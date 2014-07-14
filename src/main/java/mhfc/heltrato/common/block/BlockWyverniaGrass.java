package mhfc.heltrato.common.block;

import java.util.Random;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.core.registry.MHFCRegBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWyverniaGrass extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon iconGrassTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconGrassSide;
	
	public BlockWyverniaGrass() {
		super(Material.grass);
		setBlockName("wyverniagrass");
		setBlockTextureName("mhfc:wyverniadirt");
		setHardness(0.8F);
		setCreativeTab(MHFCMain.mhfctabs);
	}
	
	public IIcon getIcon(int par1, int par2){
		return par1 == 1? iconGrassTop : (par2 == 0 ? iconGrassSide : blockIcon);
	}
	
	public int quantityDropped(Random random){
		return 1;
	}
	
	public IIcon getBlockTexture(IBlockAccess par1, int par2, int par3, int par4, int par5){
		if(par5 == 1){
			return iconGrassTop;
		}else if(par5 == 0){
			return iconGrassSide;
		}else{
			return blockIcon;
		}
		
	}
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(getTextureName());
		iconGrassTop = icon.registerIcon(getTextureName() + "_top");
		iconGrassSide = icon.registerIcon(getTextureName() + "_side");
	}
	
	public void updateTick(World par1, int par2, int par3, int par4, Random rand){
		if(!par1.isRemote){
			if(par1.getBlockLightValue(par2, par3 + 1, par4) < 4 && par1.getBlockLightOpacity(par2, par3 + 1, par4) > 2);
			{
				par1.setBlock(par2, par3, par4, MHFCRegBlock.mhfcblockdirt);
				
			}}else if (par1.getBlockLightValue(par2, par3 + 1, par4) >= 9)
			{
				for (int l = 0; l < 4; ++l)
				{
				int i1 = par2 + rand.nextInt(3) - 1;
				int j1 = par3 + rand.nextInt(5) - 3;
				int k1 = par4 + rand.nextInt(3) - 1;
				Block l1 = par1.getBlock(i1, j1 + 1, k1);

				if (par1.getBlock(i1, j1, k1) == MHFCRegBlock.mhfcblockdirt && par1.getBlockLightValue(i1, j1 + 1, k1) >= 4 && par1.getBlockLightOpacity(i1, j1 + 1, k1) <= 2)
				{
					par1.setBlock(i1, j1, k1, MHFCRegBlock.mhfcblockgrass);
					}
				
					}
			}
	}
	}
