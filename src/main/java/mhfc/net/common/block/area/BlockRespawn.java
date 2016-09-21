package mhfc.net.common.block.area;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.util.Libraries;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRespawn extends Block {

	public BlockRespawn() {
		super(Material.WEB);
		setCreativeTab(MHFCMain.mhfctabs);
		setUnlocalizedName(Libraries.block_respawn_name);
		setBlockUnbreakable();
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
		MHFCExplorationRegistry.getExplorationManagerFor(player).respawn(player);
	}

}
