package mhfc.net.common.weapon.range.bow;

import java.util.UUID;
import java.util.function.Consumer;

import com.google.common.collect.Multimap;

import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.index.AttributeModifiers;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.materials.ItemWyverniaArrow;
import mhfc.net.common.weapon.ItemWeapon;
import mhfc.net.common.weapon.range.bow.BowWeaponStats.BowWeaponStatsBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow.PickupStatus;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
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
	protected static final UUID BOW_EFFECT_UUID = UUID.fromString("924f7565-3a7c-49b6-a521-1a4af6763756");

	protected int arrowFireCount;

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
		return ResourceInterface.weapon_bow_name;
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack itemstack = player.getHeldItem(hand);
		boolean hasAmmunition = !this.findAmmunition(player).isEmpty();

		ActionResult<ItemStack> eventarrowNock = net.minecraftforge.event.ForgeEventFactory
				.onArrowNock(itemstack, world, player, hand, hasAmmunition);
		if (eventarrowNock != null) {
			return eventarrowNock;
		}

		if (!player.capabilities.isCreativeMode && !hasAmmunition) {
			return hasAmmunition
					? new ActionResult<>(EnumActionResult.PASS, itemstack)
							: new ActionResult<>(EnumActionResult.FAIL, itemstack);
		} else {
			player.setActiveHand(hand);
			return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
		}
	}

	protected boolean isConsideredAmmunition(ItemStack stack) {
		return stack.getItem() instanceof ItemWyverniaArrow;
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

	private void spawnArrow(EntityPlayer player, World worldIn, ItemStack ammunition, float power) {
		ItemWyverniaArrow itemarrow = (ItemWyverniaArrow) ammunition.getItem();
		EntityWyverniaArrow entityarrow = itemarrow.createArrow(worldIn, new ItemStack(itemarrow), player, power);
		EntityWyverniaArrow entityarrow2 = itemarrow.createArrow(worldIn, new ItemStack(itemarrow), player, power);
		boolean isCreative = player.capabilities.isCreativeMode;
		entityarrow2.setAim(
				player,
				player.rotationPitch + player.rotationYaw / 2f,
				player.rotationYaw,
				0.0F,
				power * 3.0F,
				0.3F);
		entityarrow.setDamage(entityarrow.getDamage() + 2 * 0.5D + 0.5D);
		entityarrow.setKnockbackStrength(1);

		entityarrow2.setDamage(entityarrow.getDamage() + 2 * 0.5D + 0.5D);
		entityarrow2.setKnockbackStrength(1);

		if (isCreative) {
			entityarrow.pickupStatus = PickupStatus.CREATIVE_ONLY;
			entityarrow2.pickupStatus = PickupStatus.CREATIVE_ONLY;
		} else {
			ammunition.shrink(1);
			if (ammunition.isEmpty()) {
				player.inventory.deleteStack(ammunition);
			}
		}
		boolean shouldCrit = worldIn.rand.nextInt(10) == 0;
		if (shouldCrit) {
			entityarrow.setIsCritical(shouldCrit);
		}
		// TODO: get the damage from the player, instead of the bow stat?
		entityarrow.setDamage(entityarrow.getDamage() + this.stats.getAttack(1f));
		worldIn.spawnEntity(entityarrow);
		if (!ammunition.isEmpty() && ammunition.getItem() == MHFCItemRegistry.getRegistry().weapon_b_huntersproud) {

			worldIn.spawnEntity(entityarrow2);
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityln, int timeLeft) {
		super.onPlayerStoppedUsing(stack, world, entityln, timeLeft);


		EntityPlayer entityplayer = (EntityPlayer)entityln;
		boolean isCreative = entityln instanceof EntityPlayer && ((EntityPlayer) entityln).capabilities.isCreativeMode;
		ItemStack ammunition = this.findAmmunition(entityplayer);

		int i = this.getMaxItemUseDuration(stack) - timeLeft;
		i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, (EntityPlayer)entityln, i, true);
		if (i < 0) {
			return;
		}

		if (!ammunition.isEmpty() || isCreative) {
			if (ammunition.isEmpty()) {
				ammunition = new ItemStack(MHFCItemRegistry.getRegistry().arrow);
			}
		}

		float bowStrength = getBowStrength(stack, timeLeft);
		if (bowStrength < 0.5d) {
			return;
		}
		

		if (!world.isRemote) {
			spawnArrow(entityplayer, world, ammunition, bowStrength);
		}

		float volume = bowStrength;
		float pitch = 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + bowStrength * 0.5F;
		world.playSound(
				null,
				entityln.posX,
				entityln.posY,
				entityln.posZ,
				SoundEvents.ENTITY_ARROW_SHOOT,
				SoundCategory.NEUTRAL,
				volume,
				pitch);
	}

	public static float getArrowVelocity(int charge) {
		float f = charge / 30.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attributeModifiers = super.getAttributeModifiers(slot, stack);
		if (slot == EntityEquipmentSlot.MAINHAND) {
			attributeModifiers.put(
					SharedMonsterAttributes.MOVEMENT_SPEED.getName(),
					new AttributeModifier(
							BOW_EFFECT_UUID,
							"Weapon modifier",
							-0.1f,
							AttributeModifiers.MULTIPLICATIVE));
		}
		return attributeModifiers;
	}

	private ItemStack findAmmunition(EntityPlayer theplayer) {
		if (this.isConsideredAmmunition(theplayer.getHeldItem(EnumHand.OFF_HAND))) {
			return theplayer.getHeldItem(EnumHand.OFF_HAND);
		} else if (this.isConsideredAmmunition(theplayer.getHeldItem(EnumHand.MAIN_HAND))) {
			return theplayer.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int a = 0; a < theplayer.inventory.getSizeInventory(); ++a) {
				ItemStack itemstack = theplayer.inventory.getStackInSlot(a);

				if (this.isConsideredAmmunition(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

}
