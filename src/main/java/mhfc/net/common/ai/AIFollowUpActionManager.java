package mhfc.net.common.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mhfc.net.common.ai.general.WeightedPick;
import net.minecraft.entity.EntityLiving;

/**
 * An action manager who selects the new attack only from a set of follow up actions specific to the last action.
 */
public class AIFollowUpActionManager<EntType extends EntityLiving & IManagedActions<EntType>>
		extends
		AIActionManager<EntType> {

	public static interface FollowUpChooser<EntType extends EntityLiving & IManagedActions<EntType>> {
		public List<IExecutableAction<? super EntType>> collectFollowups(EntType entity);
	}

	public static class FollowUpAdapter<EntType extends EntityLiving & IManagedActions<EntType>>
			implements
			FollowUpChooser<EntType> {

		List<IExecutableAction<? super EntType>> followUps;

		public FollowUpAdapter(List<IExecutableAction<? super EntType>> followUps) {
			this.followUps = followUps;
		}

		@Override
		public List<IExecutableAction<? super EntType>> collectFollowups(EntType entity) {
			return followUps;
		}
	}

	protected Map<IExecutableAction<? super EntType>, FollowUpChooser<EntType>> allowedFollowUps;

	public AIFollowUpActionManager(EntType entity) {
		super(entity);
		allowedFollowUps = new HashMap<>();
	}

	/**
	 * Registers a new attack which allows all other actions to occur after it
	 */
	@Override
	public void registerAttack(IExecutableAction<? super EntType> attack) {
		registerAttack(attack, (List<IExecutableAction<? super EntType>>) null);
	}

	/**
	 * Register an attack with a set of allowed follow up actions.<br>
	 * To allow every action, provide null, to allow no action to follow up, provide an empty list.<br>
	 * The list is NOT copied, so don't use it afterwards.
	 */
	public void registerAttack(
			IExecutableAction<? super EntType> attack,
			List<IExecutableAction<? super EntType>> followUps) {
		registerAttack(attack, new FollowUpAdapter<EntType>(followUps));
	}

	public void registerAttack(IExecutableAction<? super EntType> attack, FollowUpChooser<EntType> chooser) {
		super.registerAttack(attack);
		allowedFollowUps.put(attack, chooser);
	}

	private List<IExecutableAction<? super EntType>> getFollowUpList(IExecutableAction<? super EntType> action) {
		FollowUpChooser<EntType> followUpChooser = allowedFollowUps.get(activeAttack);
		if (followUpChooser == null)
			return attacks;
		List<IExecutableAction<? super EntType>> followUps = followUpChooser.collectFollowups(this.entity);
		if (followUps == null)
			return attacks;
		followUps.forEach((x) -> x.rebind(entity));
		return followUps;
	}

	@Override
	public IExecutableAction<? super EntType> chooseAttack() {
		List<IExecutableAction<? super EntType>> followUps = getFollowUpList(activeAttack);
		return WeightedPick.pickRandom(followUps);
	}

}
