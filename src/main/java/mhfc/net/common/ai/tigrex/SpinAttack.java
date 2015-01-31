package mhfc.net.common.ai.tigrex;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.entity.type.EntityWyvernPeaceful;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.stored.AnimationRegistry;

public class SpinAttack extends AttackAdapter<EntityTigrex> {
	private static final int MAX_FRAME = 30;

	private boolean finished = false;
	private Entity trgt;

	public SpinAttack() {
		setAnimation(AnimationRegistry.loadAnimation(new ResourceLocation(
				"mhfc:models/Tigrex/tailswipe.mcanm")));
	}

	@Override
	public float getWeight() {
		EntityLivingBase target = this.entity.getAttackTarget();
		if (target == null)
			return 0.0F;
		Vec3 pos = Vec3.createVectorHelper(this.entity.posX, this.entity.posY,
				this.entity.posZ);
		Vec3 entityToTarget = Vec3.createVectorHelper(target.posX, target.posY,
				target.posZ);
		entityToTarget = pos.subtract(entityToTarget);
		trgt = target;
		MHFCMain.logger.debug(MHFCMain.isClient() ? "CLIENT" : "SERVER");
		return (float) (7.0D - entityToTarget.lengthVector());

	}

	@Override
	public void beginExecution() {
		entity.getNavigator().noPath();
		finished = false;
	}

	@Override
	public void update() {
		if (trgt instanceof EntityPlayer) {
			trgt.attackEntityFrom(DamageSource.causeMobDamage(entity), 4F);
		} else if (trgt instanceof EntityWyvernHostile
				|| trgt instanceof EntityWyvernPeaceful) {
			trgt.attackEntityFrom(DamageSource.causeMobDamage(entity), 62F);
		} else {
			trgt.attackEntityFrom(DamageSource.causeMobDamage(entity),
					70F * 5 + 100);
		}
	}

	@Override
	public boolean shouldContinue() {
		return !finished;
	}

	@Override
	public void finishExecution() {}

	@Override
	public int getNextFrame(int frame) {
		if (frame > MAX_FRAME)
			finished = true;
		return super.getNextFrame(frame);
	}
}
