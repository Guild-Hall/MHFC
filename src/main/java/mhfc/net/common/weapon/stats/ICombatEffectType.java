package mhfc.net.common.weapon.stats;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.util.Random;

public interface ICombatEffectType {
	default void onEntitySwing(
			EntityLivingBase entity,
			ItemStack stack,
			Random rand) {}

	void applyTo(EntityLivingBase target, float damageAmount, EntityLivingBase attacker);

	String getUnlocalizedName();
}
