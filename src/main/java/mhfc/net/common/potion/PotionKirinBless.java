package mhfc.net.common.potion;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionKirinBless extends Potion {
	private static final ResourceLocation texture = new ResourceLocation(MHFCReference.potion_kirinbless_tex);

	public PotionKirinBless() {
		super(false, 591932);
		setPotionName(MHFCReference.potion_kirinbless_name);
		setIconIndex(1, 0);
		registerPotionAttributeModifier(
				SharedMonsterAttributes.ATTACK_DAMAGE,
				MHFCPotionRegistry.potion_kirinbless_uuid,
				1.1D,
				1);
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
