package mhfc.net.common.potion;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionParalyze extends Potion {
	
	public Random random;
	public PotionParalyze(int par1, boolean isBad, int color) {
		super(par1, isBad, color);
		setPotionName(MHFCReference.potion_paralyze_name);
		setIconIndex(1, 0);
		random = new Random();
		func_111184_a(SharedMonsterAttributes.movementSpeed,
				MHFCReference.potion_paralyze_uuid, -1D, 1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(
				MHFCReference.potion_paralyze_tex));
		return MHFCReference.potion_paralyze_iconindex;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return duration >= 1;
	}

	@Override
	public void performEffect(EntityLivingBase par1, int par2) {
		for (int i = 0; i < 8; i++) {
		}}}

