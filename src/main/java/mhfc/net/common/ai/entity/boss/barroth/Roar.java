package mhfc.net.common.ai.entity.boss.barroth;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityBarroth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Roar extends AIGeneralRoar<EntityBarroth> {

	private static final String ANIMATION = "mhfc:models/Barroth/BarrothRoar.mcanm";
	private static final int LAST_FRAME = 105;
	private static final String ROAR_SOUND = "mhfc:barroth.roar";

	private static final IWeightProvider<EntityBarroth> weight;

	static {
		weight = new IWeightProvider.RandomWeightAdapter<>(1F);
	}

	public Roar() {}

	@Override
	public void update() {
		super.update();
		EntityBarroth entity = this.getEntity();
		target = entity.getAttackTarget();
		if (this.getCurrentFrame() >= 18 && this.getCurrentFrame() <= 22) {
			entity.getTurnHelper().updateTargetPoint(target);
			entity.getTurnHelper().updateTurnSpeed(30.0f);
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
			IExecutableAction<? super EntityBarroth> attack,
			EntityBarroth actor,
			Entity target) {
		return true;
	}

	@Override
	public float getWeight(EntityBarroth entity, Entity target) {
		return weight.getWeight(entity, target);
	}

	@Override
	public String getRoarSoundLocation() {
		return ROAR_SOUND;
	}
}
