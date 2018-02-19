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
import mhfc.net.common.ai.entity.AIAngleWhip;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.entity.monsters.nargacuga.BackOff;
import mhfc.net.common.ai.entity.monsters.nargacuga.Charge;
import mhfc.net.common.ai.entity.monsters.nargacuga.Pounce;
import mhfc.net.common.ai.entity.monsters.nargacuga.ProwlerStance;
import mhfc.net.common.ai.entity.monsters.nargacuga.Roar;
import mhfc.net.common.ai.entity.monsters.nargacuga.TailSlam;
import mhfc.net.common.ai.manager.builder.FollowUpManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.entity.type.IEnragable;
import mhfc.net.common.item.materials.ItemMaterial.MaterialSubType;
import mhfc.net.common.util.SubTypedItem;
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
		setSize(8.6F, 4.4F);
		stepHeight = 2.0F;
		recorder = new RecorderAdapter<>(100);
		eyesPositionsRight = EvictingQueue.create(EYES_RECORD_LENGTH);
		eyesPositionsLeft = EvictingQueue.create(EYES_RECORD_LENGTH);
		ticksSinceEyesSaved = 0;
		enraged = false;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5150D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(45D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected IActionManager<EntityNargacuga> constructActionManager() {
		FollowUpManagerBuilder<EntityNargacuga> attackManager = new FollowUpManagerBuilder<>();
		attackManager.registerAction(
				setDeathAction(
						new AIDeath(
								this,
								"mhfc:models/Nargacuga/NargaDeath.001.mcanm",
								MHFCSoundRegistry.getRegistry().nargacugaDeath)));
		TailSlam tailSlam = new TailSlam();
		AIAngleWhip<EntityNargacuga> tailWhip = new AIAngleWhip<>(
				"mhfc:models/Nargacuga/TailSwipeRight.mcanm",
				56,
				23,
				80,
				6F,
				MHFCSoundRegistry.getRegistry().nargacugaTailWhip,
				7,
				5,
				1,
				180,
				10);
		Roar roar = new Roar();
		ProwlerStance prowler = new ProwlerStance();
		Pounce pounce = new Pounce();
		BackOff backOff = new BackOff();
		Charge charge = new Charge();

		List<IExecutableAction<? super EntityNargacuga>> prowlerFollow = new ArrayList<>();

		prowlerFollow.add(pounce);
		prowlerFollow.add(tailWhip);
		attackManager.registerAction(new AIBreathe(this, "mhfc:models/nargacuga/nargacugaidle.mcanm", 60, 2F));
		attackManager.registerAllowingAllActions(tailSlam);
		attackManager.registerAllowingAllActions(tailWhip);
		attackManager.registerActionWithFollowUps(prowler, prowlerFollow);
		attackManager.allowAllStrongActions(pounce);
		attackManager.registerAllowingAllActions(roar);
		attackManager.registerAllowingAllActions(backOff);
		attackManager.registerAllowingAllActions(charge);

		attackManager.registerAction(
				new AIWander<EntityNargacuga>(
						this,
						"mhfc:models/nargacuga/wander.mcanm",
						120,
						1F,
						0.12F,
						0.7F,
						10,
						94,
						1,
						30));


		return attackManager.build(this);
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
		if (++ticksSinceEyesSaved >= EYES_RECORD_FREQUENCY && this.world.isRemote) {
			ticksSinceEyesSaved = 0;
			eyesPositionsLeft.add(getPositionLeftEye());
			eyesPositionsRight.add(getPositionRightEye());
		}
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.NARGASTEM, 1));
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.NARGAWING, 1));
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
