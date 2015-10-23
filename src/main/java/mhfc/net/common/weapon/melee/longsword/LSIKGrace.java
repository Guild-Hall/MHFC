package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSIKGrace extends LongswordClass {

	public LSIKGrace() {
		super(MHFCWeaponMaterialHelper.LSIKGrace);
		getWeaponDescription("No Element", 2);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_ikgrace_name);
	}

}
