package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSTrueDevilSlicer extends LongswordClass {

	public LSTrueDevilSlicer() {
		super(MHFCWeaponMaterialHelper.LSTrueDevilSlicer);
		getWeaponDescription("Thunder Element", 7);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_truedevilslicer_name);
	}

}
