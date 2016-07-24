package mhfc.net.common.ai.entity.boss.deviljho;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.projectile.EntityBreathe;
import net.minecraft.entity.Entity;

public class FrontalBreathe extends AIAnimatedAction<EntityDeviljho> {

	private static final String SET_Animation = "mhfc:models/Deviljho/DeviljhoFrontalBreathe.mcanm";
	private static final int Set_Frame = 80;
	private static final double Set_MaxDistance = 15f;
	private static final float Set_Weight = 6F;

	private static ISelectionPredicate<EntityDeviljho> selectionProvider;

	public FrontalBreathe() {}

	static {
		selectionProvider = new ISelectionPredicate.DistanceAdapter<>(0, Set_MaxDistance);
	}

	@Override
	protected void update() {
		EntityDeviljho set_ENTITY = this.getEntity();
		if(this.getCurrentFrame() == 18)
			set_ENTITY.playSound("mhfc:deviljho.dragonbreath", 2.0F, 1.0F);
		if (this.getCurrentFrame() == 40) {
			if (set_ENTITY.getAttackTarget() == null) {
				return;
			}

			for (int i = 0; i < 3; ++i) {
				EntityBreathe set_Breathe = new EntityBreathe(set_ENTITY.worldObj, set_ENTITY, true);
				set_ENTITY.worldObj.spawnEntityInWorld(set_Breathe);
			}
		}

	}

	@Override
	public String getAnimationLocation() {
		return SET_Animation;
	}

	@Override
	public int getAnimationLength() {
		return Set_Frame;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityDeviljho> attack,
			EntityDeviljho actor,
			Entity target) {
		return selectionProvider.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityDeviljho entity, Entity target) {
		return Set_Weight;
	}

}
