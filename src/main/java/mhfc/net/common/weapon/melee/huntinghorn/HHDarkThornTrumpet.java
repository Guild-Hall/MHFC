package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HHDarkThornTrumpet extends HuntingHornClass {

	public HHDarkThornTrumpet() {
		super(MHFCWeaponMaterialHelper.HHDarkThornTrumpet, 400, 600);
		setUnlocalizedName(MHFCReference.weapon_hh_darkthorntrumpet_name);
		labelWeaponRarity(1);
		elementalType(false, false, false, false, false, false, false, false);
		enableCooldownDisplay = true;
	}
	
}