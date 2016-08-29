package mhfc.net.common.quests.goals;

import java.util.Objects;
import java.util.stream.Stream;

import mhfc.net.common.eventhandler.quests.LivingDeathEventHandler;
import mhfc.net.common.eventhandler.quests.NotifyableQuestGoal;
import mhfc.net.common.eventhandler.quests.QuestGoalEventHandler;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;
import mhfc.net.common.quests.properties.IntProperty;
import mhfc.net.common.quests.world.SpawnControllerAdapter.Spawnable;
import mhfc.net.common.util.LazyQueue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class HuntingQuestGoal extends QuestGoal implements NotifyableQuestGoal<LivingDeathEvent> {

	private LazyQueue<Spawnable> infSpawns;
	private IntProperty goalNumber;
	private IntProperty currentNumber;
	private Class<? extends Entity> goalClass;
	private QuestGoalEventHandler<LivingDeathEvent> goalHandler;

	public HuntingQuestGoal(
			QuestGoalSocket socket,
			Class<? extends Entity> goalClass,
			IntProperty goalNumber,
			IntProperty currentNumber) {
		super(socket);
		this.goalClass = Objects.requireNonNull(goalClass);
		this.goalNumber = Objects.requireNonNull(goalNumber);
		this.currentNumber = Objects.requireNonNull(currentNumber);

		goalHandler = new LivingDeathEventHandler(this);
		MinecraftForge.EVENT_BUS.register(goalHandler);
		String goalMob = (String) EntityList.classToStringMapping.get(goalClass);
		Spawnable creation = (world) -> EntityList.createEntityByName(goalMob, world);
		Stream<Spawnable> generator = Stream.generate(() -> creation);
		infSpawns = new LazyQueue<>(generator.iterator());
	}

	@Override
	public boolean isFulfilled() {
		return currentNumber.get() >= goalNumber.get();
	}

	@Override
	public boolean isFailed() {
		return false;
	}

	@Override
	public void reset() {
		currentNumber.set(0);
	}

	@Override
	public void questGoalFinalize() {
		MinecraftForge.EVENT_BUS.unregister(goalHandler);
	}

	@Override
	public void notifyOfEvent(LivingDeathEvent event) {
		if (goalClass.isAssignableFrom(event.entityLiving.getClass())) {
			Entity damageSource = event.source.getEntity();
			if ((damageSource instanceof EntityPlayer) && getMission() != null) {
				boolean shouldcount = true;
				shouldcount &= getMission().getPlayers().contains(damageSource);
				shouldcount &= getMission().getSpawnController().getControlledEntities().contains(event.entity);
				if (shouldcount) {
					currentNumber.inc();
				}
			}
			notifyOfStatus(isFulfilled(), isFailed());
		}
	}

	@Override
	public void setActive(boolean newActive) {
		goalHandler.setActive(newActive);
		if (newActive) {
			getMission().getSpawnController().enqueueSpawns(infSpawns);
			getMission().getSpawnController().setGenerationMaximum(goalClass, goalNumber.get());
		} else {
			getMission().getSpawnController().dequeueSpawns(infSpawns);
		}
	}
}
