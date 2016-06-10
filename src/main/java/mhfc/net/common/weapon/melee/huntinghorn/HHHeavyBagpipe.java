package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HHHeavyBagpipe extends HuntingHornClass {

	public HHHeavyBagpipe() {
		super(MHFCWeaponMaterialHelper.HHHeavyBagpipe, 400, 600);
		setUnlocalizedName(MHFCReference.weapon_hh_heavybagpipe_name);
		labelWeaponRarity(3);
		elementalType(false, false, false, false, false, false, false, false);
		enableCooldownDisplay = true;
	}
	
}