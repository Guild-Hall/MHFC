package mhfc.net.common.block;

import java.util.List;
import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.tile.TileQuestBoard;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockQuestBoard extends BlockContainer {

	public static int upMask = 0x8;
	public static int offsetMask = 0x4;
	public static int rotationMask = 0x3;

	public BlockQuestBoard() {
		super(Material.wood);
		setBlockName(MHFCReference.block_questBoard_name);
		setHardness(3.0f);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World var1, int var2, int var3, int var4,
			EntityPlayer player, int var6, float var7, float var8, float var9) {
		if (!player.isSneaking()) {
			player.openGui(MHFCMain.instance(), MHFCReference.gui_questboard_id,
					var1, var2, var3, var4);
			return true;
		}
		return false;
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		// TODO this looks actually pretty awesome for the questing board, but
		// maybe get a dedicated one
		blockIcon = par1IconRegister
				.registerIcon(MHFCReference.block_hunterbench_icon);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileQuestBoard();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random random, int p_149650_3_) {
		switch (random.nextInt(3)) {
			case 0 :
				return Items.apple;
			case 1 :
				return Items.bed;
			case 2 :
				return Items.beef;
			default :
				return null;
		}
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side,
			float hitX, float hitY, float hitZ, int meta) {
		if (side > 1) {
			if (side == 2) // face pointing north hit
				return 0x6;
			if (side == 3) // south
				return 0x4;
			if (side == 4) // west
				return 0x5;
			if (side == 5) // east
				return 0x7;
		} else {
			meta = 0;
			if (side == 0)
				meta += upMask;
			return meta;
		}
		return side;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase entity, ItemStack stack) {

		if (!world.isRemote) {
			Vec3 vecPos = Vec3.createVectorHelper(entity.posX, entity.posY
					+ entity.getEyeHeight(), entity.posZ);
			float f1 = MathHelper.cos(-entity.rotationYaw * 0.017453292F
					- (float) Math.PI);
			float f2 = MathHelper.sin(-entity.rotationYaw * 0.017453292F
					- (float) Math.PI);
			float f3 = -MathHelper.cos(-entity.rotationPitch * 0.017453292F);
			float f4 = MathHelper.sin(-entity.rotationPitch * 0.017453292F);
			Vec3 look = Vec3.createVectorHelper(f2 * f3 * 160, f4 * 160, f1
					* f3 * 160);
			Vec3 blockVec = Vec3.createVectorHelper(x, y, z);
			Vec3 lookAim = look.addVector(vecPos.xCoord, vecPos.yCoord,
					vecPos.zCoord);
			MovingObjectPosition movPos = world.func_147447_a(vecPos, lookAim,
					false, false, true);
			// booleans are: entity must hold boat, require collision box to
			// collide, return non-blocks
			int side = getOppositeSide(blockVec.subtract(movPos.hitVec), look);
			int meta = world.getBlockMetadata(x, y, z);
			double hitX = look.xCoord;
			double hitZ = look.zCoord;
			if (side > 1) {
				if (side == 2) // face pointing north hit
					meta = 0x6;
				if (side == 3) // south
					meta = 0x4;
				if (side == 4) // west
					meta = 0x5;
				if (side == 5) // east
					meta = 0x7;
			} else {
				float cosAng = (float) (hitZ / (Math.sqrt(hitX * hitX + hitZ
						* hitZ)));
				float angle = (float) (Math.acos(cosAng) / Math.PI * 180);
				if (hitX > 0)
					angle = 360 - angle;
				angle += 45;
				angle %= 360;
				int metaData = (int) (angle / 90);
				metaData &= rotationMask;
				if (side == 0)
					metaData += upMask;
				meta = metaData;
			}
			world.setBlockMetadataWithNotify(x, y, z, meta, 3);
		}
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}

	/**
	 * This method returns the side of the next block which would be hit if the
	 * look vector was to be traced through the block, starting at the hit
	 * vector (which is relative to the block).
	 *
	 */
	private int getOppositeSide(Vec3 hitVector, Vec3 lookVector) {
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
		if (!(tX > 0 && tY >= 0 && tZ >= 0))
			MHFCMain.logger().debug("Noooo");
		int side = lookVector.xCoord > 0 ? 4 : 5;
		if (!Double.isNaN(tY) && tY < t) {
			side = lookVector.yCoord > 0 ? 0 : 1;
			t = tY;
		}
		if (!Double.isNaN(tZ) && tZ < t) {
			side = lookVector.zCoord > 0 ? 2 : 3;
		}
		return side;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x,
			int y, int z) {
		int meta = blockAccess.getBlockMetadata(x, y, z);
		boolean boxUpFlag = ((meta & upMask) == upMask)
				| ((meta & offsetMask) == offsetMask);
		float maxY = !boxUpFlag ? 0.70f : 1.0f;
		float minY = !boxUpFlag ? 0 : 0.3f;
		float minX, maxX, minZ, maxZ;
		if ((meta & 0x1) == 0) {
			minX = 0;
			maxX = 1;
		} else {
			if ((meta & offsetMask) == offsetMask) {
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
		}
		if ((meta & 0x1) == 1) {
			minZ = 0;
			maxZ = 1;
		} else {
			if ((meta & offsetMask) == offsetMask) {
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
		}
		this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_,
			int p_149743_3_, int p_149743_4_, AxisAlignedBB p_149743_5_,
			@SuppressWarnings("rawtypes") List p_149743_6_, Entity p_149743_7_) {
		// CLEANUP remove this as the state does not change during lifetime of
		// this block
		this.setBlockBoundsBasedOnState(p_149743_1_, p_149743_2_, p_149743_3_,
				p_149743_4_);
		super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_,
				p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
	}

}
