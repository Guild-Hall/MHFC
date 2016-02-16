package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSLionsRoarSaber extends LongswordClass {

	public LSLionsRoarSaber() {
		super(MHFCWeaponMaterialHelper.LSLionsRoarSaber);
		labelWeaponRarity(9);
		elementalType(false, true, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_lionsroarsaber_name);
	}

}
