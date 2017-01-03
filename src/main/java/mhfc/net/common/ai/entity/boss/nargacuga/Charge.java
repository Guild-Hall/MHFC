package mhfc.net.common.ai.entity.boss.nargacuga;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.AIUtils;
import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.AIUtils.IDamageCalculator;
import mhfc.net.common.ai.general.IFrameAdvancer.SwitchLoopAdvancer;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class Charge extends AIAnimatedAction<EntityNargacuga> {

	private static final String ANIMATION = "mhfc:models/Nargacuga/Charge.mcanm";
	private static final int ANIMATION_LENGTH = 80;
	private static final float MAX_ANGLE = 40;
	private static final float MIN_DISTANCE = 0;
	private static final float MAX_DISTANCE = 50;
	private static final float WEIGHT = 25F;
	private static final int LOOP_START = 21;
	private static final int LOOP_END = 180;
	private static final IDamageCalculator damageCalc = AIUtils.defaultDamageCalc(280f, 50F, 99999F);

	private SwitchLoopAdvancer frameAdvancer;
	private static final ISelectionPredicate<EntityNargacuga> selectionPredicate;

	static {
		selectionPredicate = new ISelectionPredicate.SelectionAdapter<>(
				-MAX_ANGLE,
				MAX_ANGLE,
				MIN_DISTANCE,
				MAX_DISTANCE);
	}

	public Charge() {
		this.frameAdvancer = new IFrameAdvancer.SwitchLoopAdvancer(LOOP_START, LOOP_END);
		setFrameAdvancer(frameAdvancer);
	}

	@Override
	protected void update() {
		EntityNargacuga nargacuga = getEntity();
		EntityLivingBase target = nargacuga.getAttackTarget();
		if(this.getCurrentFrame() == 5)
		nargacuga.playSound("narga.charge", 2.0F, 1.0F);
		Vec3 distanceVec = WorldHelper.getVectorToTarget(nargacuga, target);
		nargacuga.moveForward(1.7, true);
		if (distanceVec.lengthVector() < MIN_DISTANCE) {
			frameAdvancer.setLoopActive(false);
		}
	}

	@Override
	public String getAnimationLocation() {
		return ANIMATION;
	}

	@Override
	public int getAnimationLength() {
		return ANIMATION_LENGTH;
	}

	@Override
	public boolean shouldSelectAttack(
			IExecutableAction<? super EntityNargacuga> attack,
			EntityNargacuga actor,
			Entity target) {
		return selectionPredicate.shouldSelectAttack(attack, actor, target);
	}

	@Override
	public float getWeight(EntityNargacuga entity, Entity target) {
		return WEIGHT;
	}

}
