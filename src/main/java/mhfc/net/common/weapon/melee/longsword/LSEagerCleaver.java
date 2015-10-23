package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSEagerCleaver extends LongswordClass {

	public LSEagerCleaver() {
		super(MHFCWeaponMaterialHelper.LSEagerCleaver);
		getWeaponDescription("Thunder Element", 3);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_eagercleaver_name);
	}

}
