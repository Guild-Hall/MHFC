package mhfc.net.common.quests.goals;

import java.util.Collection;
import java.util.Objects;

import mhfc.net.common.eventhandler.quests.LivingDeathEventHandler;
import mhfc.net.common.eventhandler.quests.NotifyableQuestGoal;
import mhfc.net.common.eventhandler.quests.QuestGoalEventHandler;
import mhfc.net.common.quests.Mission;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.properties.IntProperty;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathRestrictionQuestGoal extends QuestGoal implements NotifyableQuestGoal<LivingDeathEvent> {

	protected IntProperty maxDeaths;
	protected IntProperty currentDeaths;
	protected QuestGoalEventHandler<LivingDeathEvent> handler;

	/**
	 * A new death count restriction is created with a maximum amount of deaths from players in the overlaying quest. If
	 * there is no quest above then all players are taken in account.
	 *
	 * @param propertyGroup
	 *            the property group to derive properties from
	 *
	 * @param maxDeaths
	 *            How often players are allowed to die, inclusive
	 */
	public DeathRestrictionQuestGoal(IntProperty maxDeaths, IntProperty currentDeaths) {
		this.maxDeaths = Objects.requireNonNull(maxDeaths);
		this.currentDeaths = Objects.requireNonNull(currentDeaths);
		handler = new LivingDeathEventHandler(this);
		handler.setActive(false);
		MinecraftForge.EVENT_BUS.register(handler);
	}

	@Override
	public boolean isFulfilled() {
		return maxDeaths.get() >= currentDeaths.get();
	}

	@Override
	public boolean isFailed() {
		return maxDeaths.get() < currentDeaths.get();
	}

	@Override
	public void reset() {
		currentDeaths.set(0);
	}

	@Override
	public void setActive(boolean newActive) {
		handler.setActive(newActive);
	}

	@Override
	public void questGoalFinalize() {
		MinecraftForge.EVENT_BUS.unregister(handler);
	}

	@Override
	public void notifyOfEvent(LivingDeathEvent event) {
		Mission quest = getMission();
		if (quest != null) {
			Collection<EntityPlayerMP> players = quest.getPlayers();
			if (players != null && players.contains(event.entityLiving)) {
				currentDeaths.inc();
			}
		}
		notifyOfStatus(isFulfilled(), isFailed());
	}
}
