package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSMirageFinsword extends LongswordClass {

	public LSMirageFinsword() {
		super(MHFCWeaponMaterialHelper.LSMirageFinsword);
		getWeaponDescription("No Element", 6);
		elementalType(false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_miragefinsword_name);
	}

}
