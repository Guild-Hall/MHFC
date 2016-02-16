package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSDarkVipern extends LongswordClass {

	public LSDarkVipern() {
		super(MHFCWeaponMaterialHelper.LSDarkVipern);
		labelWeaponRarity(2);
		elementalType(true, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_darkvipern_name);
		amplified = 1;
	}

}
