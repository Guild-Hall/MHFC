package mhfc.net.common.weapon.melee.greatsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class GSDeviljhobroadsword extends GreatswordClass {

	public GSDeviljhobroadsword() {
		super(MHFCWeaponMaterialHelper.GSDeviljho);
		getWeaponDescription("No Element", 6);
		setUnlocalizedName(MHFCReference.weapon_gs_deviljho_name);
		elementalType(false, false);
	}

}
