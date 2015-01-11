package mhfc.heltrato.common.eventhandler.potion;

import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mhfc.heltrato.common.core.registry.MHFCRegPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class PotionPitfallEventHandler {
	
	
	//TODO
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event){
		 
		
		if(event.entityLiving.getActivePotionEffect(MHFCRegPotion.mhfcpotionshock).getDuration() == 0){
			event.entityLiving.removePotionEffect(MHFCRegPotion.mhfcpotionshock.id);
			return;
		}
		
	}
	

}
