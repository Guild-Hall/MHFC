package mhfc.net.common.entity.monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation;
import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation.BoneTransformation;
import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Queues;

import mhfc.net.common.ai.AIFollowUpActionManager;
import mhfc.net.common.ai.IActionRecorder;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.entity.nargacuga.NargacugaBackOff;
import mhfc.net.common.ai.entity.nargacuga.NargacugaPounce;
import mhfc.net.common.ai.entity.nargacuga.NargacugaPounce.JumpBehaviour;
import mhfc.net.common.ai.entity.nargacuga.NargacugaRoar;
import mhfc.net.common.ai.entity.nargacuga.NargacugaTailWhip;
import mhfc.net.common.ai.entity.nargacuga.ProwlerStance;
import mhfc.net.common.ai.entity.nargacuga.TailSlam;
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
		setSize(4, 5);

		recorder = new RecorderAdapter<>(100);
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		eyesPositionsRight = Queues.synchronizedQueue(EvictingQueue.<Vec3>create(EYES_RECORD_LENGTH));
		eyesPositionsLeft = Queues.synchronizedQueue(EvictingQueue.<Vec3>create(EYES_RECORD_LENGTH));
		ticksSinceEyesSaved = 0;
		enraged = false;

		AIFollowUpActionManager<EntityNargacuga> attackManager = new AIFollowUpActionManager<EntityNargacuga>(this);
		TailSlam tailSlam = new TailSlam();
		NargacugaRoar roar = new NargacugaRoar();
		ProwlerStance prowler = new ProwlerStance();
		NargacugaPounce pounceTwo = NargacugaPounce.createNargaPounce(JumpBehaviour.TwoJumps);
		NargacugaPounce pounceThree = NargacugaPounce.createNargaPounce(JumpBehaviour.ThreeJump);
		NargacugaTailWhip tailWhip = new NargacugaTailWhip();
		NargacugaBackOff backOff = new NargacugaBackOff();
		// NargacugaPounce pounceFour = NargacugaPounce.createNargaPounce(
		// NargaJumpBehaviour.FOUR_JUMPS);

		List<IExecutableAction<? super EntityNargacuga>> prowlerFollow = new ArrayList<IExecutableAction<? super EntityNargacuga>>();
		prowlerFollow.add(pounceTwo);
		prowlerFollow.add(pounceThree);
		prowlerFollow.add(tailWhip);
		// prowlerFollow.add(pounceFour);

		attackManager.registerAttack(tailSlam);
		attackManager.registerAttack(roar);
		attackManager.registerAttack(prowler, prowlerFollow);
		attackManager.registerAttack(backOff);
		setAIActionManager(attackManager);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(5500D, 11000D, 22000D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.43D);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(2, 2, 2);
		return super.preRenderCallback(scale, sub);
	}

	private Vec3 getRelativePositionOfBone(String name) {
		int frame = getCurrentFrame();
		IAnimation animation = getAttackManager().getCurrentAnimation();
		if (animation == null) {
			return Vec3.createVectorHelper(0, 0, 0);
		}
		BoneTransformation boneTrans = animation.getCurrentTransformation(name, frame);
		// FIXME find out why the bones give null pointers and fix thise
		if (boneTrans == null) {
			return Vec3.createVectorHelper(0, 0, 0);
		}
		Matrix4f transform = boneTrans.asMatrix();
		Vec3 relativePosition = Vec3.createVectorHelper(transform.m03, transform.m13, transform.m23);
		return relativePosition;
	}

	private Vec3 getPositionLeftEye() {
		Vec3 relativePosition = getRelativePositionOfBone("Eye.L");
		return relativePosition.addVector(posX, posY, posZ);
	}

	private Vec3 getPositionRightEye() {
		Vec3 relativePosition = getRelativePositionOfBone("Eye.R");
		return relativePosition.addVector(posX, posY, posZ);
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

}
