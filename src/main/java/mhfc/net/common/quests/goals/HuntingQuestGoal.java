package mhfc.net.common.quests.goals;

import java.util.Objects;

import mhfc.net.common.eventhandler.quests.LivingDeathEventHandler;
import mhfc.net.common.eventhandler.quests.NotifyableQuestGoal;
import mhfc.net.common.eventhandler.quests.QuestGoalEventHandler;
import mhfc.net.common.quests.api.QuestGoal;
import mhfc.net.common.quests.api.QuestGoalSocket;
import mhfc.net.common.quests.properties.IntProperty;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class HuntingQuestGoal extends QuestGoal implements NotifyableQuestGoal<LivingDeathEvent> {

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
		if (goalClass.isAssignableFrom(event.getEntityLiving().getClass())) {
			Entity damageSource = event.getSource().getTrueSource();
			if ((damageSource instanceof EntityPlayer) && getMission() != null) {
				boolean shouldcount = true;
				shouldcount &= getMission().getPlayerEntities().contains(damageSource);
				shouldcount &= getMission().getSpawnController().getControlledEntities().contains(event.getEntity());
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
	}
}
