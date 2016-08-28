package mhfc.net.common.quests.api;

import com.google.common.base.Preconditions;

import mhfc.net.client.quests.api.IActiveGoalInformation;
import mhfc.net.common.quests.properties.GroupProperty;
import mhfc.net.common.util.stringview.Viewable;

/**
 * A stateful factory that is able to build either a {@link QuestGoal} or an {@link IActiveGoalInformation}. First,
 * {@link #bindAttributes(GroupProperty)} is invoked, and then either {@link #build()} or {@link #buildVisual()}.
 *
 * @author WorldSEnder
 *
 */
public interface IGoalFactory {

	public abstract IGoalFactory bindAttributes(GroupProperty goalProperties);

	/**
	 * Offers a chance to bind visual supplements to the (saved) GroupProperty that was given in
	 * {@link #bindAttributes(GroupProperty)}.
	 *
	 * @return this
	 */
	default IGoalFactory bindVisualSupplements() {
		return this;
	}

	public abstract Viewable buildVisual();

	public abstract Viewable buildShortStatus();

	public abstract QuestGoal build();

	boolean areAttributesBound();

	default void checkAttributesBound() {
		Preconditions.checkState(areAttributesBound(), "bindAttributes first");
	}
}
