package mhfc.net.common.eventhandler.entity;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PitfallEvent {
	public final static PitfallEvent instance = new PitfallEvent();

	// TODO
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {

		if (event == null || event.getEntityLiving() == null) {
			return;
		}
		PotionEffect activeEffect = event.getEntityLiving()
				.getActivePotionEffect(MHFCPotionRegistry.getRegistry().stun);
		if (activeEffect != null && activeEffect.getDuration() == 0) {
			event.getEntityLiving().removePotionEffect(MHFCPotionRegistry.getRegistry().stun);
		}
	}

}
