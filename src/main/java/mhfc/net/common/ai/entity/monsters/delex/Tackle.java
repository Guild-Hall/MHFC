/**
 *
 */
package mhfc.net.common.ai.entity.monsters.delex;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.entity.creature.Delex;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.util.math.Vec3d;

/**
 * @author WorldSEnder
 *
 */
public class Tackle extends DamagingAction<Delex> implements IHasAttackProvider {



	public Tackle() {}

	@Override
	protected float computeSelectionWeight() {
		Delex entity = this.getEntity();
		target = entity.getAttackTarget();


		if (target == null) {
			return DONT_SELECT;
		}
		Vec3d toTarget = WorldHelper.getVectorToTarget(entity, target);
		double dist = toTarget.lengthVector();
		if (dist >= entity.getDistanceToEntity(target) + 5) {
			return DONT_SELECT;
		}
		return 5F;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/delex/tackle.mcanm", 25),
				new DamageAdapter(AIUtils.defaultDamageCalc(25f, 50F, 9999999f)));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		damageCollidingEntities();
		if (isMoveForwardFrame(getCurrentFrame())) {
			Delex ent = getEntity();
			ent.moveForward(1, true);
		}

	}

	private static boolean isMoveForwardFrame(int frame) {
		return (frame > 14 && frame < 20);
	}
}
