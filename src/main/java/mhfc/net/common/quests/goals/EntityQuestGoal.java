package mhfc.net.common.quests.goals;

import mhfc.net.common.eventhandler.quests.NotifyableQuestGoal;
import mhfc.net.common.eventhandler.quests.QuestGoalEventHandler;
import mhfc.net.common.quests.QuestGoalSocket;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
/**
 * A quest goal that is based on killing a specific entity.
 */
public class EntityQuestGoal extends QuestGoal
		implements
			NotifyableQuestGoal<LivingDeathEvent> {
	private EntityLivingBase entity;
	private boolean died;
	private QuestGoalEventHandler<LivingDeathEvent> eventHandler;
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
		died = !entity.isEntityAlive();
		eventHandler = new QuestGoalEventHandler<LivingDeathEvent>(this);
		MinecraftForge.EVENT_BUS.register(eventHandler);
	}
	@Override
	public boolean isFulfilled() {
		return died;
	}

	@Override
	public boolean isFailed() {
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void questGoalFinalize() {
		MinecraftForge.EVENT_BUS.unregister(eventHandler);
	}

	@Override
	public void setActive(boolean newActive) {
		if (newActive)
			died = entity.isEntityAlive();
		eventHandler.setActive(newActive);
	}

	@Override
	public void notifyOfEvent(LivingDeathEvent event) {
		if (event.entityLiving == entity) {
			died = true;
			notifyOfStatus(true, false);
		}
	}

}
