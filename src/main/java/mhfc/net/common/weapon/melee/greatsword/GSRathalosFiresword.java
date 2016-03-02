package mhfc.net.common.weapon.melee.greatsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class GSRathalosFiresword extends GreatswordClass {

	public GSRathalosFiresword() {
		super(MHFCWeaponMaterialHelper.GSRathalos);
		elementalType(false, true, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_gs_rathalos_name);
		labelWeaponRarity(4);
	}

}
