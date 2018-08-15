package mhfc.net.common.potion;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionKirinBless extends Potion {
	private static final ResourceLocation texture = new ResourceLocation(ResourceInterface.potion_kirinbless_tex);

	public PotionKirinBless() {
		super(false, 591932);
		setPotionName(ResourceInterface.potion_kirinbless_name);
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
		return ResourceInterface.potion_kirinbless_iconindex;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration >= 1;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE));
		if (entity.getHealth() <= 3F) {
			entity.heal(2F);
		}
	}

}
