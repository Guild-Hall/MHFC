package mhfc.net.common.block.util;

import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRespawn extends BlockBarrier {

	public BlockRespawn() {
		setUnlocalizedName(ResourceInterface.block_respawn_name);
		setBlockUnbreakable();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (world.isRemote) {
			return;
		}
		if (!(entity instanceof EntityPlayerMP)) {
			return;
		}
		EntityPlayerMP player = (EntityPlayerMP) entity;
		MHFCExplorationRegistry.getExplorationManagerFor(player).respawn();
	}

}
