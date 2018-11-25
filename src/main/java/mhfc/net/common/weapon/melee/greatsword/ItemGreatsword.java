package mhfc.net.common.weapon.melee.greatsword;

import com.google.common.collect.Multimap;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.index.AttributeModifiers;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.weapon.melee.ItemWeaponMelee;
import mhfc.net.common.weapon.melee.greatsword.GreatswordWeaponStats.GreatswordWeaponStatsBuilder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;

import java.util.function.Consumer;

public class ItemGreatsword extends ItemWeaponMelee<GreatswordWeaponStats> {
	public static ItemGreatsword build(Consumer<GreatswordWeaponStatsBuilder> config) {
		GreatswordWeaponStatsBuilder builder = new GreatswordWeaponStatsBuilder();
		config.accept(builder);
		return new ItemGreatsword(builder.build());
	}

	public ItemGreatsword(GreatswordWeaponStats stats) {
		super(stats);
	}

	@Override
	public String getWeaponClassUnlocalized() {
		return ResourceInterface.weapon_greatsword_name;
	}

	@Override
	protected double getMovementSpeedMultiplier(ItemStack stack) {
		return -0.5;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {

		entityLiving.world.playSound(
				null,
				entityLiving.posX,
				entityLiving.posY,
				entityLiving.posZ,
				MHFCSoundRegistry.getRegistry().greatswordstrike,
				SoundCategory.NEUTRAL,
				2F,
				2F);
		return super.onEntitySwing(entityLiving, stack);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
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
					-0.7,
					AttributeModifiers.MULTIPLICATIVE);
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), attackSpeedModifier);
		}

		return multimap;

	}

}
