package mhfc.net.common.entity.monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import javax.vecmath.Matrix4f;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;
import com.github.worldsender.mcanm.common.animation.IAnimation;
import com.github.worldsender.mcanm.common.animation.IAnimation.BoneTransformation;
import com.google.common.collect.EvictingQueue;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IActionRecorder;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.boss.nargacuga.BackOff;
import mhfc.net.common.ai.entity.boss.nargacuga.Charge;
import mhfc.net.common.ai.entity.boss.nargacuga.Death;
import mhfc.net.common.ai.entity.boss.nargacuga.Idle;
import mhfc.net.common.ai.entity.boss.nargacuga.Pounce;
import mhfc.net.common.ai.entity.boss.nargacuga.Pounce.JumpBehaviour;
import mhfc.net.common.ai.entity.boss.nargacuga.Roar;
import mhfc.net.common.ai.entity.boss.nargacuga.TailWhip;
import mhfc.net.common.ai.entity.boss.nargacuga.Wander;
import mhfc.net.common.ai.entity.boss.nargacuga.ProwlerStance;
import mhfc.net.common.ai.entity.boss.nargacuga.TailSlam;
import mhfc.net.common.ai.manager.builder.FollowUpManagerBuilder;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.entity.type.IEnragable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityNargacuga extends EntityMHFCBase<EntityNargacuga>
		implements
		IActionRecorder<EntityNargacuga>,
		IEnragable {

	public static final int EYES_RECORD_LENGTH = 30;
	// How many ticks are between each recording of the eyes positions
	public static final int EYES_RECORD_FREQUENCY = 10;

	private IActionRecorder.RecorderAdapter<EntityNargacuga> recorder;
	private Queue<Vec3> eyesPositionsRight;
	private Queue<Vec3> eyesPositionsLeft;
	private int ticksSinceEyesSaved;
	private boolean enraged;

	public EntityNargacuga(World world) {
		super(world);
		setSize(8.6F, 4.4F);
		stepHeight = 2.0F;
		recorder = new RecorderAdapter<>(100);
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		eyesPositionsRight = EvictingQueue.create(EYES_RECORD_LENGTH);
		eyesPositionsLeft = EvictingQueue.create(EYES_RECORD_LENGTH);
		ticksSinceEyesSaved = 0;
		enraged = false;
	}

	@Override
	public IActionManager<EntityNargacuga> constructActionManager() {
		FollowUpManagerBuilder<EntityNargacuga> attackManager = new FollowUpManagerBuilder<>();
		TailSlam tailSlam = new TailSlam();
		Roar roar = new Roar();
		ProwlerStance prowler = new ProwlerStance();
		Pounce pounceTwo = Pounce.createNargaPounce(JumpBehaviour.TwoJumps);
		Pounce pounceThree = Pounce.createNargaPounce(JumpBehaviour.ThreeJump);
		TailWhip tailWhip = new TailWhip();
		BackOff backOff = new BackOff();
		// NargacugaPounce pounceFour = NargacugaPounce.createNargaPounce(
		// NargaJumpBehaviour.FOUR_JUMPS);
		Charge charge = new Charge();

		List<IExecutableAction<? super EntityNargacuga>> prowlerFollow = new ArrayList<>();
		prowlerFollow.add(pounceTwo);
		prowlerFollow.add(pounceThree);
		prowlerFollow.add(tailWhip);
		// prowlerFollow.add(pounceFour);
		attackManager.registerAction(new Wander());
		attackManager.registerAction(new Idle());
		attackManager.registerAllowingAllActions(tailSlam);
		attackManager.registerAllowingAllActions(roar);
		attackManager.registerActionWithFollowUps(prowler, prowlerFollow);
		attackManager.registerAllowingAllActions(backOff);
		attackManager.registerAction(setDeathAction(new Death()));
		attackManager.allowAllStrongActions(pounceThree);
		attackManager.allowAllStrongActions(pounceTwo);
		attackManager.allowAllStrongActions(tailWhip);
		attackManager.registerAllowingAllActions(charge);
		return attackManager.build(this);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		//default is 10721D
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(19721D));
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(2.6, 2.6, 2.6);
		return super.preRenderCallback(scale, sub);
	}

	private Vec3 getRelativePositionOfBone(String name) {
		int frame = getCurrentFrame();
		IAnimation animation = getActionManager().getCurrentAnimation();
		if (animation == null) {
			return Vec3.createVectorHelper(0, 0, 0);
		}
		BoneTransformation boneTransform = new BoneTransformation();
		animation.storeCurrentTransformation(name, frame, boneTransform);
		Matrix4f transform = boneTransform.getMatrix();
		Vec3 relativePosition = Vec3.createVectorHelper(transform.m03, transform.m13, transform.m23);
		return relativePosition;
	}

	private Vec3 getPositionLeftEye() {
		return getRelativePositionOfBone("Eye.L").addVector(posX, posY, posZ);
	}

	private Vec3 getPositionRightEye() {
		return getRelativePositionOfBone("Eye.R").addVector(posX, posY, posZ);
	}

	public Queue<Vec3> getEyesPositionsRight() {
		return eyesPositionsRight;
	}

	public Queue<Vec3> getEyesPositionsLeft() {
		return eyesPositionsLeft;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (++ticksSinceEyesSaved >= EYES_RECORD_FREQUENCY && this.worldObj.isRemote) {
			ticksSinceEyesSaved = 0;
			eyesPositionsLeft.add(getPositionLeftEye());
			eyesPositionsRight.add(getPositionRightEye());
		}
	}

	@Override
	public void onAttackEnd(IExecutableAction<? super EntityNargacuga> oldAttack) {
		recorder.addAction(oldAttack);
	}

	@Override
	public List<IExecutableAction<? super EntityNargacuga>> getActionHistory() {
		return recorder.getActionHistory();
	}

	@Override
	public IExecutableAction<? super EntityNargacuga> getLastAction() {
		return recorder.getLastAction();
	}

	@Override
	public boolean isEnraged() {
		return enraged;
	}

	@Override
	protected String getLivingSound() {
		return "mhfc:narga.idle";
	}

	@Override
	protected void updateFallState(double distance, boolean p_70064_3_) {
		distance = 0;
		this.fallDistance = 0;
		super.updateFallState(distance, p_70064_3_);
	}

}
