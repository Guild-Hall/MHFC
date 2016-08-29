package mhfc.net.common.block.environment;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockWyverniaDecor extends BlockBush {
	public BlockWyverniaDecor(Material material) {
		super(material);
	}

	public boolean isValidPosition(World world, BlockPos position, int metadata) {
		if (world.getBlockState(position.down()).getBlock() == Blocks.AIR) {
			return false;
		}

		return canPlaceBlockAt(world, position);
	}

	@Override
	public void updateTick(World world, BlockPos position, IBlockState state, Random random) {
		Block block = state.getBlock();

		this.dropIfCantStay(world, position, block.getItem);
	}

	public void dropIfCantStay(World world, int x, int y, int z, ItemStack stack) {
		if (!this.canReplace(world, x, y, z, 0, stack)) {
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		dropIfCantStay(world, x, y, z, new ItemStack(world.getBlock(x, y, z), 1, world.getBlockMetadata(x, y, z)));
	}
}
