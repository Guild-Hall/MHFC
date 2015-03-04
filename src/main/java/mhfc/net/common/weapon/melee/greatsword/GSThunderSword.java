package mhfc.net.common.weapon.melee.greatsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class GSThunderSword extends GreatswordClass {

	public GSThunderSword() {
		super(MHFCWeaponMaterialHelper.GSKirin);
		getWeaponDescription("Thunder Element", 4);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_gs_kirin_name);
	}

}
