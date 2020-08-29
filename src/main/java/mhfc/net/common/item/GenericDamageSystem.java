package mhfc.net.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;

public class GenericDamageSystem extends ArmorProperties {

	public GenericDamageSystem(int priority, double ratio, int max) {
		super(priority, ratio, max);
	}

	public static float applyArmor(
			EntityLivingBase entity,
			NonNullList<ItemStack> inventory,
			DamageSource source,
			double damage) {
		return 0;

	}

}
