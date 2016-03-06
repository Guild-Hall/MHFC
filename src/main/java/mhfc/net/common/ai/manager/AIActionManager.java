package mhfc.net.common.ai.manager;

import java.util.List;
import java.util.Objects;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.ai.general.WeightedPick;
import mhfc.net.common.network.message.MessageAIAction;
import net.minecraft.entity.EntityLiving;

public class AIActionManager<EntType extends EntityLiving & IManagedActions<EntType>>
		extends
		ActionManagerAdapter<EntType> {
	public static class DataObject<EntType extends EntityLiving & IManagedActions<EntType>> {
		protected final List<IExecutableAction<? super EntType>> actions;

		public DataObject(List<IExecutableAction<? super EntType>> actions) {
			this.actions = actions;
		}

		IExecutableAction<? super EntType> getAction(int index) {
			return actions.get(index);
		}

		int getIndexOf(IExecutableAction<? super EntType> attack) {
			return actions.indexOf(attack);
		}
	}

	protected final DataObject<EntType> dataObject;

	public AIActionManager(EntType entity, DataObject<EntType> dataObject) {
		super(entity);
		this.dataObject = Objects.requireNonNull(dataObject);
		this.dataObject.actions.stream().forEach((a) -> a.rebind(entity));
	}

	@Override
	public IExecutableAction<? super EntType> chooseAttack() {
		return WeightedPick.pickRandom(dataObject.actions);
	}

	protected MessageAIAction<EntType> sendUpdate() {
		return new MessageAIAction<EntType>(this.entity, this.dataObject.getIndexOf(activeAttack));
	}

	@Override
	public void switchToAction(IExecutableAction<? super EntType> attack) {
		if (dataObject.getIndexOf(attack) < 0)
			throw new IllegalArgumentException("Only registered actions may be used");
		swapAttacks(activeAttack, attack);
	}

	@Override
	public void receiveUpdate(MessageAIAction<EntType> message) {
		IExecutableAction<? super EntType> action = dataObject.getAction(message.getAttackIndex());
		switchAction(action);
	}
}
