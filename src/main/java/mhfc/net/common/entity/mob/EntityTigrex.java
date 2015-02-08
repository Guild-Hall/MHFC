package mhfc.net.common.entity.mob;

import mhfc.net.common.ai.AIAttackManager;
import mhfc.net.common.ai.IExecutableAttack;
import mhfc.net.common.ai.IMangedAttacks;
import mhfc.net.common.ai.tigrex.RunAttack;
import mhfc.net.common.ai.tigrex.SpinAttack;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.item.materials.ItemTigrex.TigrexSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.world.World;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.IAnimatedObject;
import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.IAnimation;
import com.google.common.base.Predicate;

public class EntityTigrex extends EntityWyvernHostile
		implements
			IAnimatedObject,
			IMangedAttacks<EntityTigrex> {

	public int deathTick;
	public int rageLevel;
	/** Kept around to register attacks and return the currently executed one */
	private AIAttackManager<EntityTigrex> attkManager;
	/** Gets increased every tick by the entity to animate the model */
	private int animFrame;

	public EntityTigrex(World par1World) {
		super(par1World);
		width = 6F;
		height = 4F;
		// New AI
		attkManager = new AIAttackManager<EntityTigrex>(this);
		attkManager.registerAttack(new RunAttack());
		attkManager.registerAttack(new SpinAttack());
		// New AI test
		tasks.addTask(0, attkManager);
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap()
				.getAttributeInstance(SharedMonsterAttributes.followRange)
				.setBaseValue(128d);
		applyMonsterAttributes(1.3D, 6000D, 7800D, 8600D, 35D, 0.3D);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
	}

	public void setThrownBlock() {
		dataWatcher.updateObject(16, Byte.valueOf((byte) 1));
	}

	public boolean getThrownBlock() {
		return dataWatcher.getWatchableObjectByte(16) == 1;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var4;
		for (var4 = 0; var4 < 13; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.SCALE, 2));
		}
		for (var4 = 0; var4 < 8; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.SHELL, 1));
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.FANG, 1));
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.CLAW, 1));
		}
		for (var4 = 0; var4 < 1; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.TAIL, 2));
		}
		dropItemRand(SubTypedItem.fromSubItem(TigrexSubType.SKULLSHELL, 1));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		animFrame = this.attkManager.getNextFrame(animFrame);
	}

	@Override
	protected String getLivingSound() {
		return null;
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	@Deprecated
	public boolean attackEntityAsMob(Entity entity) {
		return true;
	}

	@Override
	public IAnimation getCurrentAnimation() {
		return attkManager.getCurrentAnimation();
	}

	@Override
	public int getCurrentFrame() {
		return animFrame;
	}

	@Override
	public Predicate<String> getPartPredicate(float arg0) {
		return RENDER_ALL;
	}

	@Override
	public Scale getScale() {
		return NO_SCALE;
	}

	@Override
	public void onAttackStart(IExecutableAttack<EntityTigrex> newAttack) {
		if (newAttack == null)
			animFrame = -1;
		else
			animFrame = 0;
	}

	@Override
	public void onAttackEnd(IExecutableAttack<EntityTigrex> oldAttack) {
		animFrame = -1;
	}

	@Override
	public AIAttackManager<EntityTigrex> getAttackManager() {
		return attkManager;
	}
}
