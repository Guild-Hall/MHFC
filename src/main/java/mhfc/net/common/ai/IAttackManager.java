package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;

import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation;

public interface IAttackManager<EntityT extends EntityLivingBase & IManagedAttacks<EntityT>> {

	public void registerAttack(IExecutableAttack<EntityT> attack);

	public IAnimation getCurrentAnimation();

	public int getNextFrame(int current);

	public IExecutableAttack<EntityT> chooseAttack();
}
