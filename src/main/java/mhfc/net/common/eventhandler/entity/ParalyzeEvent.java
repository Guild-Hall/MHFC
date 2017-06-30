package mhfc.net.common.eventhandler.entity;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ResourceInterface.main_modid)
public class ParalyzeEvent {

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.getActivePotionEffect(MHFCPotionRegistry.getRegistry().stun) != null) {
			for (int i = 0; i < 15; i++) {

			}
		}
	}

}
