package mhfc.net.common.weapon.melee.greatsword;

import mhfc.net.common.helper.MHFCWeaponMaterialHelper;
import mhfc.net.common.util.lib.MHFCReference;

public class GSDeadlySerpentBlade extends GreatswordClass {

	public GSDeadlySerpentBlade() {
		super(MHFCWeaponMaterialHelper.GSDeadlySerpentBlade);
		getWeaponDescription("Poison Element", 3);
		elementalType(true, false);
		setUnlocalizedName(MHFCReference.weapon_gs_deadlyserpentblade_name);
	}

}
