package mhfc.net.common.potion;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class PotionParalyze extends Potion {

	public Random random;

	public PotionParalyze() {
		super(true, 999999);
		setPotionName(ResourceInterface.potion_paralyze_name);
		setIconIndex(1, 0);
		random = new Random();
		registerPotionAttributeModifier(
				SharedMonsterAttributes.MOVEMENT_SPEED,
				MHFCPotionRegistry.potion_paralyze_uuid,
				-1D,
				1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(ResourceInterface.potion_paralyze_tex));
		return ResourceInterface.potion_paralyze_iconindex;
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
