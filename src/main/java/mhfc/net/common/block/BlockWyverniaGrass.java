package mhfc.net.common.block;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCBlockRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWyverniaGrass extends Block {

	public BlockWyverniaGrass() {
		super(Material.GRASS);
		setUnlocalizedName(ResourceInterface.block_wyveriangrass_name);
		setHardness(0.8F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}

	// This method is disabled atm for quest area purposes..
	/*	@Override
		public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
			if (world.isRemote) {
				return;
			}
			int upperLightValue = world.getLight(pos.up());
			if (!isUpgradeEligable(world, pos)) {
				// Degrade
				world.setBlockState(pos, MHFCBlockRegistry.getRegistry().mhfcblockdirt.getDefaultState());
			} else if (upperLightValue >= 9) {
				for (int l = 0; l < 4; ++l) {
					tryUpgradeRandomNearDirt(world, pos, rand);
				}
			}
		}*/

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@SuppressWarnings("static-method")
	private boolean isUpgradeEligable(World world, BlockPos position) {
		int upperLightValue = world.getLight(position.up());
		int upperLightOpacity = world.getBlockLightOpacity(position.up());
		return upperLightValue >= 4 && upperLightOpacity <= 2;
	}

	private void tryUpgradeRandomNearDirt(World world, BlockPos basePosition, Random rand) {
		BlockPos position = WorldHelper.randomProximity(rand, basePosition, 3, 1);
		IBlockState state = world.getBlockState(position);
		if (state.getBlock() != MHFCBlockRegistry.getRegistry().mhfcblockdirt) {
			return;
		}
		if (!isUpgradeEligable(world, position)) {
			return;
		}

		world.setBlockState(position, this.getDefaultState());
	}

}
