package mhfc.net.common.quests;

import net.minecraft.entity.EntityLivingBase;
/**
 * A quest goal that is based on killing a specific entity.
 */
public class EntityQuestGoal extends QuestGoal {
	private EntityLivingBase entity;
	/**
	 * Constructs an {@link EntityQuestGoal} with the given entity. If it is
	 * null, an {@link IllegalArgumentException} is thrown
	 */
	public EntityQuestGoal(QuestGoalSocket socket, EntityLivingBase entity) {
		super(socket);
		if (entity == null)
			throw new IllegalArgumentException(
					"The goal of an EntityQuestGoal can not be null");
		this.entity = entity;
	}
	@Override
	public boolean isFulfilled() {
		return !entity.isEntityAlive();
	}

	@Override
	public boolean isFailed() {
		return false;
	}
	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

}
