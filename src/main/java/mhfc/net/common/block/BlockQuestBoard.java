package mhfc.net.common.block;

import java.util.List;
import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCContainerRegistry;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.tile.TileQuestBoard;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BlockQuestBoard extends BlockContainer {
	public static final PropertyBool UP_MASK = PropertyBool.create("upmask");
	public static final PropertyBool OFFSET_MASK = PropertyBool.create("offmask");
	public static final PropertyBool ROT_LOW_MASK = PropertyBool.create("rotlmask");
	public static final PropertyBool ROT_HIGH_MASK = PropertyBool.create("rothmask");

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
		return new BlockStateContainer(this, UP_MASK, OFFSET_MASK, ROT_LOW_MASK, ROT_HIGH_MASK);
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
			ItemStack heldItem,
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
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileQuestBoard();
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		switch (rand.nextInt(3)) {
		case 0:
			return Items.APPLE;
		case 1:
			return Items.BED;
		case 2:
			return Items.BEEF;
		default:
			return null;
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		if (state.getValue(UP_MASK)) {
			meta &= 0x8;
		}
		if (state.getValue(OFFSET_MASK)) {
			meta &= 0x4;
		}
		if (state.getValue(ROT_HIGH_MASK)) {
			meta &= 0x2;
		}
		if (state.getValue(ROT_LOW_MASK)) {
			meta &= 0x1;
		}
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		IBlockState state = getDefaultState();
		if ((meta & 0x8) != 0) {
			state.withProperty(UP_MASK, true);
		}
		if ((meta & 0x4) != 0) {
			state.withProperty(OFFSET_MASK, true);
		}
		if ((meta & 0x2) != 0) {
			state.withProperty(ROT_HIGH_MASK, true);
		}
		if ((meta & 0x1) != 0) {
			state.withProperty(ROT_LOW_MASK, true);
		}
		return state;
	}

	@Override
	public IBlockState onBlockPlaced(
			World world,
			BlockPos pos,
			EnumFacing facing,
			float hitX,
			float hitY,
			float hitZ,
			int meta,
			EntityLivingBase placer) {
		if (world.isRemote) {
			super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		}
		EnumFacing side = getPlacedSide(world, pos, placer);
		IBlockState state = getDefaultState();
		if (side == EnumFacing.NORTH) {
			return state.withProperty(OFFSET_MASK, true).withProperty(ROT_HIGH_MASK, true);
		}
		if (side == EnumFacing.SOUTH) {
			return state.withProperty(OFFSET_MASK, true);
		}
		if (side == EnumFacing.WEST) {
			return state.withProperty(OFFSET_MASK, true).withProperty(ROT_LOW_MASK, true);
		}
		if (side == EnumFacing.EAST) {
			return state.withProperty(OFFSET_MASK, true).withProperty(ROT_HIGH_MASK, true)
					.withProperty(ROT_LOW_MASK, true);
		}

		if (side == EnumFacing.UP) {
			state = state.withProperty(UP_MASK, true);
		}
		int angleAsRotation = (int) (calculateAngleHit(hitX, hitZ) / 90);
		if ((angleAsRotation & 0x2) != 1) {
			state = state.withProperty(ROT_HIGH_MASK, true);
		}
		if ((angleAsRotation & 0x1) != 0) {
			state = state.withProperty(ROT_LOW_MASK, true);
		}
		return state;
	}

	private float calculateAngleHit(float hitX, float hitZ) {
		float cosAng = (float) (hitZ / (Math.sqrt(hitX * hitX + hitZ * hitZ)));
		float angle = (float) (Math.acos(cosAng) / Math.PI * 180);
		if (hitX > 0) {
			angle = 360 - angle;
		}
		angle += 45;
		angle %= 360;
		return angle;
	}

	private EnumFacing getPlacedSide(World world, BlockPos pos, EntityLivingBase placer) {
		Vec3d vecPos = new Vec3d(placer.posX, placer.posY + placer.getEyeHeight(), placer.posZ);
		float f1 = MathHelper.cos(-placer.rotationYaw * 0.017453292F - (float) Math.PI);
		float f2 = MathHelper.sin(-placer.rotationYaw * 0.017453292F - (float) Math.PI);
		float f3 = -MathHelper.cos(-placer.rotationPitch * 0.017453292F);
		float f4 = MathHelper.sin(-placer.rotationPitch * 0.017453292F);
		Vec3d look = new Vec3d(f2 * f3 * 160, f4 * 160, f1 * f3 * 160);
		Vec3d blockVec = new Vec3d(pos);
		Vec3d lookAim = look.addVector(vecPos.xCoord, vecPos.yCoord, vecPos.zCoord);
		RayTraceResult movPos = world.rayTraceBlocks(vecPos, lookAim, false, false, true);
		EnumFacing side = getOppositeSide(blockVec.subtract(movPos.hitVec), look);
		return side;
	}

	/**
	 * This method returns the side of the next block which would be hit if the look vector was to be traced through the
	 * block, starting at the hit vector (which is relative to the block).
	 *
	 */
	private EnumFacing getOppositeSide(Vec3d hitVector, Vec3d lookVector) {
		double dX = Math.signum(lookVector.xCoord);
		double dY = lookVector.yCoord / lookVector.xCoord * dX;
		double dZ = lookVector.zCoord / lookVector.xCoord * dX;
		double targetX = lookVector.xCoord > 0 ? 1.0 : 0.0;
		double targetY = lookVector.yCoord > 0 ? 1.0 : 0.0;
		double targetZ = lookVector.zCoord > 0 ? 1.0 : 0.0;
		double tX = (targetX - hitVector.xCoord) / dX;
		double tY = (targetY - hitVector.yCoord) / dY;
		double tZ = (targetZ - hitVector.zCoord) / dZ;
		double t = tX;
		if (!(tX > 0 && tY >= 0 && tZ >= 0)) {
			MHFCMain.logger().debug("Noooo");
		}

		EnumFacing side = lookVector.xCoord > 0 ? EnumFacing.WEST : EnumFacing.EAST;
		if (!Double.isNaN(tY) && tY < t) {
			side = lookVector.yCoord > 0 ? EnumFacing.DOWN : EnumFacing.UP;
			t = tY;
		}
		if (!Double.isNaN(tZ) && tZ < t) {
			side = lookVector.zCoord > 0 ? EnumFacing.NORTH : EnumFacing.SOUTH;
		}
		return side;
	}

	public void addBlockBoundsByState(IBlockState blockState, List<AxisAlignedBB> bounds) {
		int meta = blockState.getBlock().getMetaFromState(blockState);

		boolean isOffsetSet = (meta & offsetMask) == offsetMask;
		boolean isUpSet = (meta & upMask) == upMask;
		boolean boxUpFlag = isUpSet | isOffsetSet;

		float maxY = boxUpFlag ? 1.0f : 0.7f;
		float minY = boxUpFlag ? 0.3f : 0.0f;
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
		bounds.add(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
	}

	@Override
	public void addCollisionBoxToList(
			IBlockState state,
			World worldIn,
			BlockPos pos,
			AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes,
			Entity entityIn) {
		this.addBlockBoundsByState(state, collidingBoxes);
	}
}
