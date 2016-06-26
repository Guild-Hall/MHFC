package mhfc.net.common.ai.manager.builder;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mhfc.net.common.ai.IExecutableAction;
import mhfc.net.common.ai.IManagedActions;
import mhfc.net.common.ai.manager.AIFollowUpActionManager;
import mhfc.net.common.ai.manager.AIFollowUpActionManager.ChooseAlwaysAdapter;
import mhfc.net.common.ai.manager.AIFollowUpActionManager.DataObject;
import mhfc.net.common.ai.manager.AIFollowUpActionManager.FollowUpChooser;
import mhfc.net.common.util.MapGraph;
import net.minecraft.entity.EntityLiving;

/**
 * A builder for a graph type attack manager. The builder has two distinct types of attacks, strong and weak ones. Weak
 * attacks can only occur after another attack while strong attacks can occur at the beginning or after any permissive
 * previous action.
 * 
 * @author Katora
 *
 * @param <EntType>
 */
public class FollowUpManagerBuilder<EntType extends EntityLiving & IManagedActions<EntType>>
		implements
		IActionManagerBuilder<EntType> {

	protected MapGraph<IExecutableAction<? super EntType>, FollowUpChooser<EntType>> graph = new MapGraph<>();
	protected Map<IExecutableAction<? super EntType>, FollowUpChooser<EntType>> strongDefaultMap = new HashMap<>();

	public FollowUpManagerBuilder() {
		registerActionWeak(null);
	}

	/**
	 * Registers a new strong action without any allowed follow ups.
	 */
	@Override
	public void registerAction(IExecutableAction<? super EntType> attack) {
		registerActionStrong(attack);
	}

	private void registerActionStrong(IExecutableAction<? super EntType> action) {
		graph.addNode(action);
		allowFollowUp(null, action);
		for (Entry<IExecutableAction<? super EntType>, FollowUpChooser<EntType>> defEntry : strongDefaultMap
				.entrySet()) {
			setFollowUpChooser(defEntry.getKey(), action, defEntry.getValue());
		}
	}

	private void registerActionWeak(IExecutableAction<? super EntType> action) {
		graph.addNode(action);
	}

	/**
	 * The action will allow all current and all future <b>strong</b> actions.
	 */
	public void registerAllowingAllActions(IExecutableAction<? super EntType> action) {
		registerActionStrong(action);
		allowAllStrongActions(action);
		allowAllFutureActions(action);
	}

	/**
	 * Allows all other currently registered actions to occur after the action
	 */
	public void allowAllStrongActions(IExecutableAction<? super EntType> action) {
		for (IExecutableAction<? super EntType> followUp : strongDefaultMap.keySet()) {
			graph.addEdge(action, followUp, ChooseAlwaysAdapter.<EntType>getInstance());
		}
	}

	/**
	 * Allows all future strong actions to occur after the action.
	 */
	public void allowAllFutureActions(IExecutableAction<? super EntType> action) {
		strongDefaultMap.put(action, ChooseAlwaysAdapter.<EntType>getInstance());
	}

	/**
	 * Register an attack with a set of allowed follow up actions.<br>
	 * If the actions in the collections are not yet registered, they will be registered as weak actions.
	 */
	public void registerActionWithFollowUps(
			IExecutableAction<? super EntType> action,
			List<IExecutableAction<? super EntType>> followUps) {
		registerActionStrong(action);
		registerFollowUps(action, followUps);
	}

	/**
	 * Registers and allows all actions in followUps as weak actions if they are not already registered as strong
	 * actions.
	 */
	public void registerFollowUps(
			IExecutableAction<? super EntType> attack,
			Collection<IExecutableAction<? super EntType>> followUps) {
		for (IExecutableAction<? super EntType> action : followUps) {
			registerActionWeak(action);
			allowFollowUp(attack, action);
		}
	}

	public void allowFollowUp(IExecutableAction<? super EntType> attack, IExecutableAction<? super EntType> following) {
		setFollowUpChooser(attack, following, ChooseAlwaysAdapter.<EntType>getInstance());
	}

	public void setFollowUpChooser(
			IExecutableAction<? super EntType> attack,
			IExecutableAction<? super EntType> following,
			FollowUpChooser<EntType> chooser) {
		graph.addEdge(attack, following, chooser);
	}

	public AIFollowUpActionManager<EntType> build(EntType entity) {
		DataObject<EntType> dataObject = new DataObject<>(graph);
		return new AIFollowUpActionManager<EntType>(entity, dataObject);
	}

}
