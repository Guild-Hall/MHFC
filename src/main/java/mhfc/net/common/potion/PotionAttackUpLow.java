package mhfc.net.common.potion;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class PotionAttackUpLow extends Potion {

	protected PotionAttackUpLow(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
		setPotionName("potion.attackuplow");
		setIconIndex(1,0);
		func_111184_a(SharedMonsterAttributes.attackDamage, "6a80c830-745d-4edd-8a17-e580f813bf20", 0.8D, 1);
	}

}
