package mhfc.net.common.ai.manager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.mojang.realmsclient.util.Pair;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.ai.general.WeightedPick;
import mhfc.net.common.ai.manager.AIFollowUpActionManager.DataObject;
import mhfc.net.common.util.MapGraph;
import net.minecraft.entity.EntityLiving;

/**
 * An action manager who selects the new attack only from a set of follow up actions specific to the last action.
 */
public class AIFollowUpActionManager<EntType extends EntityLiving & IManagedActions<EntType>>
		extends
		ActionManagerAdapter<EntType, DataObject<EntType>> {

	public static class DataObject<EType extends EntityLiving & IManagedActions<EType>>
			implements
			IAIAttackCollection<EType> {

		protected final MapGraph<IExecutableAction<? super EType>, FollowUpChooser<EType>> graph;

		public DataObject(MapGraph<IExecutableAction<? super EType>, FollowUpChooser<EType>> graph) {
			this.graph = graph;
		}

		public Set<IExecutableAction<? super EType>> getNodes() {
			return graph.getNodes();
		}

		public FollowUpChooser<EType> getValue(
				Pair<IExecutableAction<? super EType>, IExecutableAction<? super EType>> key) {
			return graph.getValue(key);
		}

		public Set<Pair<IExecutableAction<? super EType>, FollowUpChooser<EType>>> getOutbound(
				IExecutableAction<? super EType> n) {
			return graph.getOutbound(n);
		}

		@Override
		public int getIndexOf(IExecutableAction<? super EType> n) {
			return graph.indexOf(n);
		}

		@Override
		public IExecutableAction<? super EType> getAction(int index) {
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

	public AIFollowUpActionManager(EntType entity, DataObject<EntType> dataObject) {
		super(entity, dataObject);
		this.attackCollection.getNodes().stream().forEach((a) -> {
			if (a != null) {
				a.rebind(entity);
			}
		});
	}

	protected List<IExecutableAction<? super EntType>> getFollowUpList(IExecutableAction<? super EntType> action) {
		Set<Pair<IExecutableAction<? super EntType>, FollowUpChooser<EntType>>> followUps = attackCollection
				.getOutbound(action);
		return followUps.stream().filter((pair) -> pair.second().shouldChoose(this.entity)).map(Pair::first)
				.collect(Collectors.toList());
	}

	@Override
	public IExecutableAction<? super EntType> chooseAttack() {
		List<IExecutableAction<? super EntType>> followUps = getFollowUpList(activeAttack);
		return WeightedPick.pickRandom(followUps);
	}

}
