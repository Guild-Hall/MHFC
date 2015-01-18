package mhfc.heltrato.common.util;

import java.util.UUID;

import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public class Attributes {
	
	public static final BaseAttribute	ATTACK_SPEED			= new RangedAttribute("mhfc.attackspeed", 0D, -Double.MAX_VALUE, Double.MAX_VALUE);
}
