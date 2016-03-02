package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSIronKatana_Grace extends LongswordClass {

	public LSIronKatana_Grace() {
		super(MHFCWeaponMaterialHelper.LSIKGrace, 120);
		labelWeaponRarity(2);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_ikgrace_name);
	}

}
