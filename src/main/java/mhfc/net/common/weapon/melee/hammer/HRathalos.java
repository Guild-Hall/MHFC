package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HRathalos extends HammerClass {

	public HRathalos() {
		super(MHFCWeaponMaterialHelper.HRathalos);
		labelWeaponRarity(4);
		elementalType(false, true, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_rathalos_name);
	}

}
