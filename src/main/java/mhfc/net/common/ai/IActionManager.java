package mhfc.net.common.ai;

import net.minecraft.entity.EntityLiving;

import com.github.worldsender.mcanm.client.model.mcanmmodel.animation.IAnimation;

public interface IActionManager<EntityT extends EntityLiving & IManagedActions<EntityT>> {

	public void registerAttack(IExecutableAction<? super EntityT> attack);

	public IAnimation getCurrentAnimation();

	public int getCurrentFrame();

	public IExecutableAction<? super EntityT> chooseAttack();
}
