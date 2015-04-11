package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSPhantomMirage extends LongswordClass {

	public LSPhantomMirage() {
		super(MHFCWeaponMaterialHelper.LSPhantomMirage);
		getWeaponDescription("No Element", 7);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_phantommirage_name);
	}

}
