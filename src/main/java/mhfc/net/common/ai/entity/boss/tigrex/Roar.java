package mhfc.net.common.ai.entity.boss.tigrex;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIGeneralRoar;
import mhfc.net.common.ai.general.provider.simple.IRoarSoundProvider;
import mhfc.net.common.ai.general.provider.simple.IWeightProvider;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class Roar extends AIGeneralRoar<EntityTigrex> {

	private static final String ANIMATION = "mhfc:models/Tigrex/rawr.mcanm";
	private static final int LAST_FRAME = 70;

	private static final IWeightProvider<EntityTigrex> weight;
	private static final IRoarSoundProvider roar;

	static {
		weight = new IWeightProvider.RandomWeightAdapter<>(0F);
		roar = new IRoarSoundProvider.RoarSoundAdapter("mhfc:tigrex.roar");
	}

	protected Vec3 targetPoint;

	public Roar() {}

	@Override
	public void beginExecution() {
		super.beginExecution();
		Entity target = getEntity().getAttackTarget();
		if (target != null) {
			targetPoint = WorldHelper.getEntityPositionVector(target);
		}
	}

	@Override
	public void update() {
		super.update();
		EntityTigrex entity = this.getEntity();
		if (this.getCurrentFrame() <= 10) {
			entity.getTurnHelper().updateTargetPoint(targetPoint);
			entity.getTurnHelper().updateTurnSpeed(6.0f);
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
			IExecutableAction<? super EntityTigrex> attack,
			EntityTigrex actor,
			Entity target) {
		return true;
	}

	@Override
	public float getWeight(EntityTigrex entity, Entity target) {
		return weight.getWeight(entity, target);
	}

	@Override
	public String getRoarSoundLocation() {
		return roar.getRoarSoundLocation();
	}
}
