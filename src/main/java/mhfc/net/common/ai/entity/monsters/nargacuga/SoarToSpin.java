package mhfc.net.common.ai.entity.monsters.nargacuga;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.JumpAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.ConstantAirTimeAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpAdapter;
import mhfc.net.common.ai.general.provider.adapters.JumpTimingAdapter;
import mhfc.net.common.ai.general.provider.composite.IJumpProvider;
import mhfc.net.common.ai.general.provider.impl.IHasJumpProvider;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

public class SoarToSpin extends JumpAction<EntityNargacuga> implements IHasJumpProvider<EntityNargacuga> {
	
	public SoarToSpin() {}
	

	
	@Override
	protected void finishExecution() {
		super.finishExecution();
	}

	@Override
	protected float computeSelectionWeight() {
		EntityNargacuga e = this.getEntity();
		target = e.getAttackTarget();
		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(e, target);
		double dist = toTarget.lengthVector();
		if (dist > 15F) {
			return DONT_SELECT;
		}
		return 320f;
	}

	@Override
	public IJumpProvider<EntityNargacuga> getJumpProvider() {
		return new JumpAdapter<EntityNargacuga>(
				new AnimationAdapter(this, "mhfc:models/Nargacuga/soartospin.mcanm", 75),
				new DamageAdapter(AIUtils.defaultDamageCalc(120F, 300, 888880)),
				new ConstantAirTimeAdapter<EntityNargacuga>(
						12,
						entity -> entity.getLookVec().addVector(entity.posX, entity.posY, entity.posZ)),
				new JumpTimingAdapter<EntityNargacuga>(19, 0, 0));
	}

}
