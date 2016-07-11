package mhfc.net.common.eventhandler.entity;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class PitfallEvent {
	public final static PitfallEvent instance = new PitfallEvent();

	// TODO
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {

		if (event == null || event.entityLiving == null) {
			return;
		}
		PotionEffect activeEffect = event.entityLiving.getActivePotionEffect(MHFCPotionRegistry.getRegistry().stun);
		if (activeEffect != null && activeEffect.getDuration() == 0) {
			event.entityLiving.removePotionEffect(MHFCPotionRegistry.getRegistry().stun.id);
		}
	}

}
