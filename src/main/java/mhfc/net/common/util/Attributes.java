package mhfc.net.common.util;

import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class Attributes {
	public static final BaseAttribute	IGNORE_ARMOUR_DAMAGE	= new RangedAttribute("mhfc.ignoreArmour", 0D, 0D, Double.MAX_VALUE);
	public static final BaseAttribute	WEAPON_KNOCKBACK		= new RangedAttribute("mhfc.knockback", 0.4D, 0D, Double.MAX_VALUE);
	public static final BaseAttribute	ATTACK_SPEED			= new RangedAttribute("mhfc.attackSpeed", 0D, -Double.MAX_VALUE, Double.MAX_VALUE);
	public static final BaseAttribute	RELOAD_TIME				= new RangedAttribute("mhfc.reloadTime", 0D, 0D, Double.MAX_VALUE);
	public static final BaseAttribute	WEAPON_REACH			= new RangedAttribute("mhfc.reach", 0D, 0D, Double.MAX_VALUE);
}
