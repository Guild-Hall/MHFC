package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSLionKaiserSaber extends LongswordClass {

	public LSLionKaiserSaber() {
		super(MHFCWeaponMaterialHelper.LSLionKaiserSaber);
		getWeaponDescription("Fire Element", 8);
		elementalType(false, true);
		setUnlocalizedName(MHFCReference.weapon_ls_lionkaisersaber_name);
	}

}
