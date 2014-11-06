package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.stored.AnimationRegistry;

public class SpinAttack extends AttackAdapter<EntityTigrex> {
	private static final int MAX_FRAME = 30;

	private boolean finished = false;

	public SpinAttack() {
		setAnimation(AnimationRegistry.loadAnimation(new ResourceLocation(
				"mhfc:models/Tigrex/tailswipe.mcanm")));
	}

	@Override
	public float getWeight() {
		EntityLivingBase target = this.entity.getAttackTarget();
		if (target == null)
			return 0.0F;
		Vec3 pos = this.entity.getPosition(1.0F);
		Vec3 entityToTarget = target.getPosition(1.0F);
		entityToTarget = pos.subtract(entityToTarget);
		return (float) (15.0D - entityToTarget.lengthVector());
	}

	@Override
	public void beginExecution() {
		entity.getNavigator().noPath();
		finished = false;
	}

	@Override
	public void update() {}

	@Override
	public boolean shouldContinue() {
		return !finished;
	}

	@Override
	public void finishExecution() {}

	@Override
	public int getNextFrame(int frame) {
		if (true)
			return 50;
		if (frame == MAX_FRAME)
			finished = true;
		return super.getNextFrame(frame);
	}
}
