package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockWyverniaGrass extends Block {

	public BlockWyverniaGrass() {
		super(Material.GRASS);
		setUnlocalizedName(MHFCReference.block_wyveriangrass_name);
		setHardness(0.8F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	@Override
	public void updateTick(World par1, int par2, int par3, int par4, Random rand) {
		if (!par1.isRemote) {
			if (par1.getBlockLightValue(par2, par3 + 1, par4) < 4
					&& par1.getBlockLightOpacity(par2, par3 + 1, par4) > 2) {
				;
			}
			{
				par1.setBlock(par2, par3, par4, MHFCBlockRegistry.getRegistry().mhfcblockdirt);

			}
		} else if (par1.getBlockLightValue(par2, par3 + 1, par4) >= 9) {
			for (int l = 0; l < 4; ++l) {
				int i1 = par2 + rand.nextInt(3) - 1;
				int j1 = par3 + rand.nextInt(5) - 3;
				int k1 = par4 + rand.nextInt(3) - 1;
				// Block l1 = par1.getBlock(i1, j1 + 1, k1);

				if (par1.getBlock(i1, j1, k1) == MHFCBlockRegistry.getRegistry().mhfcblockdirt
						&& par1.getBlockLightValue(i1, j1 + 1, k1) >= 4
						&& par1.getBlockLightOpacity(i1, j1 + 1, k1) <= 2) {
					par1.setBlock(i1, j1, k1, MHFCBlockRegistry.getRegistry().mhfcblockgrass);
				}

			}
		}
	}
}
