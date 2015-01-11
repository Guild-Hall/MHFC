package mhfc.heltrato.common.helper;

import net.minecraft.util.DamageSource;

public class MHFCDamageHelper {
	
	public static DamageSource thunderelement = new DamageSource("mhfc.thunderelement").setDamageBypassesArmor();
	public static DamageSource groundpound = new DamageSource("groundpound").setDamageBypassesArmor();
	public static DamageSource aerial = new DamageSource("aerial").setProjectile();

	

}
