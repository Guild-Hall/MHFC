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
import mhfc.net.common.ai.entity.boss.nargacuga.ProwlerStance;
import mhfc.net.common.ai.entity.boss.nargacuga.Roar;
import mhfc.net.common.ai.entity.boss.nargacuga.TailSlam;
import mhfc.net.common.ai.entity.boss.nargacuga.TailWhip;
import mhfc.net.common.ai.entity.boss.nargacuga.Wander;
import mhfc.net.common.ai.manager.builder.FollowUpManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.entity.type.IEnragable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityNargacuga extends EntityMHFCBase<EntityNargacuga>
		implements
		IActionRecorder<EntityNargacuga>,
		IEnragable {

	public static final int EYES_RECORD_LENGTH = 30;
	// How many ticks are between each recording of the eyes positions
	public static final int EYES_RECORD_FREQUENCY = 10;

	private IActionRecorder.RecorderAdapter<EntityNargacuga> recorder;
	private Queue<Vec3d> eyesPositionsRight;
	private Queue<Vec3d> eyesPositionsLeft;
	private int ticksSinceEyesSaved;
	private boolean enraged;

	public EntityNargacuga(World world) {
		super(world);
		setSize(4.6F, 4.4F);
		stepHeight = 2.0F;
		recorder = new RecorderAdapter<>(100);
		eyesPositionsRight = EvictingQueue.create(EYES_RECORD_LENGTH);
		eyesPositionsLeft = EvictingQueue.create(EYES_RECORD_LENGTH);
		ticksSinceEyesSaved = 0;
		enraged = false;
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected IActionManager<EntityNargacuga> constructActionManager() {
		FollowUpManagerBuilder<EntityNargacuga> attackManager = new FollowUpManagerBuilder<>();
		TailSlam tailSlam = new TailSlam();
		Roar roar = new Roar();
		ProwlerStance prowler = new ProwlerStance();
		Pounce pounce = new Pounce();
		TailWhip tailWhip = new TailWhip();
		BackOff backOff = new BackOff();
		// NargacugaPounce pounceFour = NargacugaPounce.createNargaPounce(
		// NargaJumpBehaviour.FOUR_JUMPS);
		Charge charge = new Charge();

		List<IExecutableAction<? super EntityNargacuga>> prowlerFollow = new ArrayList<>();
		
		//prowlerFollow.add(pounce);
		//prowlerFollow.add(tailWhip);
		// prowlerFollow.add(pounceFour);
		
		//Working
		//attackManager.registerAction(new Wander());
		//attackManager.registerAction(new Idle());
		//attackManager.registerAllowingAllActions(tailSlam);
		attackManager.registerAllowingAllActions(tailWhip);
		//On Progress
		//attackManager.registerAllowingAllActions(roar);
		//attackManager.registerActionWithFollowUps(prowler, prowlerFollow);
		//attackManager.registerAllowingAllActions(backOff);
		//attackManager.registerAction(setDeathAction(new Death()));
		//attackManager.allowAllStrongActions(pounce);
		
		//attackManager.registerAllowingAllActions(charge);
		return attackManager.build(this);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		//default is 10721D
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(21321D));
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

	private Vec3d getRelativePositionOfBone(String name) {
		int frame = getFrame();
		IAnimation animation = getActionManager().getCurrentAnimation();
		if (animation == null) {
			return new Vec3d(0, 0, 0);
		}
		BoneTransformation boneTransform = new BoneTransformation();
		animation.storeCurrentTransformation(name, frame, boneTransform);
		Matrix4f transform = boneTransform.getMatrix();
		Vec3d relativePosition = new Vec3d(transform.m03, transform.m13, transform.m23);
		return relativePosition;
	}

	private Vec3d getPositionLeftEye() {
		return getRelativePositionOfBone("Eye.L").addVector(posX, posY, posZ);
	}

	private Vec3d getPositionRightEye() {
		return getRelativePositionOfBone("Eye.R").addVector(posX, posY, posZ);
	}

	public Queue<Vec3d> getEyesPositionsRight() {
		return eyesPositionsRight;
	}

	public Queue<Vec3d> getEyesPositionsLeft() {
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
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().nargacugaIdle;
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
		y = 0;
		this.fallDistance = 0;
		super.updateFallState(y, onGroundIn, state, pos);
	}
}
