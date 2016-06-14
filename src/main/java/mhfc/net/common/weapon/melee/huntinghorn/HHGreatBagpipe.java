package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HHGreatBagpipe extends HuntingHornClass {

	public HHGreatBagpipe() {
		super(MHFCWeaponMaterialHelper.HHGreatbagpipe, 400, 600);
		setUnlocalizedName(MHFCReference.weapon_hh_greatbagpipe_name);
		labelWeaponRarity(2);
		elementalType(false, false, false, false, false, false, false, false);
		enableCooldownDisplay = true;
	}
	
}