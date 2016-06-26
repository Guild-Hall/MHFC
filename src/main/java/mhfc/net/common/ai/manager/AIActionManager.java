package mhfc.net.common.ai.manager;

import java.util.List;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.ai.general.WeightedPick;
import mhfc.net.common.ai.manager.AIActionManager.DataObject;
import net.minecraft.entity.EntityLiving;

public class AIActionManager<EntType extends EntityLiving & IManagedActions<EntType>>
		extends
		ActionManagerAdapter<EntType, DataObject<EntType>> {

	public static class DataObject<EType extends EntityLiving & IManagedActions<EType>>
			implements
			IAIAttackCollection<EType> {
		protected final List<IExecutableAction<? super EType>> actions;

		public DataObject(List<IExecutableAction<? super EType>> actions) {
			this.actions = actions;
		}

		@Override
		public IExecutableAction<? super EType> getAction(int index) {
			return actions.get(index);
		}

		@Override
		public int getIndexOf(IExecutableAction<? super EType> attack) {
			return actions.indexOf(attack);
		}
	}

	public AIActionManager(EntType entity, DataObject<EntType> dataObject) {
		super(entity, dataObject);
		this.attackCollection.actions.stream().forEach((a) -> a.rebind(entity));
	}

	@Override
	public IExecutableAction<? super EntType> chooseAttack() {
		return WeightedPick.pickRandom(attackCollection.actions);
	}
}
