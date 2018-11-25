package mhfc.net.common.quests.spawns;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.api.ISpawnInformation;
import mhfc.net.common.quests.world.EntityFactory;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.util.LazyQueue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class MonsterSpawn implements ISpawnInformation {

	private final ResourceLocation goalMob;
	private final int goalNumber;
	private final ResourceLocation spawnLocation;

	public MonsterSpawn(ResourceLocation goalMob, ResourceLocation spawnLocation) {
		this(goalMob, -1, spawnLocation);
	}

	public MonsterSpawn(ResourceLocation goalMob, int goalNumber, ResourceLocation spawnLocation) {
		this.goalMob = Objects.requireNonNull(goalMob);
		this.goalNumber = goalNumber;
		this.spawnLocation = spawnLocation;
	}

	private EntityFactory getEntityFactory() {
		return (world, area) -> {
			Entity entity = EntityList.createEntityByIDFromName(goalMob, world);
			if (entity == null) {
				MHFCMain.logger().error("Couldn't create entity for id {}", goalMob);
				return null;
			}
			BlockPos position = area.resolveMonsterSpawn(spawnLocation);
			entity.setPosition(position.getX(), position.getY(), position.getZ());
			return entity;
		};
	}

	@Override
	public void enqueueSpawns(IQuestAreaSpawnController spawnController) {
		EntityFactory creation = getEntityFactory();
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

	public ResourceLocation getSpawnLocation() {
		return spawnLocation;
	}

}
