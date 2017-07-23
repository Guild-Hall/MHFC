package mhfc.net.common.index;

import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class DamageSources {

	/**
	 * 
	 * The plan here is to implement a new Damage source for the weapons which calculates random damages
	 * 
	 * And for monsters which enables to send message using AI instead of the typical "user has slain by <entity>
	 * 
	 * 
	 * 
	 * PLAN TO BE FULLY IMPLEMENTED. 1.4.3
	 * 
	 * @Heltrato
	 * 
	 * 
	 * 
	 */

	public static DamageSource monstersource = new DamageSource("monstersource").setDamageBypassesArmor();
	public static DamageSource aerial = new DamageSource("aerial").setProjectile();
	//ANTI OTHER MODS.
	public static DamageSource anti = new DamageSource("anti").setDamageBypassesArmor().setDamageIsAbsolute();

	public static DamageSource causeWyverniaArrow(EntityWyverniaArrow arrow, Entity ent) {
		return (new EntityDamageSourceIndirect("arrow", arrow, ent)).setProjectile();
	}
}
