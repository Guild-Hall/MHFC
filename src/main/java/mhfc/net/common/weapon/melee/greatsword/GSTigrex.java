package mhfc.net.common.weapon.melee.greatsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class GSTigrex extends GreatswordClass {

	public GSTigrex() {
		super(MHFCWeaponMaterialHelper.GSTigrex);
		getWeaponDescription("No Element", 3);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_gs_tigrex_name);
	}

}
