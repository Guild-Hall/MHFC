package mhfc.net.common.ai.entity.monsters.deviljho;

import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.SelectionUtils;
import mhfc.net.common.ai.general.actions.DamagingAction;
import mhfc.net.common.ai.general.provider.adapters.AnimationAdapter;
import mhfc.net.common.ai.general.provider.adapters.AttackAdapter;
import mhfc.net.common.ai.general.provider.adapters.DamageAdapter;
import mhfc.net.common.ai.general.provider.composite.IAttackProvider;
import mhfc.net.common.ai.general.provider.impl.IHasAttackProvider;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.CreatureAttributes;
import mhfc.net.common.entity.creature.Deviljho;
import mhfc.net.common.entity.projectile.ProjectileBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class Launch extends DamagingAction<Deviljho> implements IHasAttackProvider {

	private boolean thrown;

	public Launch() {}

	@Override
	protected float computeSelectionWeight() {
		Deviljho entity = this.getEntity();
		target = entity.getAttackTarget();
		if (SelectionUtils.isIdle(entity)) {
			return DONT_SELECT;
		}
		if (!SelectionUtils.isInDistance(0, 15, entity, target)) {
			return DONT_SELECT;
		}
		return 10F;
	}

	@Override
	protected void beginExecution() {
		super.beginExecution();
		thrown = false;
	}

	@Override
	public IAttackProvider getAttackProvider() {
		return new AttackAdapter(
				new AnimationAdapter(this, "mhfc:models/Deviljho/DeviljhoLaunch.mcanm", 50),
				new DamageAdapter(AIUtils.defaultDamageCalc(105F, 500F, 8888f)));
	}

	protected static double SPLIT_MULTIPLIER = 0.535;

	@Override
	public void onUpdate() {
		double HEIGHT_BLOCK = 0.50D;

		damageCollidingEntities();
		if (thrown) {
			return;
		}
		if (this.getCurrentFrame() >= 20) {
			if (getEntity().getAttackTarget() instanceof EntityPlayer) {
				CreatureAttributes.screenIntensity(getEntity(), 10, 40);
			}
		}
		Deviljho entity = this.getEntity();
		if (this.getCurrentFrame() == 28) {
			if (entity.getAttackTarget() == null) {
				return;
			}
			getEntity().playSound(MHFCSoundRegistry.getRegistry().deviljhoRockThrow, 2.0F, 1.0F);
			CreatureAttributes.mountVelocity(entity, 0, 1.4, 0);
		}
		if (this.getCurrentFrame() >= 35) {
			Vec3d look = entity.getLookVec();
			Vec3d vec_look_var = entity.getLookVec();
			// to the right and upward.
			Vec3d vec_positive_axis = vec_look_var.crossProduct(new Vec3d(0, 1, 0));

			for (int i = 0; i < 5; i++) {
				ProjectileBlock block = new ProjectileBlock(entity.world, entity);
				double xCo = look.x;
				double yCo = look.y + HEIGHT_BLOCK;
				double zCo = look.z;
				if (i == 0) {
					xCo += vec_positive_axis.x * SPLIT_MULTIPLIER;
					zCo += vec_positive_axis.z * SPLIT_MULTIPLIER;
				} else if (i == 2) {
					xCo -= vec_positive_axis.x * SPLIT_MULTIPLIER;
					zCo -= vec_positive_axis.z * SPLIT_MULTIPLIER;
				} else if (i == 3) {
					xCo += vec_positive_axis.x * SPLIT_MULTIPLIER * 0.3D;
					zCo += vec_positive_axis.z * SPLIT_MULTIPLIER * 0.3D;
				} else if (i == 4) {
					xCo -= vec_positive_axis.x * SPLIT_MULTIPLIER * 0.3D;
					zCo -= vec_positive_axis.z * SPLIT_MULTIPLIER * 0.3D;
				}

				block.shoot(xCo, yCo, zCo, 3.5f, 1.5f);
				entity.world.spawnEntity(block);
			}

			thrown = true;
		}
		super.onUpdate();
	}

}
