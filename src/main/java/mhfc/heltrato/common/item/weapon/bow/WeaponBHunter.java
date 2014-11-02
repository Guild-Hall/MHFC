package mhfc.heltrato.common.item.weapon.bow;

import java.util.List;

import mhfc.heltrato.MHFCMain;
import mhfc.heltrato.common.core.registry.MHFCRegItem;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WeaponBHunter extends Item {

	public static final String[] itemNameArray = {
			MHFCReference.weapon_bow_icon0,
			MHFCReference.weapon_bow_icon1,
			MHFCReference.weapon_bow_icon2,
			MHFCReference.weapon_bow_icon3};

	public int usingItem = 0;

	@SideOnly(Side.CLIENT)
	private IIcon[] iconArray;
	public WeaponBHunter() {
		maxStackSize = 1;
		setMaxDamage(384);
		setCreativeTab(MHFCMain.mhfctabs);
		setUnlocalizedName(MHFCReference.weapon_bow_hunter_name);

	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add("Bow Class");
		par3List.add("\u00a79No Element");
		par3List.add("\u00a72Range Damage");
	}
	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return false;
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.iconArray = new IIcon[itemNameArray.length];

		for (int i = 0; i < this.iconArray.length; i++) {
			this.iconArray[i] = par1IconRegister.registerIcon(itemNameArray[i]);
		}

		this.itemIcon = this.iconArray[0];
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player,
			ItemStack usingItem, int useRemaining) {
		if ((stack == usingItem) && (usingItem != null)
				&& (usingItem.getItem() == MHFCRegItem.mhfcitembhunter)) {
			if (useRemaining > 21)
				return this.iconArray[3];
			if (useRemaining > 14)
				return this.iconArray[2];
			if (useRemaining > 7)
				return this.iconArray[1];
		}
		return this.iconArray[0];
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return this.iconArray[0];
	}

	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int par1) {
		return this.iconArray[par1];
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		int j = this.getMaxItemUseDuration(par1ItemStack) - par4;
		usingItem = 0;
		ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer,
				par1ItemStack, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled()) {
			return;
		}
		j = event.charge;

		boolean flag = par3EntityPlayer.capabilities.isCreativeMode
				|| EnchantmentHelper.getEnchantmentLevel(
						Enchantment.infinity.effectId, par1ItemStack) > 0;

		if (flag || par3EntityPlayer.inventory.hasItem(Items.arrow)) {
			float f = j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;

			if (f < 0.1D) {
				return;
			}

			if (f > 1.0F) {
				f = 1.0F;
			}

			EntityArrow entityarrow = new EntityArrow(par2World,
					par3EntityPlayer, f * 2.0F);
			entityarrow.setDamage(6);

			if (f == 1.0F) {
				entityarrow.setIsCritical(true);
			}

			int k = EnchantmentHelper.getEnchantmentLevel(
					Enchantment.power.effectId, par1ItemStack);

			if (k > 0) {
				entityarrow
						.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);
			}

			int l = EnchantmentHelper.getEnchantmentLevel(
					Enchantment.punch.effectId, par1ItemStack);

			if (l > 0) {
				entityarrow.setKnockbackStrength(l);
			}

			if (EnchantmentHelper.getEnchantmentLevel(
					Enchantment.flame.effectId, par1ItemStack) > 0) {
				entityarrow.setFire(100);
			}

			par1ItemStack.damageItem(1, par3EntityPlayer);
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F,
					1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);
			}

			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(entityarrow);
			}
		}
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		player.setItemInUse(stack, count);
		this.usingItem += 1;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer,
				par1ItemStack);
		if (MinecraftForge.EVENT_BUS.post(event)) {
			return event.result;
		}

		if (par3EntityPlayer.capabilities.isCreativeMode
				|| par3EntityPlayer.inventory.hasItem(Items.arrow)) {
			par3EntityPlayer.setItemInUse(par1ItemStack,
					this.getMaxItemUseDuration(par1ItemStack));
		}

		return par1ItemStack;
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return 1;
	}
}
