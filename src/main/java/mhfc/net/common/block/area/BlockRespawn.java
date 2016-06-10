package mhfc.net.common.block.area;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockRespawn extends Block {

	public BlockRespawn() {
		super(Material.web);
		setCreativeTab(MHFCMain.mhfctabs);
		setBlockName(MHFCReference.block_respawn_name);
		setBlockTextureName(MHFCReference.block_respawn_tex);
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
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (world.isRemote)
			return;
		if (!(entity instanceof EntityPlayerMP)) {
			return;
		}
		EntityPlayerMP player = (EntityPlayerMP) entity;
		MHFCExplorationRegistry.getExplorationManagerFor(player).respawn(player);
	}

}
