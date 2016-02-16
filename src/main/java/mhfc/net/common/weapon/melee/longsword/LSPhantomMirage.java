package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSPhantomMirage extends LongswordClass {

	public LSPhantomMirage() {
		super(MHFCWeaponMaterialHelper.LSPhantomMirage);
		labelWeaponRarity(7);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_phantommirage_name);
	}

}
