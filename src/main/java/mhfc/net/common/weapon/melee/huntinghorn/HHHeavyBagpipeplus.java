package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HHHeavyBagpipeplus extends HuntingHornClass {

	public HHHeavyBagpipeplus() {
		super(MHFCWeaponMaterialHelper.HHHeavyBagpipeplus, 400, 600);
		setUnlocalizedName(MHFCReference.weapon_hh_heavybagpipeplus_name);
		labelWeaponRarity(3);
		elementalType(false, false, false, false, false, false, false, false);
		enableCooldownDisplay = true;
	}
	
}