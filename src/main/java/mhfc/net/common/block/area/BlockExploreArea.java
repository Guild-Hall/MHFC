package mhfc.net.common.block.area;

import java.util.HashSet;
import java.util.Set;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.tile.TileExploreArea;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockExploreArea extends Block implements ITileEntityProvider {

	private static Set<EntityPlayerMP> teleportingPlayers = new HashSet<>();

	public BlockExploreArea() {
		super(Material.plants);
		setCreativeTab(MHFCMain.mhfctabs);
		setBlockName(MHFCReference.block_exploreArea_name);
		setBlockTextureName(MHFCReference.block_exploreArea_tex);
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
			int x,
			int y,
			int z,
			EntityPlayer player,
			int var6,
			float var7,
			float var8,
			float var9) {
		if (player.capabilities.isCreativeMode) {
			player.openGui(MHFCMain.instance(), MHFCReference.gui_changearea_id, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (world.isRemote)
			return;
		TileEntity tile = world.getTileEntity(x, y, z);
		if (!(tile instanceof TileExploreArea))
			return;
		if (!(entity instanceof EntityPlayerMP))
			return;
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
