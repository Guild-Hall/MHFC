package mhfc.net.common.weapon.range.bow;

import java.util.Random;
import java.util.function.Consumer;

import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.util.Libraries;
import mhfc.net.common.weapon.ItemWeapon;
import mhfc.net.common.weapon.range.bow.BowWeaponStats.BowWeaponStatsBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBow extends ItemWeapon<BowWeaponStats> {
	public static ItemBow build(Consumer<BowWeaponStatsBuilder> config) {
		BowWeaponStatsBuilder builder = new BowWeaponStatsBuilder();
		config.accept(builder);
		return new ItemBow(builder.build());
	}

	public static final String[] ItemNameArray = new String[] { "bow", "bow1", "bow2", "bow3" };
	protected double defaultArrowDamage;

	public ItemBow(BowWeaponStats stats) {
		super(stats);
		addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, World world, EntityLivingBase entity) {
				if (entity == null || stack == null) {
					return 0.0F;
				}
				ItemStack itemstack = entity.getActiveItemStack();
				if (itemstack != stack) {
					return 0.0F;
				}
				return (stack.getMaxItemUseDuration() - entity.getItemInUseCount()) / 20.0F;
			}
		});
		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, World world, EntityLivingBase entity) {
				return entity == null || !entity.isHandActive() || entity.getActiveItemStack() != stack ? 0.0F : 1.0F;
			}
		});
		this.maxStackSize = 1;
		this.setMaxDamage(1000);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return Libraries.weapon_bow_name;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.BOW;
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
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		boolean flag = findAmmo(player) != null;

		if (!player.capabilities.isCreativeMode && !flag) {
			return new ActionResult<>(EnumActionResult.FAIL, stack);
		} else {
			player.setActiveHand(hand);
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
	}

	private ItemStack findAmmo(EntityLivingBase player) {
		// FIXME: todo
		return null;
	}

	private float getBowStrength(ItemStack stack, int timeLeft) {
		int ticksUsed = getMaxItemUseDuration(stack) - timeLeft;
		float timeUsed = ticksUsed / 20.0F;
		timeUsed = timeUsed * (timeUsed + 2.0F) / 3.0F;
		if (timeUsed > 1.0F) {
			timeUsed = 1.0F;
		}
		return timeUsed;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase player, int timeLeft) {
		super.onPlayerStoppedUsing(stack, world, player, timeLeft);
		boolean isCreative = player instanceof EntityPlayer && ((EntityPlayer) player).capabilities.isCreativeMode;

		float bowStrength = getBowStrength(stack, timeLeft);
		if (bowStrength < 0.5d) {
			return;
		}

		ItemStack ammoStack = findAmmo(player);
		if (ammoStack == null && !isCreative) {
			return;
		}

		if (!world.isRemote) {
			EntityArrow entityarrow = new EntityWyverniaArrow(world, player, bowStrength * 2.0F);
			boolean crit = new Random().nextInt(10) == 0;

			entityarrow.setIsCritical(crit);

			// TODO: get the damage from the player, instead of the bow stat?
			entityarrow.setDamage(entityarrow.getDamage() + this.stats.getAttack(1f));
			if (isCreative) {
				entityarrow.pickupStatus = PickupStatus.CREATIVE_ONLY;
			} else {
				ammoStack.damageItem(1, player);
			}
			world.spawnEntityInWorld(entityarrow);
		}
		float volume = bowStrength;
		float pitch = 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + bowStrength * 0.5F;
		world.playSound(
				null,
				player.posX,
				player.posY,
				player.posZ,
				SoundEvents.ENTITY_ARROW_SHOOT,
				SoundCategory.NEUTRAL,
				volume,
				pitch);
	}
}
