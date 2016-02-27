package mhfc.net.common.ai.entity.deviljho;

import mhfc.net.common.ai.ActionAdapter;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;

public class DeviljhoLaunch extends ActionAdapter<EntityDeviljho> {
	private static final int LAST_FRAME = 50;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(92f, 62f, 8888f);
	private static final double MAX_DIST = 12f;
	private static final float WEIGHT = 6F;
	
	public DeviljhoLaunch() {
		setAnimation("mhfc:models/Deviljho/deviljholaunch.mcanm");
		setLastFrame(LAST_FRAME);
	}
	
	@Override
	public float getWeight() {
		EntityDeviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3 toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist > MAX_DIST) {
			return DONT_SELECT;
		}
		return WEIGHT;
	}
	
	@Override
	public void update() {
		EntityDeviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 28) {
			if(entity.getAttackTarget() == null) return;
			getEntity().playSound("mhfc:deviljho.bite", 1.0F, 1.0F);

			AIUtils.damageCollidingEntities(getEntity(), damageCalc);
			if (!entity.worldObj.isRemote)
			entity.getAttackTarget().motionY *= 6.33300712D;
		}
		
		if (isMoveForwardFrame(getCurrentFrame())) {
			EntityDeviljho e = getEntity();
			e.moveForward(1, false);
		}
	}
	
	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}

}
