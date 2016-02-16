package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSDevilSlicer extends LongswordClass {

	public LSDevilSlicer() {
		super(MHFCWeaponMaterialHelper.LSDevilSlicer);
		labelWeaponRarity(4);
		elementalType(false, false, false, false, false, true, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_devilslicer_name);
	}

}
