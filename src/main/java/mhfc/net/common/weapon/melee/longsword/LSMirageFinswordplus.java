package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSMirageFinswordplus extends LongswordClass {

	public LSMirageFinswordplus() {
		super(MHFCWeaponMaterialHelper.LSMirageFinswordplus);
		labelWeaponRarity(6);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_miragefinswordplus_name);
	}

}
