package mhfc.net.common.entity.mob;

import mhfc.net.common.ai.general.TurnAttack;
import mhfc.net.common.ai.tigrex.*;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import mhfc.net.common.item.materials.ItemTigrex.TigrexSubType;
import mhfc.net.common.util.SubTypedItem;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityTigrex extends EntityMHFCBase<EntityTigrex> {

	public int deathTick;
	public int rageLevel;

	public EntityTigrex(World par1World) {
		super(par1World);
		height = 2f;
		width = 3f;

		attackManager.registerAttack(new TurnAttack(110, 180, 5f, 12f));
		attackManager.registerAttack(new RunAttack());
		attackManager.registerAttack(new SpinAttack());
		attackManager.registerAttack(new GroundHurl());
		attackManager.registerAttack(new BiteAttack());
		attackManager.registerAttack(new RoarAttack());
//		attackManager.registerAttack(new JumpTigrex());

		// TODO enable this when Popos are a thing again
		// targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
		// EntityPopo.class, 0, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
			EntityPlayer.class, 0, true));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().getAttributeInstance(SharedMonsterAttributes.followRange).setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(healthbaseHP(6000D, 7800D, 8600D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
	}

	@Override
	public void entityInit() {
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) 0));
		dataWatcher.addObject(17, Byte.valueOf((byte) 0));
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
	public EntityMHFCPart[] getParts() {
		// TODO Auto-generated method stub
		return null;
	}
}
