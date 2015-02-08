package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HKirinSpark extends HammerClass {

	public HKirinSpark() {
		super(MHFCWeaponMaterialHelper.HKirinSpark);
		getWeaponDescription("Thunder Element", 7);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_kirin_name);
	}

}
