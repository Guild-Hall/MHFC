package mhfc.net.common.helper;

import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class DamageHelper {

	public static DamageSource groundpound = new DamageSource("groundpound").setDamageBypassesArmor();
	public static DamageSource aerial = new DamageSource("aerial").setProjectile();
	//ANTI OTHER MODS.
	public static DamageSource anti = new DamageSource("anti").setDamageBypassesArmor().setDamageIsAbsolute();

	public static DamageSource causeWyverniaArrow(EntityWyverniaArrow arrow, Entity ent) {
		return (new EntityDamageSourceIndirect("arrow", arrow, ent)).setProjectile();
	}
}
