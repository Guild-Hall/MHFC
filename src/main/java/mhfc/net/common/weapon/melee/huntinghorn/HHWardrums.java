package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HHWardrums extends HuntingHornClass {

	public HHWardrums() {
		super(MHFCWeaponMaterialHelper.HHWardrums, 400, 600);
		setUnlocalizedName(MHFCReference.weapon_hh_wardrums_name);
		labelWeaponRarity(2);
		elementalType(false, false, false, false, false, false, false, false);
		enableCooldownDisplay = true;
	}
	
}