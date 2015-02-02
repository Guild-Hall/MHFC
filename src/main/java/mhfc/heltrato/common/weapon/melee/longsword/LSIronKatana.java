package mhfc.heltrato.common.weapon.melee.longsword;

import java.util.List;


public class LSIronKatana extends LongswordClass {


	public LSIronKatana(ToolMaterial getType) {
		super(getType);
		getWeaponDescription("No Element", 1);
		elementalType(false, false);
		setUnlocalizedName(lsLocal + 1);
	}


	
}
