package mhfc.net.common.ai.manager;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import net.minecraft.entity.EntityLiving;

public interface IAIAttackCollection<EntType extends EntityLiving & IManagedActions<EntType>> {

	IExecutableAction<? super EntType> getAction(int index);

	int getIndexOf(IExecutableAction<? super EntType> action);

}
