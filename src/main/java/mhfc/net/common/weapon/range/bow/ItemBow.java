package mhfc.net.common.weapon.range.bow;

import java.util.Random;
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
import net.minecraft.entity.projectile.EntityArrow;
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

	public static ItemBow build(Consumer<BowWeaponStatsBuilder> config) {
		BowWeaponStatsBuilder builder = new BowWeaponStatsBuilder();
		config.accept(builder);
		return new ItemBow(builder.build());
	}

	/**
	 * 
	 * Checks if the ammo is arrow.
	 */
	public boolean isTheAmmoArrow(ItemStack stack) {
		return stack.getItem() instanceof ItemWyverniaArrow;
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
		boolean flag = !this.bowAmmo(player).isEmpty();

		ActionResult<ItemStack> eventarrowNock = net.minecraftforge.event.ForgeEventFactory
				.onArrowNock(itemstack, world, player, hand, flag);
		if (eventarrowNock != null)
			return eventarrowNock;

		if (!player.capabilities.isCreativeMode && !flag) {
			return flag
					? new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack)
					: new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		} else {
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
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
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityln, int timeLeft) {
		super.onPlayerStoppedUsing(stack, world, entityln, timeLeft);
		
		
		EntityPlayer entityplayer = (EntityPlayer)entityln;
		boolean isCreative = entityln instanceof EntityPlayer && ((EntityPlayer) entityln).capabilities.isCreativeMode;
		ItemStack itemstack = this.bowAmmo(entityplayer);	
		
		int i = this.getMaxItemUseDuration(stack) - timeLeft;
		i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, (EntityPlayer)entityln, i, true);
		if (i < 0) return;
		
		
		if (!itemstack.isEmpty() || isCreative)
           {
               if (itemstack.isEmpty())
               {
                   itemstack = new ItemStack(MHFCItemRegistry.getRegistry().arrow);
               }
		}

		float bowStrength = getBowStrength(stack, timeLeft);
		if (bowStrength < 0.5d) {
			return;
		}

		if (!world.isRemote) {
			EntityArrow entityarrow = new EntityWyverniaArrow(world, entityln, bowStrength * 2.0F);
			boolean crit = new Random().nextInt(10) == 0;
			float time = (stack.getMaxItemUseDuration() - entityln.getItemInUseCount()) / 20.0F;
			if(time < 2f) {
				world.spawnEntity(entityarrow);
			}
			entityarrow.setIsCritical(crit);

			// TODO: get the damage from the player, instead of the bow stat?
			entityarrow.setDamage(entityarrow.getDamage() + this.stats.getAttack(1f));
			if (isCreative) {
				entityarrow.pickupStatus = PickupStatus.CREATIVE_ONLY;
			} else {
				itemstack.damageItem(1, entityln);
			}
			world.spawnEntity(entityarrow);
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
		if (!entityplayer.capabilities.isCreativeMode) {
			itemstack.shrink(1);

			if (itemstack.isEmpty()) {
				entityplayer.inventory.deleteStack(itemstack);
			}
		}
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

	private ItemStack bowAmmo(EntityPlayer theplayer) {
		if (this.isTheAmmoArrow(theplayer.getHeldItem(EnumHand.OFF_HAND))) {
			return theplayer.getHeldItem(EnumHand.OFF_HAND);
		} else if (this.isTheAmmoArrow(theplayer.getHeldItem(EnumHand.MAIN_HAND))) {
			return theplayer.getHeldItem(EnumHand.MAIN_HAND);
		} else {
			for (int a = 0; a < theplayer.inventory.getSizeInventory(); ++a) {
				ItemStack itemstack = theplayer.inventory.getStackInSlot(a);

				if (this.isTheAmmoArrow(itemstack)) {
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

}
