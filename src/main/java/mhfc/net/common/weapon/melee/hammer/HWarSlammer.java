package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HWarSlammer extends HammerClass {

	public HWarSlammer() {
		super(MHFCWeaponMaterialHelper.HWarSlammer);
		getWeaponDescription("No", 2);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_warslammer_name);
	}

}
