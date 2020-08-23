package mhfc.net.common.block.quest;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileQuestBoard;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockQuestBoard extends BlockContainer {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static int upMask = 0x8;
	public static int offsetMask = 0x4;
	public static int rotationMask = 0x3;

	private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.0D, 0.78125D, 0.875D, 1.0D, 0.98125, 1.0D);

	public BlockQuestBoard() {
		super(Material.WOOD);
		setHardness(3.0f);
		setUnlocalizedName(ResourceInterface.block_questBoard_name);
		setCreativeTab(MHFCMain.mhfctabs);
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
			World world,
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
					MHFCContainerRegistry.gui_questboard_id,
					world,
					pos.getX(),
					pos.getY(),
					pos.getZ());
			return true;
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileQuestBoard();
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDS;
	}

}
