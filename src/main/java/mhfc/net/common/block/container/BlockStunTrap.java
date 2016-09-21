package mhfc.net.common.block.container;

import java.util.Random;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.tile.TileStunTrap;
import mhfc.net.common.util.Libraries;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

// FIXME: fix the stun trap, currently logic is in the block that should be in the tile and other way around
public class BlockStunTrap extends BlockContainer {
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(
			1F / 16F * 5F,
			0,
			1F / 16F * 5F,
			1F - 1F / 16F * 5F,
			1F - 1F / 16F * 12F,
			1F - 1F / 16F * 5F);

	public BlockStunTrap() {
		super(Material.ROCK);
		setUnlocalizedName(Libraries.block_stuntrap_name);
		setHardness(0.8F);
		disableStats();
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(ACTIVE) ? 0 : 1;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(ACTIVE, meta != 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var1) {
		return new TileStunTrap();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, ACTIVE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BOUNDS;
	}

	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		float f1 = pos.getX() + 0.5F;
		float f2 = pos.getY() + 1.1F;
		float f3 = pos.getZ() + 0.5F;
		float f4 = random.nextFloat() * 0.6F - 0.3F;
		float f5 = random.nextFloat() * -0.6F - -0.3F;
		world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, f1 + f4, f2, (double) f3 + f5, 0D, 0D, 0D);
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
		if (state.getValue(ACTIVE)) {
			world.setBlockState(pos, state.withProperty(ACTIVE, false), 3);
			return true;
		}
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (!(entity instanceof EntityLivingBase) || (entity instanceof EntityPlayer)) {
			return;
		}
		EntityLivingBase entityliving = (EntityLivingBase) entity;
		entityliving.addPotionEffect(new PotionEffect(MHFCPotionRegistry.getRegistry().stun, 500, 10));

		world.setBlockState(pos, state.withProperty(ACTIVE, true));
		return;
	}

}
