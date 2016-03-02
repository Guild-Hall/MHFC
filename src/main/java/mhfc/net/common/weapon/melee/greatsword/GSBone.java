package mhfc.net.common.weapon.melee.greatsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class GSBone extends GreatswordClass {

	public GSBone() {
		super(MHFCWeaponMaterialHelper.GSBoneBlade);
		labelWeaponRarity(1);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_gs_bone_name);
	}

}
