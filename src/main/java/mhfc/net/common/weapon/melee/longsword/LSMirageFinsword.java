package mhfc.net.common.weapon.melee.longsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class LSMirageFinsword extends LongswordClass {

	public LSMirageFinsword() {
		super(MHFCWeaponMaterialHelper.LSMirageFinsword, 120);
		labelWeaponRarity(6);
		elementalType(false, false, false, false, false, false, false, false);
		setUnlocalizedName(MHFCReference.weapon_ls_miragefinsword_name);
	}

}
