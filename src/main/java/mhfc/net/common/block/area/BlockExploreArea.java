package mhfc.net.common.block.area;

import java.util.HashSet;
import java.util.Set;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.tile.TileExploreArea;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockExploreArea extends Block implements ITileEntityProvider {

	private static Set<EntityPlayerMP> teleportingPlayers = new HashSet<>();

	public BlockExploreArea() {
		super(Material.PLANTS);
		setCreativeTab(MHFCMain.mhfctabs);
		setUnlocalizedName(Libraries.block_exploreArea_name);
		setBlockUnbreakable();
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(
			World p_149668_1_,
			int p_149668_2_,
			int p_149668_3_,
			int p_149668_4_) {
		return null;
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
			ItemStack heldItem,
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
		teleportingPlayers.add(player);
		MHFCExplorationRegistry.transferPlayer(player, targetArea, (t) -> {
			teleportingPlayers.remove(player);
		});
	}

}
