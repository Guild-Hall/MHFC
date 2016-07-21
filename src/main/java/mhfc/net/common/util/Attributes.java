package mhfc.net.common.util;

import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class Attributes {
	// @formatter:off
	public static final BaseAttribute IGNORE_ARMOUR_DAMAGE	= new RangedAttribute("mhfc.ignoreArmour", 0D, 0D, Double.MAX_VALUE).setShouldWatch(true);
	public static final BaseAttribute WEAPON_KNOCKBACK		= new RangedAttribute("mhfc.knockback", 0.4D, 0D, Double.MAX_VALUE).setShouldWatch(true);
	public static final BaseAttribute ATTACK_COOLDOWN 		= new RangedAttribute("mhfc.attackCooldown", 10d, 0D, Double.MAX_VALUE).setShouldWatch(true);
	public static final BaseAttribute RELOAD_TIME 			= new RangedAttribute("mhfc.reloadTime", 20d, 0D, Double.MAX_VALUE).setShouldWatch(true);
	public static final BaseAttribute WEAPON_REACH 			= new RangedAttribute("mhfc.reach", 3d, 0D, Double.MAX_VALUE).setShouldWatch(true);
	
	
}
