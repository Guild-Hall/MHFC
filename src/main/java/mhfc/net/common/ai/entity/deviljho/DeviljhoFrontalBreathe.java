package mhfc.net.common.ai.entity.deviljho;

import java.util.Random;

import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityDeviljho;
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
		if (this.getCurrentFrame() == 30) {
			if (set_ENTITY.getAttackTarget() == null) {
				return;
			}
			double posX = set_ENTITY.posX + set_TARGET.posX * 1.0D;
			double posY = set_ENTITY.posY + set_TARGET.posY * 1.0D;
			double posZ = set_ENTITY.posZ + set_TARGET.posZ * 1.0D;
			double X = set_TARGET.posX * 0.1D;
			double Y = set_TARGET.posZ * 0.1D;
			double Z = set_TARGET.posY * 0.1D;
			for (int i = 0; i < 100; i++) {
				for (int z = 0; z < 10; z++) {
					set_ENTITY.worldObj.spawnParticle(
							"lava",
							posX + X * 1.1D,
							posY + Y * 1.1D,
							posZ + Z * 1.1D,
							random.nextInt(10) / 10 - 0.2D,
							random.nextInt(10) / 10 - 0.2D,
							random.nextInt(10) / 10 - 0.2D);
				}
				set_ENTITY.worldObj.spawnParticle(
						"lava",
						posX + X * 1.1D,
						posY + Y * 1.1D,
						posZ + Z * 1.1D,
						random.nextInt(10) / 10 - 0.2D,
						random.nextInt(10) / 10 - 0.2D,
						random.nextInt(10) / 10 - 0.2D);
			}
		}

	}

	private boolean isMoveForwardFrame(int frame) {
		return (frame > 20 && frame < 30);
	}

}
