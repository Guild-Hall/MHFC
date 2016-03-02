package mhfc.net.common.helper;

import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class MHFCDamageHelper {

	public static DamageSource thunderelement = new DamageSource("mhfc.thunderelement").setDamageBypassesArmor();
	public static DamageSource groundpound = new DamageSource("groundpound").setDamageBypassesArmor();
	public static DamageSource aerial = new DamageSource("aerial").setProjectile();

	
	   public static DamageSource causeWyverniaArrow(EntityWyverniaArrow arrow, Entity ent)
	    {
	        return (new EntityDamageSourceIndirect("arrow", arrow, ent)).setProjectile();
	    }
}
