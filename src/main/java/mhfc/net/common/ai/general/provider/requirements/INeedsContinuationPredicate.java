package mhfc.net.common.ai.general.provider.requirements;

import mhfc.net.common.ai.general.provider.simple.IContinuationPredicate;

public interface INeedsContinuationPredicate {
	/**
	 * Called before execution starts to determine when to end it
	 *
	 * @return
	 */
	IContinuationPredicate provideContinuationPredicate();
}
