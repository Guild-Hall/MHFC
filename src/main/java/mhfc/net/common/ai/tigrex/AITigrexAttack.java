package mhfc.net.common.ai.tigrex;

import mhfc.net.common.entity.mob.EntityTigrex;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class AITigrexAttack extends EntityAIBase
{

    private float dist;
    private float actualDistSq;
    private EntityTigrex tigrex;
    private EntityLivingBase attackTarget;

    public AITigrexAttack(EntityTigrex par1, float f)
    {
        dist = f;
        actualDistSq = 0.0F;
        tigrex = par1;
        attackTarget = null;
    }

    public boolean isContinuous()
    {
        return false;
    }

    public boolean shouldExecute()
    {
        attackTarget = tigrex.getAttackTarget();
        if(attackTarget == null)
        {
            return false;
        } 
        
        double x = this.attackTarget.posX - this.tigrex.posX - (this.attackTarget.width + this.tigrex.width) / 2.0F;
        double y = this.attackTarget.posY - this.tigrex.posY - (this.attackTarget.height + this.tigrex.height) / 2.0F;
        double z = this.attackTarget.posZ - this.tigrex.posZ - (this.attackTarget.width + this.tigrex.width) / 2.0F;
        x = Math.max(0.0D, x);
        y = Math.max(0.0D, y);
        z = Math.max(0.0D, z);
        this.actualDistSq = ((float)(x * x + y * y + z * z));
        
        return actualDistSq <= dist * dist;
    }

    public void startExecuting()
    {
        tigrex.attackEntityAtDistSq(attackTarget, actualDistSq);
    }
    
    public void resetTask(){
		super.resetTask();
	}
}
