package mhfc.net.common.potion;

import java.util.Random;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionParalyze extends Potion {

	public Random random;

	public PotionParalyze() {
		super(true, 999999);
		setPotionName(MHFCReference.potion_paralyze_name);
		setIconIndex(1, 0);
		random = new Random();
		registerPotionAttributeModifier(
				SharedMonsterAttributes.MOVEMENT_SPEED,
				MHFCReference.potion_paralyze_uuid,
				-1D,
				1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(MHFCReference.potion_paralyze_tex));
		return MHFCReference.potion_paralyze_iconindex;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration >= 1;
	}

	@Override
	public void performEffect(EntityLivingBase par1, int par2) {
		for (int i = 0; i < 8; i++) {}
	}
}
