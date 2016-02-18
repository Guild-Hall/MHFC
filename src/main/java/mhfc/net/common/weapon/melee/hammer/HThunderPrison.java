package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HThunderPrison extends HammerClass {

	public HThunderPrison() {
		super(MHFCWeaponMaterialHelper.HKirinSpark, 450);
		labelWeaponRarity(8);
		elementalType(false, false, false, false, false, true, false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_kirin_name);
	}

}
