package mhfc.heltrato.common.item.materials;

import java.util.Iterator;
import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.list.MHFCMobList;
import mhfc.heltrato.common.list.MHFCMobList.MHFCEggInfo;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFrontierSpawner extends Item {
	@SideOnly(Side.CLIENT)
	private IIcon theIcon;

	public ItemFrontierSpawner() {
		super();
		setTextureName(MHFCReference.item_mhfcspawnegg_icon);
		setUnlocalizedName(MHFCReference.item_mhfcspawnegg_name);
		setHasSubtypes(true);
		setCreativeTab(MHFCMain.mhfctabs);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		String s = ("" + StatCollector.translateToLocal(this
				.getUnlocalizedName() + ".name")).trim();
		String s1 = MHFCMobList.getStringFromID(par1ItemStack.getItemDamage());

		if (s1 != null) {
			s = s + " "
					+ StatCollector.translateToLocal("entity." + s1 + ".name");
		}

		return s;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		MHFCEggInfo entityegginfo = (MHFCEggInfo) MHFCMobList.entityEggs.get(Integer
				.valueOf(par1ItemStack.getItemDamage()));
		return entityegginfo != null ? (par2 == 0
				? entityegginfo.primaryColor
				: entityegginfo.secondaryColor) : 16777215;
	}

	/**
	 * Callback for item usage. If the item does something special on right
	 * clicking, he will have one of those. Return True if something happen and
	 * false if it don't. This is for ITEMS, not BLOCKS
	 */
	@Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
		if (par3World.isRemote) {
			return true;
		}
		Block block = par3World.getBlock(par4, par5, par6);
		par4 += Facing.offsetsXForSide[par7];
		par5 += Facing.offsetsYForSide[par7];
		par6 += Facing.offsetsZForSide[par7];
		double d0 = 0.0D;

		if (par7 == 1 && block.getRenderType() == 11) {
			d0 = 0.5D;
		}

		Entity entity = spawnCreature(par3World, par1ItemStack.getItemDamage(),
				par4 + 0.5D, par5 + d0, par6 + 0.5D);

		if (entity != null) {
			if (entity instanceof EntityLivingBase
					&& par1ItemStack.hasDisplayName()) {
				((EntityLiving) entity).setCustomNameTag(par1ItemStack
						.getDisplayName());
			}

			if (!par2EntityPlayer.capabilities.isCreativeMode) {
				--par1ItemStack.stackSize;
			}
		}

		return true;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if (par2World.isRemote) {
			return par1ItemStack;
		}
		MovingObjectPosition movingobjectposition = this
				.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer,
						true);

		if (movingobjectposition == null) {
			return par1ItemStack;
		}
		if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
			int i = movingobjectposition.blockX;
			int j = movingobjectposition.blockY;
			int k = movingobjectposition.blockZ;

			if (!par2World.canMineBlock(par3EntityPlayer, i, j, k)) {
				return par1ItemStack;
			}

			if (!par3EntityPlayer.canPlayerEdit(i, j, k,
					movingobjectposition.sideHit, par1ItemStack)) {
				return par1ItemStack;
			}

			if (par2World.getBlock(i, j, k) instanceof BlockLiquid) {
				Entity entity = spawnCreature(par2World,
						par1ItemStack.getItemDamage(), i, j, k);

				if (entity != null) {
					if (entity instanceof EntityLivingBase
							&& par1ItemStack.hasDisplayName()) {
						((EntityLiving) entity).setCustomNameTag(par1ItemStack
								.getDisplayName());
					}

					if (!par3EntityPlayer.capabilities.isCreativeMode) {
						--par1ItemStack.stackSize;
					}
				}
			}
		}

		return par1ItemStack;
	}

	/**
	 * Spawns the creature specified by the egg's type in the location specified
	 * by the last three parameters. Parameters: world, entityID, x, y, z.
	 */
	public static Entity spawnCreature(World par0World, int par1, double par2,
			double par4, double par6) {
		if (!MHFCMobList.entityEggs.containsKey(Integer.valueOf(par1))) {
			return null;
		}
		Entity entity = null;

		for (int j = 0; j < 1; ++j) {
			entity = MHFCMobList.createEntityByID(par1, par0World);

			if (entity != null && entity instanceof EntityLivingBase) {
				EntityLiving entityliving = (EntityLiving) entity;
				entity.setLocationAndAngles(par2, par4, par6,
						MathHelper.wrapAngleTo180_float(par0World.rand
								.nextFloat() * 360.0F), 0.0F);
				entityliving.rotationYawHead = entityliving.rotationYaw;
				entityliving.renderYawOffset = entityliving.rotationYaw;
				entityliving.onSpawnWithEgg((IEntityLivingData) null);
				par0World.spawnEntityInWorld(entity);
				entityliving.playLivingSound();
			}
		}

		return entity;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(
				par1, par2);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	/**
	 * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
	 */
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs,
			@SuppressWarnings("rawtypes") List par3List) {
		Iterator<MHFCEggInfo> iterator = MHFCMobList.entityEggs.values()
				.iterator();

		while (iterator.hasNext()) {
			MHFCEggInfo entityegginfo = iterator.next();
			par3List.add(new ItemStack(par1, 1, entityegginfo.spawnedID));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		this.theIcon = par1IconRegister
				.registerIcon(MHFCReference.item_mhfcspawnegg_overlay_icon);
	}
}
