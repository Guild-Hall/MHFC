package mhfc.net.common.eventhandler.entity;

import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.index.ResourceInterface;
import mhfc.net.common.item.armor.ArmorBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ResourceInterface.main_modid)
public class ModLivingEvent {

	public static final ModLivingEvent instance = new ModLivingEvent();

	/** All sort of event attributes should be added here, it will be the same in registering in EVENT.BUS **/


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
		ArmorBase armor = ArmorBase.instance;
		DamageSource damageSrc = event.getSource();
		double damage = event.getAmount();
		Entity ent = event.getEntity();
		if (ent != null && ent instanceof EntityPlayer) {
			if(damageSrc.getSourceOfDamage() instanceof EntityMHFCBase)
			if (ent.getArmorInventoryList() != null && ent.getArmorInventoryList() instanceof ArmorBase) {
				/** Refines the damage analyzer based from Monster Hunter Series but in a special case which
				 *  the default health is set to 20 for players. **/
					float newDamage = (float) Math.abs(((damage * 80) / (armor.getInitialDefenseValue() + 80)) * 0.20);
					event.setAmount(newDamage);
					//event.getEntity().attackEntityFrom(damageSrc, newDamage);
					System.out.println("Current AI Damage = " + damage + " Current New Damage = " + newDamage);
					}
				}
			}	

		

}
