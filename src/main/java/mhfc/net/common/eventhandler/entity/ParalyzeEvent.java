package mhfc.net.common.eventhandler.entity;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;

public class ParalyzeEvent {

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (entity.getActivePotionEffect(MHFCPotionRegistry.getRegistry().stun) != null) {
			for (int i = 0; i < 15; i++) {

			}
		}
	}
}
