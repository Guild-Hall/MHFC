package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Roar extends AIGeneralRoar<EntityNargacuga> {

	private static final String ANIMATION = "mhfc:models/Nargacuga/Roar.mcanm";
	private static final int LAST_FRAME = 71;
	private static final String ROAR_SOUND = "mhfc:narga.roar";

	private static final IWeightProvider<EntityNargacuga> weight;

	static {
		weight = new IWeightProvider.RandomWeightAdapter<>(1F);
	}

	public Roar() {}

	@Override
	protected void update() {
		super.update();
		EntityNargacuga entity = getEntity();
		if (this.getCurrentFrame() <= 10) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(7.0f);
		}
	}

	@Override
	public boolean shouldStun(EntityLivingBase actor) {
		return true;
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
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityNargacuga> attack,
			EntityNargacuga actor,
			Entity target) {
		return true;
	}

	@Override
	public float getWeight(EntityNargacuga entity, Entity target) {
		return weight.getWeight(entity, target);
	}

	@Override
	public String getRoarSoundLocation() {
		return ROAR_SOUND;
	}
}
