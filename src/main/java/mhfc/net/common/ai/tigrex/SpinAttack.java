package mhfc.net.common.ai.tigrex;

import mhfc.net.common.ai.AttackAdapter;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.type.EntityWyvernHostile;
import mhfc.net.common.entity.type.EntityWyvernPeaceful;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.stored.AnimationRegistry;

public class SpinAttack extends AttackAdapter<EntityTigrex> {
	private static final int MAX_FRAME = 30;

	private boolean finished = false;

	public SpinAttack() {
		setAnimation(AnimationRegistry.loadAnimation(new ResourceLocation(
				"mhfc:models/Tigrex/tailswipe.mcanm")));
	}

	@Override
	public float getWeight() {
		EntityLivingBase target = this.entity.getAttackTarget();
		if (target == null)
			return 0.0F;
		Vec3 pos = this.entity.getPosition(1.0F);
		Vec3 entityToTarget = target.getPosition(1.0F);
		entityToTarget = pos.subtract(entityToTarget);
		if(target instanceof EntityPlayer){
			target.attackEntityFrom(DamageSource.causeMobDamage(entity), 22F);
		}else if(target instanceof EntityWyvernHostile || target instanceof EntityWyvernPeaceful){
			target.attackEntityFrom(DamageSource.causeMobDamage(entity), 62F);
		}else{
			target.attackEntityFrom(DamageSource.causeMobDamage(entity), 60F * 5 + 100);
		}
		return (float) (7.0D - entityToTarget.lengthVector());
		
	}

	@Override
	public void beginExecution() {
		entity.getNavigator().noPath();
		finished = false;
	}

	@Override
	public void update() {}

	@Override
	public boolean shouldContinue() {
		return !finished;
	}

	@Override
	public void finishExecution() {}

	@Override
	public int getNextFrame(int frame) {
			
		if (frame > MAX_FRAME)
			finished = true;
		return super.getNextFrame(frame);
	}
}
