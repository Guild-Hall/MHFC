package mhfc.net.common.block.util;

import java.util.List;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileQuestBoard;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockQuestBoard extends BlockContainer {
	public static enum EnumPlacing implements IStringSerializable {
		// The block faces forward, but is not offset
		FORWARD("centered"),
		// The block face forward, but is offset
		FORWARD_OFFSET("front"),
		// The block faces upwards
		UPWARDS("up"),;
		private static final EnumPlacing[] VALUES = values();

		private final String name;

		private EnumPlacing(String name) {
			this.name = name;
		}

		public int getIndex() {
			return this.ordinal();
		}

		@Override
		public String getName() {
			return this.name;
		}

		public static EnumPlacing getPlacing(int i) {
			return VALUES[i];
		}
	}

	public static final PropertyEnum<EnumPlacing> PLACING = PropertyEnum.create("placing", EnumPlacing.class);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public static int upMask = 0x8;
	public static int offsetMask = 0x4;
	public static int rotationMask = 0x3;

	public BlockQuestBoard() {
		super(Material.WOOD);
		setHardness(3.0f);
		setUnlocalizedName(ResourceInterface.block_questBoard_name);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PLACING, FACING);
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
	protected boolean canSilkHarvest() {
		return false;
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
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta &= state.getValue(PLACING).getIndex() << 2;
		meta &= state.getValue(FACING).getHorizontalIndex();
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		state.withProperty(PLACING, EnumPlacing.getPlacing((meta >> 2) & 0x3));
		state.withProperty(FACING, EnumFacing.getHorizontal(meta & 0x3));
		return state;
	}

	private static boolean isHorizontal(EnumFacing facing) {
		return facing.getHorizontalIndex() >= 0;
	}

	@Override
	public IBlockState getStateForPlacement(
			World world,
			BlockPos pos,
			EnumFacing facing,
			float hitX,
			float hitY,
			float hitZ,
			int meta,
			EntityLivingBase placer,
			EnumHand hand) {
		if (world.isRemote) {
			return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand);
		}
		IBlockState state = getDefaultState();
		if (isHorizontal(facing)) {
			return state.withProperty(PLACING, EnumPlacing.FORWARD_OFFSET).withProperty(FACING, facing);
		}
		if (facing == EnumFacing.UP) {
			return state.withProperty(PLACING, EnumPlacing.UPWARDS)
					.withProperty(FACING, placer.getHorizontalFacing().getOpposite());
		}
		EnumFacing rotatedFacing = calculateHorizontalFacing(hitX, hitZ);
		state.withProperty(FACING, rotatedFacing);
		return state;
	}

	private static EnumFacing calculateHorizontalFacing(float hitX, float hitZ) {
		float cosAng = (float) (hitZ / (Math.sqrt(hitX * hitX + hitZ * hitZ)));
		float angle = (float) (Math.acos(cosAng) / Math.PI * 180);
		if (hitX > 0) {
			angle = 360 - angle;
		}
		angle += 45;
		int idx = (int) angle;
		idx = (idx % 360) / 90;
		return EnumFacing.getHorizontal(idx);
	}

	@Override
	public void addCollisionBoxToList(
			IBlockState blockState,
			World worldIn,
			BlockPos pos,
			AxisAlignedBB entityBox,
			List<AxisAlignedBB> bounds,
			Entity entityIn) {
		int meta = blockState.getBlock().getMetaFromState(blockState);

		boolean isOffsetSet = blockState.getValue(PLACING) == EnumPlacing.FORWARD_OFFSET;
		boolean isUpSet = blockState.getValue(PLACING) == EnumPlacing.UPWARDS;
		boolean boxUpFlag = isUpSet | isOffsetSet;

		float minY = boxUpFlag ? 0.3f : 0.0f;
		float maxY = boxUpFlag ? 1.0f : 0.7f;
		float minX, maxX, minZ, maxZ;
		if ((meta & 0x1) == 0) {
			minX = 0;
			maxX = 1;
		} else if (isOffsetSet) {
			if ((meta & rotationMask) == 1) {
				minX = 0.75f;
				maxX = 1.0f;
			} else {
				minX = 0;
				maxX = 0.25f;
			}
		} else {
			minX = 0.375f;
			maxX = 0.625f;
		}
		if ((meta & 0x1) == 1) {
			minZ = 0;
			maxZ = 1;
		} else if (isOffsetSet) {
			if ((meta & rotationMask) == 2) {
				minZ = 0.75f;
				maxZ = 1.0f;
			} else {
				minZ = 0;
				maxZ = 0.25f;
			}
		} else {
			minZ = 0.375f;
			maxZ = 0.625f;
		}

		AxisAlignedBB boundingBox = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
		addCollisionBoxToList(pos, entityBox, bounds, boundingBox);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withProperty(FACING, mirrorIn.mirror(state.getValue(FACING)));
	}
}
