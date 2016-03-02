package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSLionDanceSaber extends LongswordClass {

	public LSLionDanceSaber() {
		super(MHFCWeaponMaterialHelper.LSLionDanceSaber, 120);
		labelWeaponRarity(7);
		elementalType(false, true, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_liondancesaber_name);
	}

}
