package mhfc.net.common.potion;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.util.Libraries;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class PotionAttackUpLow extends Potion {

	public PotionAttackUpLow(boolean isBad, int liquidColor) {
		super(isBad, liquidColor);
		setPotionName(Libraries.potion_attackup_name);
		setIconIndex(1, 0);
		registerPotionAttributeModifier(
				SharedMonsterAttributes.ATTACK_DAMAGE,
				MHFCPotionRegistry.potion_attackup_uuid,
				2D,
				1);
	}

}
