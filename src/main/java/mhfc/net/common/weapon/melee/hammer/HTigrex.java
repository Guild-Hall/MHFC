package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HTigrex extends HammerClass {

	public HTigrex() {
		super(MHFCWeaponMaterialHelper.HTigrex, 450);
		labelWeaponRarity(3);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_tigrex_name);
	}

}
