package mhfc.net.common.weapon.stats;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface ICombatEffectType {
	default void onEntitySwing(EntityLivingBase entity, ItemStack stack, Random rand) {}

	void applyTo(Entity target, float damageAmount);

	String getUnlocalizedName();
}
