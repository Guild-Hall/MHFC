package mhfc.net.common.potion;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class PotionAttackUpLow extends Potion {

	public PotionAttackUpLow(boolean isBad, int liquidColor) {
		super(isBad, liquidColor);
		setPotionName(ResourceInterface.potion_attackup_name);
		setIconIndex(1, 0);
		registerPotionAttributeModifier(
				SharedMonsterAttributes.ATTACK_DAMAGE,
				MHFCPotionRegistry.potion_attackup_uuid,
				2.2D,
				1);
	}

}
