package mhfc.net.common.ai.manager.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.ai.manager.AIActionManager;
import mhfc.net.common.ai.manager.AIActionManager.DataObject;
import net.minecraft.entity.EntityLiving;

public class ActionManagerBuilder<EntType extends EntityLiving & IManagedActions<EntType>>
		implements
		IActionManagerBuilder<EntType> {

	List<IExecutableAction<? super EntType>> actions = new ArrayList<>();

	public void registerAction(IExecutableAction<? super EntType> attack) {
		Objects.requireNonNull(attack);
		actions.add(attack);
	}

	public AIActionManager<EntType> build(EntType entity) {
		DataObject<EntType> dataObject = new DataObject<>(actions);
		actions = new ArrayList<>();
		return new AIActionManager<EntType>(entity, dataObject);
	}

}
