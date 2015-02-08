package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HTigrex extends HammerClass {

	public HTigrex() {
		super(MHFCWeaponMaterialHelper.HTigrex);
		getWeaponDescription("No Element", 5);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_tigrex_name);
	}

}
