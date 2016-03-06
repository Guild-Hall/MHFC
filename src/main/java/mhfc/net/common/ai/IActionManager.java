package mhfc.net.common.ai;

import net.minecraft.entity.EntityLiving;

public interface IActionManager<EntityT extends EntityLiving & IManagedActions<EntityT>>
		extends
		IActionManagerView<EntityT> {

	public void switchToAction(IExecutableAction<? super EntityT> attack);

	public boolean continueExecuting();

	public void updateTask();

}
