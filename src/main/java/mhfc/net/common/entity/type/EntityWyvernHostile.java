package mhfc.net.common.entity.type;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

@Deprecated
public class EntityWyvernHostile extends EntityCreature implements IMob {

	public int getArmor = 17;
	public double health;
	public double speed;
	public int getExpValue = 1000;
	public boolean isHightoStun;
	public boolean lethResist;

	public EntityWyvernHostile(World par1World) {
		super(par1World);
		experienceValue = getExpValue;
		getNavigator().setBreakDoors(true);
		getNavigator().setAvoidsWater(true);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class,
				8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));

		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
				EntityPlayer.class, 0, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this,
				EntityIronGolem.class, 0, true));

	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.dimension != 0) {
			this.setDead();
		}
	}

	@Override
	protected Entity findPlayerToAttack() {
		EntityPlayer entityplayer = worldObj
				.getClosestVulnerablePlayerToEntity(this, 40.0D);
		return entityplayer != null && canEntityBeSeen(entityplayer)
				? entityplayer
				: null;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
		float dmg = par2;
		Entity entity = par1DamageSource.getEntity();
		if (entity instanceof EntityPlayer
				|| entity instanceof EntityWyvernHostile)
			return super.attackEntityFrom(par1DamageSource, dmg);
		return super.attackEntityFrom(par1DamageSource, (dmg / 2));
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {
		boolean flag = par1Entity.attackEntityFrom(
				DamageSource.causeMobDamage(this), 4);
		return flag;
	}

	public void dropItemRand(Item index, int par1) {
		dropItemRand(new ItemStack(index, par1, 0));
	}

	public void dropItemRand(ItemStack stack) {
		EntityItem itemEntity = new EntityItem(this.worldObj, posX
				+ worldObj.rand.nextInt(10) - 5, posY + 1.0D, this.posZ
				+ worldObj.rand.nextInt(10) - 5, stack);
		worldObj.spawnEntityInWorld(itemEntity);
	}

	@Override
	public float getBlockPathWeight(int par1, int par2, int par3) {
		return 1F - this.worldObj.getLightBrightness(par1, par2, par3);
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public int getTotalArmorValue() {
		return getArmor;
	}

	@Override
	protected void updateFallState(double par1, boolean par3) {}

	public void applyMonsterAttributes(double knockback, double baselowhp,
			double basemedhp, double basehighhp, double baseRange,
			double baseSpeed) {
		speed = baseSpeed;
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance)
				.setBaseValue(knockback);
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(
				healthbaseHP(baselowhp, basemedhp, basehighhp));
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(
				baseRange);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(
				baseSpeed);
	}

	@Override
	protected void fall(float f1) {}

	public double healthbaseHP(double lowhp, double medhp, double highhp) {

		if (this.rand.nextInt(60) == 0) {
			return medhp;
		} else if (this.rand.nextInt(120) == 0) {
			return highhp;
		} else if (this.rand.nextInt(80) == 0) {
			return lowhp;
		}
		return medhp;
	}

}
