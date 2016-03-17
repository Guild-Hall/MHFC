package mhfc.net.common.ai.general.actions;

import mhfc.net.common.ai.general.provider.composite.ISpinProvider;
import mhfc.net.common.entity.type.EntityMHFCBase;

public abstract class AIGeneralTailWhip<EntityT extends EntityMHFCBase<? super EntityT>>
		extends
		AIGeneralAttack<EntityT> implements ISpinProvider<EntityT> {

}
