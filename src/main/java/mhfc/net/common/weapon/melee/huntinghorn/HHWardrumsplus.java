package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HHWardrumsplus extends HuntingHornClass {

	public HHWardrumsplus() {
		super(MHFCWeaponMaterialHelper.HHWardrumsplus, 400, 600);
		setUnlocalizedName(MHFCReference.weapon_hh_wardrumsplus_name);
		labelWeaponRarity(2);
		elementalType(false, false, false, false, false, false, false, false);
		enableCooldownDisplay = true;
	}
	
}