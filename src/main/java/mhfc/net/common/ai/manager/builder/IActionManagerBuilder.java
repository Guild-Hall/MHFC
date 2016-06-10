package mhfc.net.common.ai.manager.builder;

import mhfc.net.common.ai.IActionManager;
import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import net.minecraft.entity.EntityLiving;

public interface IActionManagerBuilder<EntType extends EntityLiving & IManagedActions<EntType>> {

	public IActionManager<EntType> build(EntType entity);

	void registerAction(IExecutableAction<? super EntType> attack);

}
