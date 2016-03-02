package mhfc.net.common.weapon.melee.greatsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class GSTigrex extends GreatswordClass {

	public GSTigrex() {
		super(MHFCWeaponMaterialHelper.GSTigrex);
		labelWeaponRarity(4);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_gs_tigrex_name);
	}

}
