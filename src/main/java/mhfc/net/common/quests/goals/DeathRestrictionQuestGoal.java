package mhfc.net.common.quests.goals;

import java.util.Arrays;

import mhfc.net.common.eventhandler.quests.LivingDeathEventHandler;
import mhfc.net.common.eventhandler.quests.NotifyableQuestGoal;
import mhfc.net.common.eventhandler.quests.QuestGoalEventHandler;
import mhfc.net.common.quests.GeneralQuest;
import mhfc.net.common.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.quests.api.QuestGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathRestrictionQuestGoal extends QuestGoal
	implements
		NotifyableQuestGoal<LivingDeathEvent> {

	protected int maxDeaths;
	protected int currentDeaths;
	protected QuestGoalEventHandler<LivingDeathEvent> handler;

	/**
	 * A new death count restriction is created with a maximum amount of deaths
	 * from players in the overlaying quest. If there is no quest above then all
	 * players are taken in account.
	 *
	 * @param maxDeaths
	 *            How often players are allowed to die, inclusive
	 */
	public DeathRestrictionQuestGoal(int maxDeaths) {
		this.maxDeaths = maxDeaths;
		currentDeaths = 0;
		handler = new LivingDeathEventHandler(this);
		handler.setActive(false);
		MinecraftForge.EVENT_BUS.register(handler);
	}

	@Override
	public boolean isFulfilled() {
		return maxDeaths >= currentDeaths;
	}

	@Override
	public boolean isFailed() {
		return maxDeaths < currentDeaths;
	}

	@Override
	public void reset() {
		currentDeaths = 0;
	}

	@Override
	public void setActive(boolean newActive) {
		handler.setActive(newActive);
	}

	@Override
	public void questGoalFinalize() {
		if (handler != null)
			MinecraftForge.EVENT_BUS.unregister(handler);
	}

	@Override
	public void notifyOfEvent(LivingDeathEvent event) {
		GeneralQuest quest = getQuest();
		if (quest == null) {

		} else {
			EntityPlayer[] players = quest.getPlayers();
			if (players != null
				&& Arrays.asList(players).contains(event.entityLiving)) {
				++currentDeaths;
			}
		}
		notifyOfStatus(isFulfilled(), isFailed());
	}

	@Override
	public String modify(InformationType type, String current) {
		if (type == InformationType.LongStatus) {
			current += (current.equals("") ? "" : "\n")
				+ (maxDeaths - currentDeaths) + " respawns left";
		} else if (type == InformationType.ShortStatus) {
			current += (current.equals("") ? "" : "\n")
				+ (maxDeaths - currentDeaths) + " respawns";
		}
		return current;
	}
}
