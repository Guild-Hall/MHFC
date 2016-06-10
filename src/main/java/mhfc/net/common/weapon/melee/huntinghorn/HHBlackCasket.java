package mhfc.net.common.weapon.melee.huntinghorn;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HHBlackCasket extends HuntingHornClass {

	public HHBlackCasket() {
		super(MHFCWeaponMaterialHelper.HHBlackCasket, 400, 600);
		setUnlocalizedName(MHFCReference.weapon_hh_blackcasket_name);
		labelWeaponRarity(10);
		elementalType(false, false, false, false, false, false, false, false);
		enableCooldownDisplay = true;
	}
	
}