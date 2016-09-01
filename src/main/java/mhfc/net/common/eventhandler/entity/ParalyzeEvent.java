package mhfc.net.common.eventhandler.entity;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ParalyzeEvent {

	@SubscribeEvent
	public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.getActivePotionEffect(MHFCPotionRegistry.getRegistry().stun) != null) {
			for (int i = 0; i < 15; i++) {

			}
		}
	}
}
