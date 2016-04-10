package mhfc.net.common.ai.entity.nargacuga;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.general.IFrameAdvancer;
import mhfc.net.common.ai.general.IFrameAdvancer.SwitchLoopAdvancer;
import mhfc.net.common.ai.general.actions.AIAnimatedAction;
import mhfc.net.common.ai.general.provider.simple.ISelectionPredicate;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.util.world.WorldHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class NargacugaCharge extends AIAnimatedAction<EntityNargacuga> {

	private static final String ANIMATION = "mhfc:models/Nargacuga/Charge.mcanm";
	private static final int ANIMATION_LENGTH = 70;
	private static final float MAX_ANGLE = 20;
	private static final float MIN_DISTANCE = 10;
	private static final float MAX_DISTANCE = 100;
	private static final float WEIGHT = 5;
	private static final int RUN_START = 30;
	private static final int LOOP_START = 46;
	private static final int LOOP_END = 58;

	private static final float TURN_SPEED = 1;
	private static final float RUSH_SPEED = 1.7f;

	private SwitchLoopAdvancer frameAdvancer;
	boolean isRushing;
	private static final ISelectionPredicate<EntityNargacuga> selectionPredicate;

	static {
		selectionPredicate = new ISelectionPredicate.SelectionAdapter<>(
				-MAX_ANGLE,
				MAX_ANGLE,
				MIN_DISTANCE,
				MAX_DISTANCE);
	}

	public NargacugaCharge() {
		this.frameAdvancer = new IFrameAdvancer.SwitchLoopAdvancer(LOOP_START, LOOP_END);
		setFrameAdvancer(frameAdvancer);
	}

	@Override
	protected void beginExecution() {
		isRushing = true;
		super.beginExecution();
	}

	@Override
	protected void update() {
		EntityNargacuga nargacuga = getEntity();
		EntityLivingBase target = nargacuga.getAttackTarget();
		Vec3 distanceVec = WorldHelper.getVectorToTarget(nargacuga, target);
		int frame = getCurrentFrame();
		if (distanceVec.lengthVector() < MIN_DISTANCE) {
			frameAdvancer.setLoopActive(false);
			isRushing = false;
		}
		if (frame < RUN_START || frame > LOOP_END)
			return;
		float speed = RUSH_SPEED;
		if (frame < LOOP_START) {
			speed = (frame - RUN_START) / (LOOP_START - RUN_START);
		} else if (!isRushing) {
			speed = 0.1f;
		}
		nargacuga.getTurnHelper().updateTurnSpeed(TURN_SPEED);
		nargacuga.getTurnHelper().updateTargetPoint(target);
		nargacuga.moveForward(speed, true);
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
