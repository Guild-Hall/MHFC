package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HWarSlammer extends HammerClass {

	public HWarSlammer() {
		super(MHFCWeaponMaterialHelper.HWarSlammer, 500);
		labelWeaponRarity(2);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_warslammer_name);
	}

}
