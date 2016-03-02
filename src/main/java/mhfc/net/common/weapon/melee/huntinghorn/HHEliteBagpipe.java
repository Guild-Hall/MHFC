package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HHEliteBagpipe extends HuntingHornClass {

	public HHEliteBagpipe() {
		super(MHFCWeaponMaterialHelper.HHEliteBagpipe, 400, 600);
		setUnlocalizedName(MHFCReference.weapon_hh_elitebagpipe_name);
		labelWeaponRarity(6);
		elementalType(false, false, false, false, false, false, false, false);
		enableCooldownDisplay = true;
	}
	
}