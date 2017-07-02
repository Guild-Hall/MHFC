package mhfc.net.common.weapon.melee.hammer;

import java.util.function.Consumer;

import com.google.common.collect.Multimap;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.index.AttributeModifiers;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.hammer.HammerWeaponStats.HammerWeaponStatsBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;

public class ItemHammer extends ItemWeaponMelee<HammerWeaponStats> {
	public static ItemHammer build(Consumer<HammerWeaponStatsBuilder> config) {
		HammerWeaponStatsBuilder builder = new HammerWeaponStatsBuilder();
		config.accept(builder);
		return new ItemHammer(builder.build());
	}

	protected static final double motY = 0.2D;
	protected static final int stunDur = 140;
	protected static final int critDamageFlat = 20; // Hammer deals FLAT.

	public ItemHammer(HammerWeaponStats stats) {
		super(stats);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return ResourceInterface.weapon_hammer_name;
	}

	@Override
	protected double getMovementSpeedMultiplier(ItemStack stack) {
		return -0.25;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

		entityLiving.world.playSound(
				null,
				entityLiving.posX,
				entityLiving.posY,
				entityLiving.posZ,
				MHFCSoundRegistry.getRegistry().hammerstrike,
				SoundCategory.NEUTRAL,
				2F,
				2F);
		return super.onEntitySwing(entityLiving, stack);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (!isOffCooldown(stack)) {
			return false;
		}

		target.addPotionEffect(new PotionEffect(MHFCPotionRegistry.getRegistry().stun, stunDur, 5));
		triggerCooldown(stack);
		return true;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(
			EntityEquipmentSlot equipmentSlot,
			ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);
		if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
			AttributeModifier attackSpeedModifier = new AttributeModifier(
					ATTACK_SPEED_MODIFIER,
					"Weapon modifier",
					-0.4,
					AttributeModifiers.MULTIPLICATIVE);
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), attackSpeedModifier);
		}
		return multimap;

	}
}
