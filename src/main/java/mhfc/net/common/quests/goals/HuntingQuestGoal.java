package mhfc.net.common.quests.goals;

import java.util.stream.Stream;

import mhfc.net.client.quests.QuestRunningInformation.InformationType;
import mhfc.net.common.eventhandler.quests.LivingDeathEventHandler;
import mhfc.net.common.eventhandler.quests.NotifyableQuestGoal;
import mhfc.net.common.eventhandler.quests.QuestGoalEventHandler;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;
import mhfc.net.common.quests.world.SpawnControllerAdapter.Spawnable;
import mhfc.net.common.util.LazyQueue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class HuntingQuestGoal extends QuestGoal implements NotifyableQuestGoal<LivingDeathEvent> {

	LazyQueue<Spawnable> infSpawns;

	public HuntingQuestGoal(QuestGoalSocket socket, Class<? extends Entity> goalClass, int goalNumber) {
		super(socket);
		this.goalClass = goalClass;
		this.goalNumber = goalNumber;
		this.currentNumber = 0;
		goalHandler = new LivingDeathEventHandler(this);
		MinecraftForge.EVENT_BUS.register(goalHandler);
		String goalMob = (String) EntityList.classToStringMapping.get(goalClass);
		Spawnable creation = (world) -> EntityList.createEntityByName(goalMob, world);
		Stream<Spawnable> generator = Stream.generate(() -> creation);
		infSpawns = new LazyQueue<>(generator.iterator());
	}

	private int goalNumber;
	private int currentNumber;
	private Class<? extends Entity> goalClass;
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
			Entity damageSource = event.source.getEntity();
			if ((damageSource instanceof EntityPlayer) && getQuest() != null) {
				boolean shouldcount = true;
				shouldcount &= getQuest().getPlayers().contains(damageSource);
				shouldcount &= getQuest().getSpawnController().getControlledEntities().contains(event.entity);
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
		if (newActive) {
			getQuest().getSpawnController().enqueueSpawns(infSpawns);
			getQuest().getSpawnController().setGenerationMaximum(goalClass, goalNumber);
		} else {
			getQuest().getSpawnController().dequeueSpawns(infSpawns);
		}
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
