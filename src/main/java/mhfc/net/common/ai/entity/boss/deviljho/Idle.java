package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.Entity;

public class Idle extends AIGeneralIdle<EntityDeviljho> {

	private static final int LAST_FRAME = 100;
	public static final String ANIMATION = "mhfc:models/Deviljho/DeviljhoIdle.mcanm";
	public static final float WEIGHT = 6;

	public Idle() {}

	@Override
	public void update() {
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 50) {
			entity.playLivingSound();
			// just a copy from roar the update method. nothing else
		}
	}

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return LAST_FRAME;
	}

	@Override
	public float getWeight(EntityDeviljho entity, Entity target) {
		return WEIGHT;
	}
}
