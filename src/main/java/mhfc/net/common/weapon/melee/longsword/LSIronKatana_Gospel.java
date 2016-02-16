package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSIronKatana_Gospel extends LongswordClass {

	public LSIronKatana_Gospel() {
		super(MHFCWeaponMaterialHelper.LSIKGospel);
		labelWeaponRarity(2);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_ikgospel_name);
	}

}
