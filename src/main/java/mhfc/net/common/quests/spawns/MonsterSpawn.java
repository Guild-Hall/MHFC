package mhfc.net.common.quests.spawns;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import mhfc.net.common.quests.api.ISpawnInformation;
import mhfc.net.common.quests.world.EntityFactory;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.util.LazyQueue;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class MonsterSpawn implements ISpawnInformation {

	private final ResourceLocation goalMob;
	private final int goalNumber;

	public MonsterSpawn(ResourceLocation goalMob) {
		this(goalMob, -1);
	}

	public MonsterSpawn(ResourceLocation goalMob, int goalNumber) {
		this.goalMob = Objects.requireNonNull(goalMob);
		this.goalNumber = goalNumber;
	}

	@Override
	public void enqueueSpawns(IQuestAreaSpawnController spawnController) {
		EntityFactory creation = (world, area) -> EntityList.createEntityByIDFromName(goalMob, world);
		Stream<EntityFactory> generator = Stream.generate(() -> creation);
		if (goalNumber > 0) {
			generator = generator.limit(goalNumber);
		}
		LazyQueue<EntityFactory> spawnQueue = new LazyQueue<>(generator.iterator());

		spawnController.enqueueSpawns(spawnQueue);
	}

	public ResourceLocation getSpawnType() {
		return goalMob;
	}

	public Optional<Integer> getSpawnCount() {
		return goalNumber > 0 ? Optional.of(Integer.valueOf(goalNumber)) : Optional.empty();
	}

}
