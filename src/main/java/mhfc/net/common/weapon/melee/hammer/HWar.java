package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HWar extends HammerClass {

	public HWar() {
		super(MHFCWeaponMaterialHelper.HWarHammer);
		getWeaponDescription("No", 2);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_war_name);
	}

}
