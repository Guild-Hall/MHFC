package mhfc.net.common.potion;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionKirinBless extends Potion {
	private static final ResourceLocation texture = new ResourceLocation(
			MHFCReference.potion_kirinbless_tex);
	public PotionKirinBless(int par1, boolean par2, int par3) {
		super(par1, par2, par3);
		setPotionName(MHFCReference.potion_kirinbless_name);
		setIconIndex(1, 0);
		func_111184_a(SharedMonsterAttributes.attackDamage,
				MHFCReference.potion_kirinbless_uuid, 1.1D, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		return MHFCReference.potion_kirinbless_iconindex;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration >= 1;
	}

	@Override
	public void performEffect(EntityLivingBase par1, int par2) {
		par1.fireResistance = 2;
		if (par1.getHealth() >= 3F) {
			par1.heal(2F);
		}
	}

}
