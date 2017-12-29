package mhfc.net.common.weapon.melee.longsword;

import java.util.UUID;
import java.util.function.Consumer;

import com.google.common.collect.Multimap;

import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.index.AttributeModifiers;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.util.NBTUtils;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.longsword.LongswordWeaponStats.LongswordWeaponStatsBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemLongsword extends ItemWeaponMelee<LongswordWeaponStats> {
	public static ItemLongsword build(Consumer<LongswordWeaponStatsBuilder> config) {
		LongswordWeaponStatsBuilder builder = new LongswordWeaponStatsBuilder();
		config.accept(builder);
		return new ItemLongsword(builder.build());
	}

	protected static final String NBT_SPIRIT = "mhfc:affinity";
	protected static final UUID LONGSWORD_AFFINITY_UUID = UUID.fromString("e6f4502b-1242-4024-bc5e-e89f47fcda76");
	protected static final float MAX_SPIRIT = 250F;
	protected static final float TRIGGER_SPIRIT = 180f;
	protected static final float SPIRIT_DECREASE = -0.4f;

	public ItemLongsword(LongswordWeaponStats stats) {
		super(stats);
	}

	protected float getAffinity(ItemStack stack) {
		return NBTUtils.getNBTChecked(stack).getFloat(NBT_SPIRIT);
	}

	@Override
	protected double getMovementSpeedMultiplier(ItemStack stack) {
		return -0.25;
	}

	protected float changeSpirit(ItemStack stack, float change) {
		float current = getAffinity(stack);
		current = Math.min(Math.max(current + change, 0f), MAX_SPIRIT);
		NBTUtils.getNBTChecked(stack).setFloat(NBT_SPIRIT, current);
		return current;
	}

	public boolean isAffinityTriggered(ItemStack stack) {
		return getAffinity(stack) > TRIGGER_SPIRIT;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity holder, int slot, boolean isHoldItem) {
		super.onUpdate(stack, world, holder, slot, isHoldItem);
		changeSpirit(stack, SPIRIT_DECREASE);
		if (!isHoldItem) {
			return;
		}
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		changeSpirit(entityItem.getEntityItem(), SPIRIT_DECREASE);
		return super.onEntityItemUpdate(entityItem);
	}

	/**
	 * Gets the current affinity as a float in [0..1]
	 *
	 * @param stack
	 * @return
	 */
	public float getSpiritPercentage(ItemStack stack) {
		return Math.min(TRIGGER_SPIRIT, getAffinity(stack)) / TRIGGER_SPIRIT;
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return ResourceInterface.weapon_longsword_name;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		attacker.world.playSound(
				null,
				attacker.posX,
				attacker.posY,
				attacker.posZ,
				MHFCSoundRegistry.getRegistry().longswordstrike,
				SoundCategory.NEUTRAL,
				1F,
				1F);
		changeSpirit(stack, 10);
		return true;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		entityLiving.world.playSound(
				null,
				entityLiving.posX,
				entityLiving.posY,
				entityLiving.posZ,
				MHFCSoundRegistry.getRegistry().longswordswing,
				SoundCategory.NEUTRAL,
				1F,
				1F);
		return false;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> attributes = super.getAttributeModifiers(slot, stack);
		if (slot != EntityEquipmentSlot.MAINHAND) {
			return attributes;
		}
		if (isAffinityTriggered(stack)) {
			AttributeModifier attackModifier = new AttributeModifier(
					LONGSWORD_AFFINITY_UUID,
					"Spirit Gauge",
					0.3,
					AttributeModifiers.ADDITIVE_MULT);
			attributes.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), attackModifier);
		}
		return attributes;
	}
}
