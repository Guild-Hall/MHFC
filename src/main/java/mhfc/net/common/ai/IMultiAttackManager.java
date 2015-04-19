package mhfc.net.common.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public interface IMultiAttackManager<E extends EntityLivingBase & IManagedAttacks<E>, //
AIBase extends EntityAIBase & IAttackManager<E, AIBase>, //
AI extends IMultiAttackManager<E, AIBase, AI, Mode>, //
Mode extends Enum<Mode>> {

	public AI getEntityAI(); // This implicitly forces AI to extend AIBase

	public void switchMode(Mode newMode);

	public Mode getCurrentMode();

}
