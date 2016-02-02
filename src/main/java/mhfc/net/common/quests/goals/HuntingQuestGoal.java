package mhfc.net.common.quests.goals;

import mhfc.net.common.eventhandler.quests.LivingDeathEventHandler;
import mhfc.net.common.eventhandler.quests.NotifyableQuestGoal;
import mhfc.net.common.eventhandler.quests.QuestGoalEventHandler;
import mhfc.net.common.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class HuntingQuestGoal extends QuestGoal implements NotifyableQuestGoal<LivingDeathEvent> {

	public HuntingQuestGoal(QuestGoalSocket socket, Class<? extends Entity> goalClass, int goalNumber) {
		super(socket);
		this.goalClass = goalClass;
		this.goalNumber = goalNumber;
		this.currentNumber = 0;
		goalHandler = new LivingDeathEventHandler(this);
		MinecraftForge.EVENT_BUS.register(goalHandler);
	}

	private int goalNumber;
	private int currentNumber;
	private Class<?> goalClass;
	private QuestGoalEventHandler<LivingDeathEvent> goalHandler;

	@Override
	public boolean isFulfilled() {
		return currentNumber >= goalNumber;
	}

	@Override
	public boolean isFailed() {
		return false;
	}

	@Override
	public void reset() {
		currentNumber = 0;
	}

	@Override
	public void questGoalFinalize() {
		MinecraftForge.EVENT_BUS.unregister(goalHandler);
	}

	@Override
	public void notifyOfEvent(LivingDeathEvent event) {
		if (goalClass.isAssignableFrom(event.entityLiving.getClass())) {
			// FIXME Redo this so that we check if inside borders
			Entity e = event.source.getEntity();
			if ((e instanceof EntityPlayer) && getQuest() != null) {
				boolean shouldcount = false;
				shouldcount |= getQuest().getPlayers().contains(e);
				// shouldcount |= getQuest().getSpawnController();
				if (shouldcount) {
					++currentNumber;
				}
			}
			notifyOfStatus(isFulfilled(), isFailed());
		}
	}

	@Override
	public void setActive(boolean newActive) {
		goalHandler.setActive(newActive);
	}

	@Override
	public String modify(InformationType type, String current) {
		if (type == InformationType.LongStatus) {
			current += (current.equals("") ? "" : "\n") + "Hunted " + currentNumber + " of " + goalNumber + " "
					+ EntityList.classToStringMapping.get(goalClass);
		} else if (type == InformationType.ShortStatus) {
			current += (current.equals("") ? "" : "\n") + currentNumber + "/" + goalNumber + " "
					+ EntityList.classToStringMapping.get(goalClass);
		}
		return current;
	}

}
