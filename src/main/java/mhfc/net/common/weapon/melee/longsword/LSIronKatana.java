package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSIronKatana extends LongswordClass {

	public LSIronKatana() {
		super(MHFCWeaponMaterialHelper.LSIronKatana, 120);
		labelWeaponRarity(1);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_ironkatana_name);
	}

}
