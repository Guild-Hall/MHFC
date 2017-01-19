package mhfc.net.common.block.area;

import java.util.HashSet;
import java.util.Set;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileExploreArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockExploreArea extends BlockBarrier implements ITileEntityProvider {

	private static Set<EntityPlayerMP> teleportingPlayers = new HashSet<>();

	public BlockExploreArea() {
		setUnlocalizedName(ResourceInterface.block_exploreArea_name);
		setBlockUnbreakable();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return NULL_AABB;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileExploreArea();
	}

	@Override
	public boolean onBlockActivated(
			World world,
			BlockPos pos,
			IBlockState state,
			EntityPlayer player,
			EnumHand hand,
			EnumFacing side,
			float hitX,
			float hitY,
			float hitZ) {
		if (!player.capabilities.isCreativeMode) {
			return false;
		}
		player.openGui(
				MHFCMain.instance(),
				MHFCContainerRegistry.gui_changearea_id,
				world,
				pos.getX(),
				pos.getY(),
				pos.getZ());
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (world.isRemote) {
			return;
		}
		TileEntity tile = world.getTileEntity(pos);
		if (!(tile instanceof TileExploreArea)) {
			return;
		}
		if (!(entity instanceof EntityPlayerMP)) {
			return;
		}
		TileExploreArea tileChangeArea = (TileExploreArea) tile;
		EntityPlayerMP player = (EntityPlayerMP) entity;
		if (teleportingPlayers.contains(player)) {
			player.motionY = 0;
			return;
		}
		IAreaType targetArea = tileChangeArea.getTargetArea();
		// FIXME: this ignores the quest flair completely
		MHFCMain.logger().debug(
				"Teleporting " + player.getName() + " to area " + targetArea.getUnlocalizedName()
						+ " flair ignored right now");
		teleportingPlayers.add(player);
		MHFCExplorationRegistry.transferPlayer(player, targetArea).whenComplete((t, e) -> {
			teleportingPlayers.remove(player);
		});
	}

}
