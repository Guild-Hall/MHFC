package mhfc.net.common.block.environment;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockWyverniaDecor extends BlockBush {
	public BlockWyverniaDecor(Material material) {
		super(material);
	}

	@Override
	public void updateTick(World world, BlockPos position, IBlockState state, Random random) {
		Block block = state.getBlock();

		this.dropIfCantStay(world, position, block.getItem(world, position, state));
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		dropIfCantStay(world, pos, new ItemStack(this, 1, block.getMetaFromState(state)));
	}

	private void dropIfCantStay(World world, BlockPos pos, ItemStack stack) {
		if (!this.canReplace(world, pos, EnumFacing.UP, stack)) {
			this.dropBlockAsItem(world, pos, world.getBlockState(pos), 0);
			world.setBlockToAir(pos);
		}
	}
}
