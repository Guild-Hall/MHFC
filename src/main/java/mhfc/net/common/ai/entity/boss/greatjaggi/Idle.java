package mhfc.net.common.ai.entity.boss.greatjaggi;

import mhfc.net.common.ai.general.actions.AIGeneralIdle;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import net.minecraft.entity.Entity;

public class Idle extends AIGeneralIdle<EntityGreatJaggi>  {
	
	private static int ANIM_FRAME = 64;
	public static String ANIM_LOC = "mhfc:models/GreatJaggi/GreatJaggiIdle.mcanm";
	public static final float ANIM_WEIGHT = 6;

	@Override
	public String getAnimationLocation() {
		return ANIM_LOC;
	}

	@Override
	public int getAnimationLength() {
		return ANIM_FRAME;
	}

	@Override
	public float getWeight(EntityGreatJaggi entity, Entity target) {
		return ANIM_WEIGHT;
	}
	
	@Override
	public void update() {
		EntityGreatJaggi entity = this.getEntity();
		if(getCurrentFrame() == 30){
			entity.playLivingSound();
		}
	}
	
	

}
