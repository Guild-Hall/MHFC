package mhfc.net.common.block.container;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileHunterBench;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHunterBench extends BlockContainer {

	public BlockHunterBench() {
		super(Material.ROCK);
		setUnlocalizedName(ResourceInterface.block_hunterbench_name);
		setHardness(1.2F);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var1) {
		return new TileHunterBench();
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean onBlockActivated(
			World worldIn,
			BlockPos pos,
			IBlockState state,
			EntityPlayer player,
			EnumHand hand,
			EnumFacing side,
			float hitX,
			float hitY,
			float hitZ) {

		if (!player.isSneaking()) {
			player.openGui(
					MHFCMain.instance(),
					MHFCContainerRegistry.gui_hunterbench_id,
					worldIn,
					pos.getX(),
					pos.getY(),
					pos.getZ());
			return true;
		}
		return false;
	}

}
