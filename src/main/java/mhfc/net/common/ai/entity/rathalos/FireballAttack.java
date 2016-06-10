package mhfc.net.common.ai.entity.rathalos;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
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

	private static ISelectionPredicate<EntityRathalos> selectionProvider;

	public FireballAttack() {}

	static {
		selectionProvider = new ISelectionPredicate.SelectionAdapter<EntityRathalos>(
				-ANGLE,
				ANGLE,
				MIN_DISTANCE,
				MAX_DISTANCE) {
			@Override
			public boolean shouldSelectAttack(
					IExecutableAction<? super EntityRathalos> attack,
					EntityRathalos actor,
					Entity target) {
				return actor.getStance() == Stances.GROUND && super.shouldSelectAttack(attack, actor, target);
			}
		};
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

	@Override
	public String getAnimationLocation() {
		return ANIMATION_LOCATION;
	}

	@Override
	public int getAnimationLength() {
		return LENGTH;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityRathalos> attack,
			EntityRathalos actor,
			Entity target) {
		return selectionProvider.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityRathalos entity, Entity target) {
		return WEIGHT;
	}

}
