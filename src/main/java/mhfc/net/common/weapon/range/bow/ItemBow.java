package mhfc.net.common.weapon.range.bow;

import java.util.Random;
import java.util.function.Consumer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.util.lib.MHFCReference;
import mhfc.net.common.weapon.ItemWeapon;
import mhfc.net.common.weapon.range.bow.BowWeaponStats.BowWeaponStatsBuilder;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemBow extends ItemWeapon<BowWeaponStats> {
	public static ItemBow build(Consumer<BowWeaponStatsBuilder> config) {
		BowWeaponStatsBuilder builder = new BowWeaponStatsBuilder();
		config.accept(builder);
		return new ItemBow(builder.build());
	}

	public static final String[] ItemNameArray = new String[] { "bow", "bow1", "bow2", "bow3" };
	protected double defaultArrowDamage;

	@SideOnly(Side.CLIENT)
	public IIcon[] IconArray;

	public ItemBow(BowWeaponStats stats) {
		super(stats);
		this.maxStackSize = 1;
		this.setMaxDamage(1000);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return MHFCReference.weapon_bow_name;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if (stack != usingItem) {
			return IconArray[0];
		}
		if (useRemaining > 21) {
			return IconArray[3];
		} else if (useRemaining > 14) {
			return IconArray[2];
		} else if (useRemaining > 7) {
			return IconArray[1];
		}
		return IconArray[0];
	}

	@Override
	public IIcon getIconFromDamage(int par1) {
		return this.IconArray[0];
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
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
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
		player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));

		return par1ItemStack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount) {
		super.onPlayerStoppedUsing(stack, world, player, itemInUseCount);
		int ticksUsed = getMaxItemUseDuration(stack) - itemInUseCount;
		boolean isCreative = (player.capabilities.isCreativeMode);
		if (!player.inventory.hasItem(MHFCItemRegistry.getRegistry().arrow) && !isCreative) {
			return;
		}

		float timeUsed = ticksUsed / 20.0F;
		timeUsed = ((timeUsed * timeUsed) + (timeUsed * 2.0F)) / 3.0F;
		if (timeUsed < 0.5d) {
			return;
		}

		EntityArrow entityarrow = new EntityArrow(world, player, timeUsed * 2.0F);
		boolean crit = new Random().nextInt(10) == 0 ? true : false;

		entityarrow.setIsCritical(crit);

		if (timeUsed >= 1.0F && timeUsed < 1.5F) {
			entityarrow.setIsCritical(true);
		}

		if (timeUsed > 1.0F) {
			timeUsed = 1.0F;
		}

		entityarrow.setDamage(entityarrow.getDamage() + this.stats.getAttack(1f));

		if (isCreative) {
			entityarrow.canBePickedUp = 2;
		} else {
			player.inventory.consumeInventoryItem(MHFCItemRegistry.getRegistry().arrow);
		}
		if (!world.isRemote) {
			world.spawnEntityInWorld(entityarrow);
		}
		float pitch = 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + timeUsed * 0.5F;
		world.playSoundAtEntity(player, "random.bow", 1.0F, pitch);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		player.setItemInUse(stack, count);
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

}
