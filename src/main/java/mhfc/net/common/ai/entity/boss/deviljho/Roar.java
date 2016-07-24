package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Roar extends AIGeneralRoar<EntityDeviljho> {

	private static final String ANIMATION = "mhfc:models/Deviljho/DeviljhoRoar.mcanm";
	private static final int LAST_FRAME = 70;
	private static final String ROAR_SOUND = "mhfc:deviljho.roar";

	private static final IWeightProvider<EntityDeviljho> weight;

	static {
		weight = new IWeightProvider.RandomWeightAdapter<>(2F);
	}

	public Roar() {}

	@Override
	public void update() {
		super.update();
		EntityDeviljho entity = this.getEntity();
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
			IExecutableAction<? super EntityDeviljho> attack,
			EntityDeviljho actor,
			Entity target) {
		return true;
	}

	@Override
	public float getWeight(EntityDeviljho entity, Entity target) {
		return weight.getWeight(entity, target);
	}

	@Override
	public String getRoarSoundLocation() {
		return ROAR_SOUND;
	}
}
