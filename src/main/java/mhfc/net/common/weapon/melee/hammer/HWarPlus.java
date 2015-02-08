package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HWarPlus extends HammerClass {

	public HWarPlus() {
		super(MHFCWeaponMaterialHelper.HWarHammerplus);
		getWeaponDescription("No", 2);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_warplus_name);
	}
}
