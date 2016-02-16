package mhfc.net.common.weapon.melee.hammer;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class HDeviljho extends HammerClass {

	public HDeviljho() {
		super(MHFCWeaponMaterialHelper.HDeviljho);
		labelWeaponRarity(7);
		elementalType(false, false, false, false, true, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_hm_deviljho_name);
	}
}
