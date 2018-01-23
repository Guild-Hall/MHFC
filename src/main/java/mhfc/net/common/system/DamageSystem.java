package mhfc.net.common.system;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSystem extends EntityDamageSource {

	/**
	 * A renowned delayed plan. Im going to try to tackle on this as it was the new.
	 * 
	 * @Heltrato
	 */

	public Entity attacker = null;

	public DamageSystem(String name, Entity damagingEntity, Entity attacker) {
		super(name, damagingEntity);
	}

	/** This makes repel the other mods and may realize youtuber that our entites are not for mob battles. **/
	public static DamageSource antimod = new DamageSource("anti").setDamageBypassesArmor().setDamageIsAbsolute();

	@Override
	public Entity getEntity() {
		return this.attacker;
	}

}
