package mhfc.net.common.eventhandler.potion;

import mhfc.net.common.core.registry.MHFCRegPotion;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PotionPitfallEventHandler {
	public final static PotionPitfallEventHandler instance = new PotionPitfallEventHandler();

	// TODO
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {

		if (event.entityLiving.getActivePotionEffect(
				MHFCRegPotion.mhfcpotionshock).getDuration() == 0) {
			event.entityLiving
					.removePotionEffect(MHFCRegPotion.mhfcpotionshock.id);
			return;
		}

	}

}
