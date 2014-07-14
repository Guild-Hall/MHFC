package mhfc.heltrato.common.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class AIWyvernAttackOnCollide extends EntityAIBase
{

    World worldObj;
    EntityCreature attacker;
    EntityLivingBase entityTarget;
    private int attackTick;
    private int maxAttackTick;
    public float moveSpeed;
    boolean field_75437_f;
    PathEntity entityPathEntity;
    Class classTarget;
    private int field_75445_i;

    public AIWyvernAttackOnCollide(EntityCreature par1EntityLiving, Class par2Class, float par3, boolean par4)
    {
        this(par1EntityLiving, par3, par4);
        classTarget = par2Class;
    }

    public AIWyvernAttackOnCollide(EntityCreature par1EntityLiving, float par2, boolean par3)
    {
        attackTick = 0;
        maxAttackTick = 20;
        attacker = par1EntityLiving;
        worldObj = par1EntityLiving.worldObj;
        moveSpeed = par2;
        field_75437_f = par3;
        setMutexBits(3);
    }

    public boolean shouldExecute()
    {
        EntityLivingBase var1 = attacker.getAttackTarget();
        if(var1 == null)
        {
            return false;
        }
        if(classTarget != null && !classTarget.isAssignableFrom(var1.getClass()))
        {
            return false;
        } else
        {
            entityTarget = var1;
            entityPathEntity = attacker.getNavigator().getPathToEntityLiving(entityTarget);
            return entityPathEntity != null;
        }
    }

    public boolean continueExecuting()
    {
        EntityLivingBase var1 = attacker.getAttackTarget();
        return var1 != null ? var1.isEntityAlive() ? field_75437_f ? attacker.isWithinHomeDistance(MathHelper.floor_double(var1.posX), MathHelper.floor_double(var1.posY), MathHelper.floor_double(var1.posZ)) : !attacker.getNavigator().noPath() : false : false;
    }

    public void startExecuting()
    {
        attacker.getNavigator().setPath(entityPathEntity, moveSpeed);
        field_75445_i = 0;
    }

    public void resetTask()
    {
        entityTarget = null;
        attacker.getNavigator().clearPathEntity();
    }

    public void updateTask()
    {
        attacker.getLookHelper().setLookPositionWithEntity(entityTarget, 30F, 30F);
        if((field_75437_f || attacker.getEntitySenses().canSee(entityTarget)) && --field_75445_i <= 0)
        {
            field_75445_i = 4 + attacker.getRNG().nextInt(7);
            attacker.getNavigator().tryMoveToEntityLiving(entityTarget, moveSpeed);
        }
        attackTick = Math.max(attackTick - 1, 0);
        double var1 = attacker.width * 2.0F * attacker.width * 2.0F;
        if(attacker.getDistanceSq(entityTarget.posX, entityTarget.boundingBox.minY, entityTarget.posZ) <= var1 && attackTick <= 0)
        {
            attackTick = maxAttackTick;
            if(attacker.getHeldItem() != null)
            {
                attacker.swingItem();
            }
            attacker.attackEntityAsMob(entityTarget);
        }
    }

    public int getAttackTick()
    {
        return attackTick;
    }

    public void resetAttackTick()
    {
        attackTick = maxAttackTick;
    }

    public AIWyvernAttackOnCollide setMaxAttackTick(int max)
    {
        maxAttackTick = max;
        return this;
    }
    
}
