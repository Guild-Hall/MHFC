package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HWarPlus extends HammerClass {

	public HWarPlus() {
		super(MHFCWeaponMaterialHelper.HWarHammerplus);
		labelWeaponRarity(1);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_warplus_name);
	}
}
