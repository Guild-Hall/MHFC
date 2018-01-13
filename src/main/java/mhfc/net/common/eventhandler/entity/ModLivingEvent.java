package mhfc.net.common.eventhandler.entity;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ResourceInterface.main_modid)
public class ModLivingEvent {

	/** All sort of event attributes should be added here, it will be the same in registering in EVENT.BUS **/

	/** Shock Trap Event **/
	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (entity.getActivePotionEffect(MHFCPotionRegistry.getRegistry().stun) != null) {
			for (int i = 0; i < 15; i++) {

			}
		}
	}

	/** Pitfall Event ( W I P ) **/
	@SubscribeEvent
	public static void onEntityUpdate(LivingUpdateEvent event) {

		if (event == null || event.getEntityLiving() == null) {
			return;
		}
		PotionEffect activeEffect = event.getEntityLiving()
				.getActivePotionEffect(MHFCPotionRegistry.getRegistry().stun);
		if (activeEffect != null && activeEffect.getDuration() == 0) {
			event.getEntityLiving().removePotionEffect(MHFCPotionRegistry.getRegistry().stun);
		}
	}

	@SubscribeEvent
	public static void onDamageDealtToArmor(LivingHurtEvent event) {

		//TODO
		if (event == null || event.getEntityLiving() == null)
			return;
		DamageSource damage = DamageSource.causeMobDamage((EntityLivingBase) event.getEntity());
		if (damage != null) {

		}
	}

}
