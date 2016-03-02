package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSLionKingSaber extends LongswordClass {

	public LSLionKingSaber() {
		super(MHFCWeaponMaterialHelper.LSLionKingSaber, 120);
		labelWeaponRarity(8);
		elementalType(false, true, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_lionkingsaber_name);
	}

}
