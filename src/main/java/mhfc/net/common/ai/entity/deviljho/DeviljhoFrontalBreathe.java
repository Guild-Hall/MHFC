package mhfc.net.common.ai.entity.deviljho;

import java.util.Random;

import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.projectile.EntityBreathe;
import net.minecraft.entity.EntityLivingBase;

public class DeviljhoFrontalBreathe extends AIAnimatedAction<EntityDeviljho> {

	private static final String SET_Animation = "mhfc:models/Deviljho/DeviljhoFrontalBreathe.mcanm";
	private static final int Set_Frame = 80;
	private static final double Set_MaxDistance = 6f;
	private static final float Set_Weight = 6F;

	public DeviljhoFrontalBreathe() {
		super(generateProvider());
	}

	private static IAnimatedActionProvider<EntityDeviljho> generateProvider() {
		IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(SET_Animation, Set_Frame);
		ISelectionPredicate<EntityDeviljho> selectionProvider = new ISelectionPredicate.DistanceAdapter<>(
				0,
				Set_MaxDistance);
		IWeightProvider<EntityDeviljho> weightProvider = new IWeightProvider.SimpleWeightAdapter<>(Set_Weight);
		return new AnimatedActionAdapter<EntityDeviljho>(animationProvider, selectionProvider, weightProvider);
	}

	@Override
	protected void update() {
		EntityDeviljho set_ENTITY = this.getEntity();
		EntityLivingBase set_TARGET = set_ENTITY.getAttackTarget();
		getEntity().playSound("mhfc:deviljho.dragonbreath", 1.0F, 1.0F);
		Random random = new Random();
		if (this.getCurrentFrame() == 40) {
			if (set_ENTITY.getAttackTarget() == null) {
				return;
			}

			for (int i = 0; i < 3; ++i) {
				EntityBreathe set_Breathe = new EntityBreathe(set_ENTITY.worldObj, set_ENTITY, true);
				set_Breathe.posY = set_ENTITY.posY + (double) (set_ENTITY.height / 2.0F) + 0.5D;
				set_ENTITY.worldObj.spawnEntityInWorld(set_Breathe);
			}
		}

	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}

}
