package mhfc.net.common.entity.mob;

import java.util.List;
import java.util.Queue;

import mhfc.net.common.ai.IActionRecorder;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.entity.type.IEnragable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.mcanmmodel.data.RenderPassInformation;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.Queues;

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
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
			EntityPlayer.class, 0, true));
		eyesPositionsRight = Queues.synchronizedQueue(EvictingQueue
			.<Vec3> create(EYES_RECORD_LENGTH));
		eyesPositionsLeft = Queues.synchronizedQueue(EvictingQueue
			.<Vec3> create(EYES_RECORD_LENGTH));
		ticksSinceEyesSaved = 0;
		enraged = false;
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(
			SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
			.setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
			healthbaseHP(9000D, 13888D, 18465D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(
			35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(
			0.43D);
	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale,
		RenderPassInformation sub) {
		GL11.glScaled(2, 2, 2);
		return super.preRenderCallback(scale, sub);
	}

	private Vec3 getPositionLeftEye() {
		// FIXME use bones that should exist at the eyes positions for this
		return Vec3.createVectorHelper(posX, posY, posZ);
	}

	private Vec3 getPositionRightEye() {
		return Vec3.createVectorHelper(posX, posY, posZ);
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
		if (++ticksSinceEyesSaved >= EYES_RECORD_FREQUENCY) {
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
