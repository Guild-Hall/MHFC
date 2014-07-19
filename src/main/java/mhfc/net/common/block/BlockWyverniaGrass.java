package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCRegBlock;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
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
		setBlockName(MHFCReference.block_wyveriangrass_name);
		setBlockTextureName(MHFCReference.block_wyveriangrass_tex);
		setHardness(0.8F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? iconGrassTop : (meta == 0
				? iconGrassSide
				: blockIcon);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(getTextureName());
		iconGrassTop = icon.registerIcon(getTextureName() + "_top");
		iconGrassSide = icon.registerIcon(getTextureName() + "_side");
	}

	@Override
	public void updateTick(World par1, int par2, int par3, int par4, Random rand) {
		if (!par1.isRemote) {
			if (par1.getBlockLightValue(par2, par3 + 1, par4) < 4
					&& par1.getBlockLightOpacity(par2, par3 + 1, par4) > 2)
				;
			{
				par1.setBlock(par2, par3, par4, MHFCRegBlock.mhfcblockdirt);

			}
		} else if (par1.getBlockLightValue(par2, par3 + 1, par4) >= 9) {
			for (int l = 0; l < 4; ++l) {
				int i1 = par2 + rand.nextInt(3) - 1;
				int j1 = par3 + rand.nextInt(5) - 3;
				int k1 = par4 + rand.nextInt(3) - 1;
				// Block l1 = par1.getBlock(i1, j1 + 1, k1);

				if (par1.getBlock(i1, j1, k1) == MHFCRegBlock.mhfcblockdirt
						&& par1.getBlockLightValue(i1, j1 + 1, k1) >= 4
						&& par1.getBlockLightOpacity(i1, j1 + 1, k1) <= 2) {
					par1.setBlock(i1, j1, k1, MHFCRegBlock.mhfcblockgrass);
				}

			}
		}
	}
}
