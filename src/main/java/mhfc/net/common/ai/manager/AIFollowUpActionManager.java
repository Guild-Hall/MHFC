package mhfc.net.common.ai.manager;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.mojang.realmsclient.util.Pair;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.ai.general.WeightedPick;
import mhfc.net.common.network.message.MessageAIAction;
import mhfc.net.common.util.MapGraph;
import net.minecraft.entity.EntityLiving;

/**
 * An action manager who selects the new attack only from a set of follow up actions specific to the last action.
 */
public class AIFollowUpActionManager<EntType extends EntityLiving & IManagedActions<EntType>>
		extends
		ActionManagerAdapter<EntType> {

	public static class DataObject<EntType extends EntityLiving & IManagedActions<EntType>> {

		protected final MapGraph<IExecutableAction<? super EntType>, FollowUpChooser<EntType>> graph;

		public DataObject(MapGraph<IExecutableAction<? super EntType>, FollowUpChooser<EntType>> graph) {
			this.graph = graph;
		}

		public Set<IExecutableAction<? super EntType>> getNodes() {
			return graph.getNodes();
		}

		public FollowUpChooser<EntType> getValue(
				Pair<IExecutableAction<? super EntType>, IExecutableAction<? super EntType>> key) {
			return graph.getValue(key);
		}

		public Set<Pair<IExecutableAction<? super EntType>, FollowUpChooser<EntType>>> getOutbound(
				IExecutableAction<? super EntType> n) {
			return graph.getOutbound(n);
		}

		public int indexOf(IExecutableAction<? super EntType> n) {
			return graph.indexOf(n);
		}

		public IExecutableAction<? super EntType> get(int index) {
			return graph.get(index);
		}

	}

	public static interface FollowUpChooser<EntType extends EntityLiving & IManagedActions<EntType>> {
		public boolean shouldChoose(EntType entity);
	}

	public static class ChooseAlwaysAdapter<EntType extends EntityLiving & IManagedActions<EntType>>
			implements
			FollowUpChooser<EntType> {

		private static final ChooseAlwaysAdapter<?> instance = new ChooseAlwaysAdapter<>();

		@SuppressWarnings("unchecked")
		public static final <EntType extends EntityLiving & IManagedActions<EntType>> ChooseAlwaysAdapter<EntType> getInstance() {
			return (ChooseAlwaysAdapter<EntType>) instance;
		}

		private ChooseAlwaysAdapter() {}

		@Override
		public boolean shouldChoose(EntType entity) {
			return true;
		}

	}

	protected final DataObject<EntType> dataObject;

	public AIFollowUpActionManager(EntType entity, DataObject<EntType> dataObject) {
		super(entity);
		this.dataObject = Objects.requireNonNull(dataObject);
		this.dataObject.getNodes().stream().forEach((a) -> {
			if (a != null) {
				a.rebind(entity);
			}
		});
	}

	protected List<IExecutableAction<? super EntType>> getFollowUpList(IExecutableAction<? super EntType> action) {
		Set<Pair<IExecutableAction<? super EntType>, FollowUpChooser<EntType>>> followUps = dataObject
				.getOutbound(action);
		return followUps.stream().filter((pair) -> pair.second().shouldChoose(this.entity)).map(Pair::first)
				.collect(Collectors.toList());
	}

	@Override
	public IExecutableAction<? super EntType> chooseAttack() {
		List<IExecutableAction<? super EntType>> followUps = getFollowUpList(activeAttack);
		return WeightedPick.pickRandom(followUps);
	}

	@Override
	public void switchToAction(IExecutableAction<? super EntType> action) {
		if (dataObject.indexOf(action) < 0)
			throw new IllegalArgumentException("Can only switch to registered attacks");
		switchAction(action);
	}

	@Override
	protected MessageAIAction<EntType> sendUpdate() {
		return new MessageAIAction<>(entity, dataObject.indexOf(activeAttack));
	}

	@Override
	public void receiveUpdate(MessageAIAction<EntType> message) {
		IExecutableAction<? super EntType> action = dataObject.get(message.getAttackIndex());
		switchAction(action);
	}

}
