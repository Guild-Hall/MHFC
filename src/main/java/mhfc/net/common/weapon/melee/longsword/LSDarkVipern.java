package mhfc.net.common.weapon.melee.longsword;

import java.util.List;

public class LSDarkVipern extends LongswordClass {


	public LSDarkVipern(ToolMaterial getType) {
		super(getType);
		getWeaponDescription("Poison Element", 2);
		elementalType(true, false);
		setUnlocalizedName(lsLocal + 2);
		amplified = 1;
	}




}
