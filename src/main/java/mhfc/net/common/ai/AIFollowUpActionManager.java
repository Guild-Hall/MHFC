package mhfc.net.common.ai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mhfc.net.common.ai.general.WeightedPick;
import net.minecraft.entity.EntityLiving;

/**
 * An action manager who selects the new attack only from a set of follow up
 * actions specific to the last action.
 */
public class AIFollowUpActionManager<EntType extends EntityLiving & IManagedActions<EntType>>
	extends
		AIActionManager<EntType> {

	protected Map<IExecutableAction<? super EntType>, List<IExecutableAction<? super EntType>>> allowedFollowUps;

	public AIFollowUpActionManager(EntType entity) {
		super(entity);
		allowedFollowUps = new HashMap<>();
	}

	/**
	 * Registers a new attack which allows all other actions to occur after it
	 */
	@Override
	public void registerAttack(IExecutableAction<? super EntType> attack) {
		registerAttack(attack, null);
	}

	/**
	 * Register an attack with a set of allowed follow up actions.<br>
	 * To allow every action, provide null, to allow no action to follow up,
	 * provide an empty list.<br>
	 * The list is NOT copied, so don't use it afterwards.
	 */
	public void registerAttack(IExecutableAction<? super EntType> attack,
		List<IExecutableAction<? super EntType>> followUps) {
		super.registerAttack(attack);
		allowedFollowUps.put(attack, followUps);
	}

	@Override
	public IExecutableAction<? super EntType> chooseAttack() {
		List<IExecutableAction<? super EntType>> followUps = allowedFollowUps
			.get(activeAttack);
		if (followUps == null)
			followUps = attacks;
		return WeightedPick.pickRandom(followUps);
	}

}
