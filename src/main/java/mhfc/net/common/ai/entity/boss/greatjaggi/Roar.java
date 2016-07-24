package mhfc.net.common.ai.entity.boss.greatjaggi;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Roar extends AIGeneralRoar<EntityGreatJaggi> {

	private static final String ANIMATION = "mhfc:models/GreatJaggi/roar.mcanm";
	private static final int LAST_FRAME = 64;
	private static final String ROAR_SOUND = "mhfc:greatjaggi.roar";

	private static final IWeightProvider<EntityGreatJaggi> weight;

	public Roar() {}

	static {
		weight = new IWeightProvider.RandomWeightAdapter<>(1F);
	}

	@Override
	public void update() {
		super.update();
		EntityGreatJaggi entity = this.getEntity();
		target = entity.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(30.0f);
		}
	}

	@Override
	public boolean shouldStun(EntityLivingBase actor) {
		return false;
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
			IExecutableAction<? super EntityGreatJaggi> attack,
			EntityGreatJaggi actor,
			Entity target) {
		return true;
	}

	@Override
	public float getWeight(EntityGreatJaggi entity, Entity target) {
		return weight.getWeight(entity, target);
	}

	@Override
	public String getRoarSoundLocation() {
		return ROAR_SOUND;
	}
}
