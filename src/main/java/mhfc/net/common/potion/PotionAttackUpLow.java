package mhfc.net.common.potion;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;

public class PotionAttackUpLow extends Potion {

	public PotionAttackUpLow(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
		setPotionName(MHFCReference.potion_attackup_name);
		setIconIndex(1, 0);
		func_111184_a(SharedMonsterAttributes.attackDamage, MHFCReference.potion_attackup_uuid, 2D, 1);
	}

}
