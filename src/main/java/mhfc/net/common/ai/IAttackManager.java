package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation;

public interface IAttackManager<E extends EntityLivingBase & IManagedAttacks<E>, //
AI extends EntityAIBase & IAttackManager<E, AI>> {

	public AI getEntityAI();

	public void registerAttack(IExecutableAttack<E> attack);

	public IAnimation getCurrentAnimation();

	public int getNextFrame(int current);

	public IExecutableAttack<E> chooseAttack();
}
