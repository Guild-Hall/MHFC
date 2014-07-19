package mhfc.net.client.damage;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.util.DamageSource;

public class DamageSourceShatter extends DamageSource {

	public DamageSourceShatter(String par1Str) {
		super(par1Str);
	}

	private static DamageSourceShatter instance = null;

	public static DamageSourceShatter instance() {
		if (instance == null) {
			instance = (DamageSourceShatter) new DamageSourceShatter(
					MHFCReference.damagesource_shatter_name)
					.setDamageBypassesArmor();
		}
		return instance;
	}

}
