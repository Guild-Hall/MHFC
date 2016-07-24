package mhfc.net.common.ai.entity.boss.lagiacrus;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Roar extends AIGeneralRoar<EntityLagiacrus> {

	private static final String ANIMATION = "mhfc:models/Lagiacrus/LagiacrusRoar.mcanm";
	private static final int LAST_FRAME = 95;
	private static final String ROAR_SOUND = "mhfc:lagiacrus.roar";

	private static final IWeightProvider<EntityLagiacrus> weight;

	public Roar() {}

	static {
		weight = new IWeightProvider.RandomWeightAdapter<>(1F);
	}

	@Override
	public void update() {
		super.update();
		EntityLagiacrus entity = this.getEntity();
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
			IExecutableAction<? super EntityLagiacrus> attack,
			EntityLagiacrus actor,
			Entity target) {
		return true;
	}

	@Override
	public float getWeight(EntityLagiacrus entity, Entity target) {
		return weight.getWeight(entity, target);
	}

	@Override
	public String getRoarSoundLocation() {
		return ROAR_SOUND;
	}
}
