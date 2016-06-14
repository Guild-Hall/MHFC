package mhfc.net.common.weapon.range.bow;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.helper.MHFCWeaponClassingHelper;
import mhfc.net.common.system.ColorSystem;
import mhfc.net.common.util.Cooldown;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BowClass extends Item {

	public static final String[] ItemNameArray = new String[] { "bow", "bow1", "bow2", "bow3" };
	protected boolean poisontype, firetype, enableCooldownDisplay;
	protected String des1, des2, des3; // <--- Shorten the handles
	protected int attackdelay, rarity, meta, getcooldown;
	public boolean rest, start, half, full = false;
	public int usingItem = 72000;

	@SideOnly(Side.CLIENT)
	public IIcon[] IconArray;

	public BowClass() {
		super();
		getWeaponDescription(MHFCWeaponClassingHelper.bowname);
		this.maxStackSize = 1;
		this.setMaxDamage(1000);
		this.setCreativeTab(MHFCMain.mhfctabs);
	}

	public void elementalType(boolean poison, boolean fire) {
		poisontype = poison;
		firetype = fire;
	}

	@Deprecated
	// will rework soon
	public void getWeaponDescription(String title) {
		des1 = title;
	}

	public void getWeaponDescriptionWithMeta(String second, int rarerty, int metaData) {
		des2 = second;
		rarity = rarerty;
		meta = metaData;
	}

	public void getWeaponDescription(String second, int rareity) {
		des2 = second;
		rarity = rareity;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer,
			@SuppressWarnings("rawtypes") List par3List, boolean par4) {
		par3List.add(ColorSystem.gold + des1);
		par3List.add(ColorSystem.dark_green + des2);
		par3List.add(ColorSystem.yellow + "Rarity: " + rarity);
		if (enableCooldownDisplay)
			Cooldown.displayAttackDelay(par1ItemStack, par3List, getcooldown);
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (stack == usingItem) {
			if ((usingItem != null) && (usingItem.getItem() instanceof BowClass)) {
				if (useRemaining > 21) {
					return IconArray[3];
				} else if (useRemaining > 14) {
					return IconArray[2];
				} else if (useRemaining > 7) {
					return IconArray[1];
				}
			}
		}
		return IconArray[0];
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return this.IconArray[0];
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	@Override
	public int getItemEnchantability() {
		return -1;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int par1) {
		return this.IconArray[par1];
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.bow;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 72000;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		return par1ItemStack;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
		player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World par2World, EntityPlayer player, int par4) {
		super.onPlayerStoppedUsing(stack, par2World, player, par4);
		int maxItemUse = getMaxItemUseDuration(stack) - par4;
		boolean flag = (player.capabilities.isCreativeMode);
		if (player.inventory.hasItem(MHFCItemRegistry.mhfcitemarrow) || flag) {

			float f = maxItemUse / 20.0F;
			f = ((f * f) + (f * 2.0F)) / 3.0F;

			if (f < 0.5d) {
				return;
			}

			EntityWyverniaArrow entityarrow = new EntityWyverniaArrow(par2World, player, f * 2.0F);
			boolean crit = new Random().nextInt(10) == 0 ? true : false;

			entityarrow.setIsCritical(crit);

			if (f >= 1.0F && f < 1.5F) {
				entityarrow.setIsCritical(true);
			}

			if (f > 1.0F) {
				f = 1.0F;
			}

			entityarrow.setDamage(entityarrow.getDamage() + (flag ? 8D : 2D));
			

			if (flag) {
				entityarrow.canBePickedUp = 2;
			} else {
				player.inventory.consumeInventoryItem(MHFCItemRegistry.mhfcitemarrow);
			}
			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(entityarrow);
			}
			stack.damageItem(1, player);
			par2World.playSoundAtEntity(player, "random.bow", 1.0F,
					(1.0F / ((itemRand.nextFloat() * 0.4F) + 1.2F)) + (f * 0.5F));
		}
	}
	
	

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {

		player.setItemInUse(stack, count);
		usingItem = count;

	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		this.IconArray = new IIcon[ItemNameArray.length];

		for (int i = 0; i < this.IconArray.length; ++i) {
			String prefix = "mhfc:";
			this.IconArray[i] = par1IconRegister.registerIcon(prefix + ItemNameArray[i]);
		}
		this.itemIcon = this.IconArray[0];
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return false;
	}

}
