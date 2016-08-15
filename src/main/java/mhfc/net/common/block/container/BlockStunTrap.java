package mhfc.net.common.block.container;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.tile.TileStunTrap;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockStunTrap extends BlockContainer {

	// private ItemBlockWyverniaDefault block;
	// private TileStunTrap trap;
	public BlockStunTrap() {
		super(Material.rock);
		setBlockBounds(1F / 16F * 5F, 0, 1F / 16F * 5F, 1F - 1F / 16F * 5F, 1F - 1F / 16F * 12F, 1F - 1F / 16F * 5F);
		setBlockName(MHFCReference.block_stuntrap_name);
		setHardness(0.8F);
		disableStats();
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		float f1 = x + 0.5F;
		float f2 = y + 1.1F;
		float f3 = z + 0.5F;
		float f4 = random.nextFloat() * 0.6F - 0.3F;
		float f5 = random.nextFloat() * -0.6F - -0.3F;
		world.spawnParticle("largesmoke", f1 + f4, f2, (double) f3 + f5, 0D, 0D, 0D);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var1) {
		return new TileStunTrap();
	}

	@Override
	public boolean onBlockActivated(World par1World,int par2,int par3,
			int par4,
			EntityPlayer par5EntityPlayer,
			int par6,
			float par7,
			float par8,
			float par9) {
		int metadata = par1World.getBlockMetadata(par2, par3, par4);
		if (metadata == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0, 3);
			return true;
		}
		return false;
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		boolean flag = false;

		if (!World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4)
				&& !BlockFence.func_149825_a(par1World.getBlock(par2, par3 - 1, par4))) {
			flag = true;
		}

		if (flag) {
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(MHFCReference.block_stuntrap_icon);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
		if ((entity instanceof EntityPlayer) || !(entity instanceof EntityLiving)) {
			return;
		}

		world.setBlockToAir(i, j, k);
		EntityLivingBase entityliving = (EntityLivingBase) entity;
		world.setBlockMetadataWithNotify(i, j, k, 1, 3);
		// TileStunTrap tileentitystuntrap = (TileStunTrap)
		// world.getTileEntity(i,
		// j, k);
		entityliving.addPotionEffect(new PotionEffect(MHFCPotionRegistry.getRegistry().stun.id, 500, 10));
		return;

	}

}
