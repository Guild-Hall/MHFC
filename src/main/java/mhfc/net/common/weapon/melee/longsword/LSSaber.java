package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSSaber extends LongswordClass {

	public LSSaber() {
		super(MHFCWeaponMaterialHelper.LSSaber);
		getWeaponDescription("Fire Element", 5);
		elementalType(false, true);
		setUnlocalizedName(MHFCReference.weapon_ls_saber_name);
	}

}
