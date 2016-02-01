package mhfc.net.common.quests.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import mhfc.net.common.world.area.IArea;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public abstract class SpawnControllerAdapter implements IQuestAreaSpawnController {

	protected static class SpawnInformation {
		private Entity entity;
		private int relX, relY, relZ;

		public SpawnInformation(Entity entity, int relX, int relY, int relZ) {
			this.entity = entity;
			this.relX = relX;
			this.relY = relY;
			this.relZ = relZ;
		}

		public Entity getEntity() {
			return entity;
		}

		public void setEntity(Entity entity) {
			this.entity = entity;
		}

		public int getRelX() {
			return relX;
		}

		public void setRelX(int relX) {
			this.relX = relX;
		}

		public int getRelY() {
			return relY;
		}

		public void setRelY(int relY) {
			this.relY = relY;
		}

		public int getRelZ() {
			return relZ;
		}

		public void setRelZ(int relZ) {
			this.relZ = relZ;
		}

	}

	List<Queue<Entity>> spawnQueues;
	Map<Class<? extends Entity>, Integer> aliveCount;
	Set<Entity> managedEntities;
	IArea area;

	public SpawnControllerAdapter(IArea area) {
		this.area = area;
		this.spawnQueues = new ArrayList<>();
		this.aliveCount = new HashMap<>();
		this.managedEntities = new HashSet<>();
	}

	@Override
	public void defaultSpawnsEnabled(boolean defaultSpawnsEnabled) {
		if (defaultSpawnsEnabled)
			enqueDefaultSpawns();
	}

	protected abstract void enqueDefaultSpawns();

	@Override
	public void spawnEntity(String entityID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnEntity(String entityID, int x, int y, int z) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnEntity(Entity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void spawnEntity(Entity entity, int relX, int relY, int relZ) {
		spawnEntity(new SpawnInformation(entity, relX, relY, relZ));
	}

	@Override
	public void enqueueSpawns(Queue<Entity> qu) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setGenerationMaximum(String entityID, int maxAmount) {
		@SuppressWarnings("unchecked")
		Class<? extends Entity> clazz = (Class<? extends Entity>) EntityList.stringToClassMapping.get(entityID);
		if (clazz == null)
			return;
		setGenerationMaximum(clazz, maxAmount);
	}

	@Override
	public <T extends Entity> void setGenerationMaximum(Class<T> entityclass, int maxAmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public int clearArea() {
		List<Entity> allEntities = new ArrayList<>(managedEntities);
		return allEntities.stream().filter((e) -> despawnEntity(e)).collect(Collectors.counting()).intValue();
	}

	@Override
	public int clearAreaOf(String entityClassID) {

		@SuppressWarnings("unchecked") // This cast should be safe, EntityList does it itself
		Class<? extends Entity> clazz = (Class<? extends Entity>) EntityList.stringToClassMapping.get(entityClassID);
		if (clazz == null)
			return 0;
		List<Entity> allEntities = new ArrayList<>(managedEntities);

		return allEntities.stream().filter((e) -> clazz.isInstance(e)).filter((e) -> despawnEntity(e))
				.collect(Collectors.counting()).intValue();
	}

	protected boolean inArea(int relX, int relY, int relZ) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Directly spawns the given entity at the position
	 * 
	 * @param information
	 * @return
	 */
	protected boolean spawnEntity(SpawnInformation information) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Directly despawns the entity (if it is managed) and removes it from this manager.
	 * 
	 * @param entity
	 * @return
	 */
	protected boolean despawnEntity(Entity entity) {
		// TODO Auto-generated method stub
		return releaseEntity(entity);
	}

	/**
	 * This method should be called by all methods spawning an entity
	 */
	protected boolean controlEntity(Entity entity) {
		if (!managedEntities.add(entity))
			return false;

		Class<? extends Entity> clazz = entity.getClass();
		Integer count = aliveCount.get(clazz);
		if (count == null)
			count = new Integer(0);
		count = new Integer(count.intValue() + 1);
		aliveCount.put(clazz, count);
		return true;
	}

	/**
	 * This method should be called by all methods removing entity from the area. It only handles the internal lists and
	 * maps, not the actual world.
	 */
	protected boolean releaseEntity(Entity entity) {
		if (!managedEntities.remove(entity))
			return false;

		Class<? extends Entity> clazz = entity.getClass();
		Integer count = aliveCount.get(clazz);
		count = new Integer(count.intValue() - 1);
		aliveCount.put(clazz, count);
		return true;
	}

}
