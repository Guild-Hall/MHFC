package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSSaber extends LongswordClass {

	public LSSaber() {
		super(MHFCWeaponMaterialHelper.LSSaber);
		labelWeaponRarity(5);
		elementalType(false, true, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_saber_name);
	}

}
