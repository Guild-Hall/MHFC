package mhfc.net.common.entity.monster.wip;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.model.util.RenderPassInformation;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IStancedEntity;
import mhfc.net.common.ai.entity.AIBreathe;
import mhfc.net.common.ai.entity.AIDeath;
import mhfc.net.common.ai.entity.AIWander;
import mhfc.net.common.ai.entity.monsters.rathalos.Rush;
import mhfc.net.common.ai.manager.builder.ActionManagerBuilder;
import mhfc.net.common.core.registry.MHFCSoundRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.entity.type.IConfusable;
import mhfc.net.common.item.materials.ItemMaterial.MaterialSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityRathalos extends EntityMHFCBase<EntityRathalos>
		implements
		IStancedEntity<EntityRathalos, EntityRathalos.Stances>,
		IConfusable {

	public static enum Stances implements IStancedEntity.IStance<EntityRathalos> {
		GROUND,
		FLYING,
		FALLING,
		BLINDED {
			@Override
			public void onAttackStart(IExecutableAction<? super EntityRathalos> attack, EntityRathalos entity) {
				entity.confusedAttacks++;
			}

			@Override
			public void onAttackEnd(IExecutableAction<? super EntityRathalos> attack, EntityRathalos entity) {
				if (entity.getNumberOfConfusedAttacks() == 3) {
					entity.setStance(GROUND);
				}
			}
		};

		@Override
		public void onAttackEnd(IExecutableAction<? super EntityRathalos> attack, EntityRathalos entity) {}

		@Override
		public void onAttackStart(IExecutableAction<? super EntityRathalos> attack, EntityRathalos entity) {}

		@Override
		public void onStanceStart() {}

		@Override
		public void onStanceEnd() {}

	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(healthbaseHP(15341D));
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(13D);
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 0, true, true, null));
	}

	@Override
	protected IActionManager<EntityRathalos> constructActionManager() {
		ActionManagerBuilder<EntityRathalos> attackManager = new ActionManagerBuilder<>();
		//stancedAttackManager.registerAction(new Idle());
		attackManager.registerAction(new AIBreathe(this, "mhfc:models/Rathalos/rathalosidle.mcanm", 65, 5));
		attackManager.registerAction(new AIWander<EntityRathalos>(this, "mhfc:models/Rathalos/RathalosWalk.mcanm", 125, 1F, 0.07F, 3F, 0, 120, 1, 15));
		//stancedAttackManager.registerAction(new BiteLeft());
		//stancedAttackManager.registerAction(new BiteRight());
		//stancedAttackManager.registerAction(new TailWhip());
		attackManager.registerAction(new Rush());
		attackManager.registerAction(
				setDeathAction(
						new AIDeath(
								this,
								"mhfc:models/Rathalos/RathalosDeath.mcanm",
								MHFCSoundRegistry.getRegistry().rathalosDeath)));
		return attackManager.build(this);
	}

	@Override
	public RenderPassInformation preRenderCallback(float scale, RenderPassInformation sub) {
		GL11.glScaled(2, 2, 2);
		return super.preRenderCallback(scale, sub);
	}

	private int confusedAttacks;
	private Stances stance;

	public EntityRathalos(World world) {
		super(world);
		this.stance = Stances.GROUND;
		this.setSize(5F, 5F);

	}

	@Override
	public EntityMHFCPart[] getParts() {
		return null;
	}

	@Override
	public void confuse() {
		confusedAttacks = 0;
		setStance(Stances.BLINDED);
	}

	@Override
	public int getNumberOfConfusedAttacks() {
		return confusedAttacks;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
	}

	/**
	 * FIXME This should disable falling during flying but it also makes collision weird I don't know if it disables AI
	 * movement as well
	 */
	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
		if (getStance() == Stances.FLYING) {
			this.motionY = 0;
			this.fallDistance = 0;
			y = 0;
		}

		super.updateFallState(y, onGroundIn, state, pos);
	}

	@Override
	public void setStance(Stances stance) {
		this.stance.onStanceEnd();
		this.stance = stance;
		this.stance.onStanceStart();
	}

	@Override
	public void onAttackStart(IExecutableAction<? super EntityRathalos> newAttack) {
		super.onAttackStart(newAttack);
		this.stance.onAttackStart(newAttack, this);
	}

	@Override
	public void onAttackEnd(IExecutableAction<? super EntityRathalos> oldAttack) {
		super.onAttackEnd(oldAttack);
		this.stance.onAttackEnd(oldAttack, this);
	}

	@Override
	public Stances getStance() {
		return this.stance;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return MHFCSoundRegistry.getRegistry().rathalosIdle;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2) {
		int var4;
		for (var4 = 0; var4 < 13; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.RATHALOSSHELL, 1));
		}
		for (var4 = 0; var4 < 8; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.RATHALOSWEBBING, 1));
			dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.RATHALOSMARROW, 1));

		}
		for (var4 = 0; var4 < 1; ++var4) {
			dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.RATHALOSWING, 1));

		}
		dropItemRand(SubTypedItem.fromSubItem(MaterialSubType.RATHALOSPLATE, 1));
	}
}
