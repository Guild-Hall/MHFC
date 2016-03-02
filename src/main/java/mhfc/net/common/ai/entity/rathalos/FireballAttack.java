package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.MHFCMain;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.IAnimationProvider;
import mhfc.net.common.ai.general.provider.ISelectionPredicate;
import mhfc.net.common.ai.general.provider.IWeightProvider;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.entity.monster.EntityRathalos.Stances;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class FireballAttack extends AIAnimatedAction<EntityRathalos> {

	public static final String ANIMATION_LOCATION = "mhfc:models/Rathalos/RathalosFireBlast.mcanm";
	public static final int LENGTH = 60;
	public static final int SPIT_FRAME = 25;
	public static final float ANGLE = 10;
	public static final double MIN_DISTANCE = 1;
	public static final double MAX_DISTANCE = 50;
	public static final float WEIGHT = 4;

	public FireballAttack() {
		super(generateProvider());
	}

	private static IAnimatedActionProvider<EntityRathalos> generateProvider() {
		IAnimationProvider animationProvider = new IAnimationProvider.AnimationAdapter(ANIMATION_LOCATION, LENGTH);
		ISelectionPredicate<EntityRathalos> selectionProvider = new ISelectionPredicate.SelectionAdapter<EntityRathalos>(
				-ANGLE,
				ANGLE,
				MIN_DISTANCE,
				MAX_DISTANCE) {
			@Override
			public boolean shouldSelectAttack(
					IExecutableAction<? super EntityRathalos> attack,
					EntityRathalos actor,
					Entity target) {
				MHFCMain.logger.debug("Actor {} , Target {}", actor, target);
				MHFCMain.logger.debug("Fireball available {}", actor.getStance() == Stances.GROUND);
				return actor.getStance() == Stances.GROUND && super.shouldSelectAttack(attack, actor, target);
			}
		};
		IWeightProvider<EntityRathalos> weightProvider = new IWeightProvider.SimpleWeightAdapter<>(WEIGHT);
		return new AnimatedActionAdapter<EntityRathalos>(animationProvider, selectionProvider, weightProvider);
	}

	@Override
	protected void update() {
		EntityRathalos rathalos = getEntity();
		if (getCurrentFrame() == 25 && rathalos.worldObj.isRemote) {
			Vec3 lookVec = rathalos.getLookVec();
			EntityRathalosFireball fireball = new EntityRathalosFireball(rathalos.worldObj, rathalos);
			fireball.moveEntity(0, -0.8f, 0);
			fireball.setThrowableHeading(lookVec.xCoord, lookVec.yCoord, lookVec.zCoord, 1.0f, 0);
			rathalos.worldObj.spawnEntityInWorld(fireball);
		}
	}

}
