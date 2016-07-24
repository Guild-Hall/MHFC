package mhfc.net.common.ai.entity.nonboss.gargwa;

import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.actions.AIGeneralWander;
import mhfc.net.common.ai.general.provider.simple.IMoveParameterProvider;
import mhfc.net.common.entity.monster.EntityGargwa;
import net.minecraft.entity.Entity;

public class Wander extends AIGeneralWander<EntityGargwa> {

	private static final String ANIMATION = "mhfc:models/Gagua/GaguaWalk.mcanm";
	private static final int LAST_FRAME = 45;
	private static final float WEIGHT = 1F;

	private static final IMoveParameterProvider parameterProvider = new IMoveParameterProvider.MoveParameterAdapter(
			1f,
			0.4f);

	public Wander() {
		super(parameterProvider);
		setFrameAdvancer(new IFrameAdvancer.CountLoopAdvancer(0, 45, -1));
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
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
	public float getWeight(EntityGargwa entity, Entity target) {
		if(entity.worldObj.isDaytime()){
			return WEIGHT;
		}
		return DONT_SELECT;
	}

}
