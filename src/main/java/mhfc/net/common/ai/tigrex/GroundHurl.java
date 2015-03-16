package mhfc.net.common.ai.tigrex;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.Vec3;

public class GroundHurl extends AttackAdapter<EntityTigrex> {
	private static final float MIN_DIST = 10f;
	private static final int LAST_FRAME = 60;

	private boolean thrown;

	public GroundHurl() {
		setAnimation("mhfc:models/Tigrex/dirtthrow.mcanm");
		setLastFrame(LAST_FRAME);
	}

	@Override
	public float getWeight() {
		EntityTigrex tigrex = this.getEntity();
		target = tigrex.getAttackTarget();
		if (target == null)
			return DONT_SELECT;
		Vec3 toTarget = WorldHelper.getVectorToTarget(tigrex, target);
		double dist = toTarget.lengthVector();
		return (float) (dist - MIN_DIST);
	}

	@Override
	public void beginExecution() {
		super.beginExecution();
		thrown = false;
	}

	@Override
	public void update() {
		if (thrown)
			return;
		if (getRecentFrame() < 30)
			return;
		// TODO: throw actual rock-entities, what is their name again?
		MHFCMain.logger.info("Throwing rocks");
		thrown = true;
	}

}
