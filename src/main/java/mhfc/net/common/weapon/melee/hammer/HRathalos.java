package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HRathalos extends HammerClass {

	public HRathalos() {
		super(MHFCWeaponMaterialHelper.HRathalos);
		getWeaponDescription("Fire Element", 5);
		elementalType(false, true);
		setUnlocalizedName(MHFCReference.weapon_hm_rathalos_name);
	}

}
