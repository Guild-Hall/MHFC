package mhfc.net.common.item.materials;

import java.util.Iterator;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.MHFCMobList;
import mhfc.net.common.core.MHFCMobList.MHFCEggInfo;
import mhfc.net.common.util.lib.MHFCReference;
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

public class ItemSpawner extends Item {
	@SideOnly(Side.CLIENT)
	private IIcon theIcon;

	public ItemSpawner() {
		super();
		setTextureName(MHFCReference.base_tool_horn);
		setUnlocalizedName(MHFCReference.item_mhfcspawnegg_name);
		setCreativeTab(MHFCMain.mhfctabs);
		setHasSubtypes(true);
		setMaxStackSize(1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		String s = (StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String s1 = MHFCMobList.getStringFromID(par1ItemStack.getItemDamage());

		if (s1 != null) {
			s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
		}

		return s;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		MHFCEggInfo entityegginfo = MHFCMobList.registeredEggs().get(Integer.valueOf(par1ItemStack.getItemDamage()));
		return entityegginfo != null
				? (par2 == 0 ? entityegginfo.primaryColor : entityegginfo.secondaryColor)
				: 16777215;
	}

	@Override
	public boolean onItemUse(
			ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			World par3World,
			int par4,
			int par5,
			int par6,
			int par7,
			float par8,
			float par9,
			float par10) {
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

		Entity entity = spawnCreature(par3World, par1ItemStack.getItemDamage(), par4 + 0.5D, par5 + d0, par6 + 0.5D);

		if (entity != null) {
			if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName()) {
				((EntityLiving) entity).setCustomNameTag(par1ItemStack.getDisplayName());
			}

			if (!par2EntityPlayer.capabilities.isCreativeMode) {
				--par1ItemStack.stackSize;
			}
		}

		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par2World.isRemote) {
			return par1ItemStack;
		}
		MovingObjectPosition movingobjectposition = this
				.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, true);

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

			if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, par1ItemStack)) {
				return par1ItemStack;
			}

			if (par2World.getBlock(i, j, k) instanceof BlockLiquid) {
				Entity entity = spawnCreature(par2World, par1ItemStack.getItemDamage(), i, j, k);

				if (entity != null) {
					if (entity instanceof EntityLivingBase && par1ItemStack.hasDisplayName()) {
						((EntityLiving) entity).setCustomNameTag(par1ItemStack.getDisplayName());
					}

					if (!par3EntityPlayer.capabilities.isCreativeMode) {
						--par1ItemStack.stackSize;
					}
				}
			}
		}

		return par1ItemStack;
	}

	public static Entity spawnCreature(World par0World, int par1, double par2, double par4, double par6) {
		if (!MHFCMobList.registeredEggs().containsKey(Integer.valueOf(par1))) {
			return null;
		}
		Entity entity = null;

		for (int j = 0; j < 1; ++j) {
			entity = MHFCMobList.createEntityByID(par1, par0World);

			if (entity != null && entity instanceof EntityLivingBase) {
				EntityLiving entityliving = (EntityLiving) entity;
				entity.setLocationAndAngles(
						par2,
						par4,
						par6,
						MathHelper.wrapAngleTo180_float(par0World.rand.nextFloat() * 360.0F),
						0.0F);
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
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(par1, par2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item base, CreativeTabs par2CreativeTabs, List list) {
		Iterator<MHFCEggInfo> iterator = MHFCMobList.registeredEggs().values().iterator();

		while (iterator.hasNext()) {
			MHFCEggInfo entityegginfo = iterator.next();
			list.add(new ItemStack(base, 1, entityegginfo.spawnedID));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		this.theIcon = par1IconRegister.registerIcon(MHFCReference.item_mhfcspawnegg_overlay_icon);
	}
}
