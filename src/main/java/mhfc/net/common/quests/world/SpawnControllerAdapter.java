package mhfc.net.common.quests.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import mhfc.net.common.world.area.IArea;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IWorldAccess;

public abstract class SpawnControllerAdapter implements IQuestAreaSpawnController {

	public static class SpawnInformation {
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

	protected class WorldAccessor implements IWorldAccess {

		@Override
		public void markBlockForUpdate(int p_147586_1_, int p_147586_2_, int p_147586_3_) {}

		@Override
		public void markBlockForRenderUpdate(int p_147588_1_, int p_147588_2_, int p_147588_3_) {}

		@Override
		public void markBlockRangeForRenderUpdate(
				int p_147585_1_,
				int p_147585_2_,
				int p_147585_3_,
				int p_147585_4_,
				int p_147585_5_,
				int p_147585_6_) {}

		@Override
		public void playSound(
				String p_72704_1_,
				double p_72704_2_,
				double p_72704_4_,
				double p_72704_6_,
				float p_72704_8_,
				float p_72704_9_) {}

		@Override
		public void playSoundToNearExcept(
				EntityPlayer p_85102_1_,
				String p_85102_2_,
				double p_85102_3_,
				double p_85102_5_,
				double p_85102_7_,
				float p_85102_9_,
				float p_85102_10_) {}

		@Override
		public void spawnParticle(
				String p_72708_1_,
				double p_72708_2_,
				double p_72708_4_,
				double p_72708_6_,
				double p_72708_8_,
				double p_72708_10_,
				double p_72708_12_) {}

		@Override
		public void onEntityCreate(Entity entity) {
			if (!inArea(entity.posX, entity.posY, entity.posZ))
				return;
			SpawnControllerAdapter.this.controlEntity(entity);
		}

		@Override
		public void onEntityDestroy(Entity entity) {
			SpawnControllerAdapter.this.releaseEntity(entity);
		}

		@Override
		public void playRecord(String p_72702_1_, int p_72702_2_, int p_72702_3_, int p_72702_4_) {}

		@Override
		public void broadcastSound(int p_82746_1_, int p_82746_2_, int p_82746_3_, int p_82746_4_, int p_82746_5_) {}

		@Override
		public void playAuxSFX(
				EntityPlayer p_72706_1_,
				int p_72706_2_,
				int p_72706_3_,
				int p_72706_4_,
				int p_72706_5_,
				int p_72706_6_) {}

		@Override
		public void destroyBlockPartially(
				int p_147587_1_,
				int p_147587_2_,
				int p_147587_3_,
				int p_147587_4_,
				int p_147587_5_) {}

		@Override
		public void onStaticEntitiesChanged() {}

	}

	Set<Queue<Entity>> spawnQueues;
	Map<Class<? extends Entity>, Integer> aliveCount;
	Map<Class<? extends Entity>, Integer> spawnMaximum;
	Set<Entity> managedEntities;
	IArea area;

	public SpawnControllerAdapter(IArea area) {
		this.area = area;
		this.spawnQueues = new HashSet<>();
		this.aliveCount = new HashMap<>();
		this.managedEntities = new HashSet<>();
		this.area.getWorldView().addWorldAccess(new WorldAccessor());
	}

	@Override
	public void defaultSpawnsEnabled(boolean defaultSpawnsEnabled) {
		if (defaultSpawnsEnabled)
			enqueDefaultSpawns();
	}

	protected abstract void enqueDefaultSpawns();

	protected abstract SpawnInformation constructDefaultSpawnInformation(Entity entity);

	@Override
	public void spawnEntity(String entityID) {
		Entity entity = EntityList.createEntityByName(entityID, area.getWorldView().getWorldObject());
		spawnEntity(entity);
	}

	@Override
	public void spawnEntity(String entityID, int x, int y, int z) {
		Entity entity = EntityList.createEntityByName(entityID, area.getWorldView().getWorldObject());
		spawnEntity(new SpawnInformation(entity, x, y, z));
	}

	@Override
	public void spawnEntity(Entity entity) {
		spawnEntity(constructDefaultSpawnInformation(entity));
	}

	@Override
	public void spawnEntity(Entity entity, int x, int y, int z) {
		spawnEntity(new SpawnInformation(entity, x, y, z));
	}

	@Override
	public void enqueueSpawns(Queue<Entity> qu) {
		spawnQueues.add(qu);
	}

	@Override
	public void clearQueues() {
		spawnQueues.clear();
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
		spawnMaximum.put(entityclass, maxAmount);
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

	protected boolean inArea(double posX, double posY, double posZ) {
		return area.getWorldView().isManaged(posX, posY, posZ);
	}

	/**
	 * Directly spawns the given entity at the position
	 * 
	 * @param information
	 * @return
	 */
	protected boolean spawnEntity(SpawnInformation information) {
		area.getWorldView().spawnEntityAt(information.entity, information.relX, information.relY, information.relZ);
		return false;
	}

	/**
	 * Directly despawns the entity (if it is managed) and removes it from this manager.
	 * 
	 * @param entity
	 * @return
	 */
	protected boolean despawnEntity(Entity entity) {
		area.getWorldView().removeEntity(entity);
		return releaseEntity(entity);
	}

	/**
	 * This method should be called by all methods spawning an entity
	 */
	protected boolean controlEntity(Entity entity) {
		if (!managedEntities.add(entity))
			return false;

		Class<? extends Entity> clazz = entity.getClass();
		Integer count = aliveCount.getOrDefault(clazz, new Integer(0));
		aliveCount.put(clazz, count.intValue() + 1);
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
		aliveCount.put(clazz, count.intValue() - 1);
		return true;
	}

	@Override
	public void runSpawnCycle() {
		Iterator<Queue<Entity>> it = spawnQueues.iterator();
		while (it.hasNext()) {
			Queue<Entity> spawnQ = it.next();
			Entity firstEnt = spawnQ.peek();
			if (firstEnt == null)
				it.remove();
			Class<? extends Entity> firstClass = firstEnt.getClass();
			if (aliveCount.getOrDefault(firstClass, 0) < spawnMaximum.getOrDefault(firstClass, 0)) {
				spawnQ.poll();
				spawnEntity(firstEnt);
			}
		}
	}

}
