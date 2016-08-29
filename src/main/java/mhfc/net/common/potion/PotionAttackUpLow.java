package mhfc.net.common.potion;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class PotionAttackUpLow extends Potion {

	public PotionAttackUpLow(boolean isBad, int liquidColor) {
		super(isBad, liquidColor);
		setPotionName(MHFCReference.potion_attackup_name);
		setIconIndex(1, 0);
		registerPotionAttributeModifier(
				SharedMonsterAttributes.ATTACK_DAMAGE,
				MHFCReference.potion_attackup_uuid,
				2D,
				1);
	}

}
