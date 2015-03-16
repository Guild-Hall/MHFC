package mhfc.net.common.entity.mob;

import mhfc.net.common.ai.tigrex.BiteAttack;
import mhfc.net.common.ai.tigrex.GroundHurl;
import mhfc.net.common.ai.tigrex.JumpAttack;
import mhfc.net.common.ai.tigrex.RunAttack;
import mhfc.net.common.ai.tigrex.SpinAttack;
import mhfc.net.common.core.registry.MHFCItemRegistry;
import mhfc.net.common.entity.type.EntityMHFCBase;
import mhfc.net.common.entity.type.EntityMHFCPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityTigrex extends EntityMHFCBase<EntityTigrex> {

	public int deathTick;
	public int rageLevel;

	public EntityTigrex(World par1World) {
		super(par1World);
		this.height = 4f;
		this.width = 6f;

		this.attackManager.registerAttack(new RunAttack());
		this.attackManager.registerAttack(new SpinAttack());
		this.attackManager.registerAttack(new GroundHurl());
		this.attackManager.registerAttack(new BiteAttack());
		this.attackManager.registerAttack(new JumpAttack());

		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
				EntityPopo.class, 0, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
				EntityPlayer.class, 0, true));
	}

	@Override
	public void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap()
				.getAttributeInstance(SharedMonsterAttributes.followRange)
				.setBaseValue(128d);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
				.setBaseValue(1.3D);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
				healthbaseHP(6000D, 7800D, 8600D));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(
				35D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(
				0.3D);
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
			dropItemRand(MHFCItemRegistry.MHFCItemTigrexScale, 2);
		}
		for (var4 = 0; var4 < 8; ++var4) {
			dropItemRand(MHFCItemRegistry.MHFCItemTigrexShell, 1);
			dropItemRand(MHFCItemRegistry.mhfcitemtigrexfang, 1);
			dropItemRand(MHFCItemRegistry.mhfcitemtigrexclaw, 1);
		}
		for (var4 = 0; var4 < 1; ++var4) {
			dropItemRand(MHFCItemRegistry.mhfcitemtigrextail, 2);
		}
		dropItemRand(MHFCItemRegistry.mhfcitemtigrexskullshell, 1);
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
