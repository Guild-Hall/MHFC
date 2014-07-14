package mhfc.heltrato.common.potion;

import mhfc.heltrato.common.core.registry.MHFCRegPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PotionParalyze extends Potion {

	public PotionParalyze(int par1, boolean isBad, int color) {
		super(par1, isBad, color);
		setPotionName("potion.paralyze");
		setIconIndex(1, 0);
		func_111184_a(SharedMonsterAttributes.movementSpeed, "6a80c830-745d-4edd-8a17-e580f813bf20", -1D, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getStatusIconIndex()
	{
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("mhfc:textures/potion/mhfcpotion.png"));
		return 1;
	}
	
	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return duration >= 1;
	}
	
	@Override
	public void performEffect(EntityLivingBase par1, int par2){
		
		if(par1.getHealth() > 1.0F){
			par1.attackEntityFrom(DamageSource.magic, 2f);
		}
	}
	
	

}
