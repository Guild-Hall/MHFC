package mhfc.heltrato.common.item.weapon.longsword;

import java.util.List;

import mhfc.heltrato.common.item.weapon.type.LongswordClass;


public class WeaponLSIronKatana extends LongswordClass {


	public WeaponLSIronKatana(ToolMaterial getType) {
		super(getType);
		getWeaponDescription("No Element", 1);
		elementalType(false, false);
		setUnlocalizedName(lsLocal + 1);
	}


	
}
