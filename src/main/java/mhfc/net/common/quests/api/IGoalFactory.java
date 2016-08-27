package mhfc.net.common.quests.api;

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

	public abstract Viewable buildVisual();

	public abstract QuestGoal build();
}
