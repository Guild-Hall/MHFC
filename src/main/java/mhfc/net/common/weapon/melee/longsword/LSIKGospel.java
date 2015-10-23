package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSIKGospel extends LongswordClass {

	public LSIKGospel() {
		super(MHFCWeaponMaterialHelper.LSIKGospel);
		getWeaponDescription("No Element", 2);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_ikgospel_name);
	}

}
