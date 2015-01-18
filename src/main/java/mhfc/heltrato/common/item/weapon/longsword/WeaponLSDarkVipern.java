package mhfc.heltrato.common.item.weapon.longsword;

import java.util.List;

public class WeaponLSDarkVipern extends LongswordClass {


	public WeaponLSDarkVipern(ToolMaterial getType) {
		super(getType);
		getWeaponDescription("Poison Element", 2);
		elementalType(true, false);
		setUnlocalizedName(lsLocal + 2);
		amplified = 1;
	}




}
