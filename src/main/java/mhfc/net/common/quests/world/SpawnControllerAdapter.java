package mhfc.net.common.quests.world;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IWorldView;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;

public abstract class SpawnControllerAdapter implements IQuestAreaSpawnController {

	public static interface Spawnable extends Function<World, Entity> {}

	public static class SpawnInformation {
		private Spawnable spawnable;
		private double relX, relY, relZ;

		public SpawnInformation(Spawnable entity, double relX, double relY, double relZ) {
			this.spawnable = entity;
			this.relX = relX;
			this.relY = relY;
			this.relZ = relZ;
		}

		public Spawnable getEntity() {
			return spawnable;
		}

		public void setEntity(Spawnable entity) {
			this.spawnable = entity;
		}

		public double getRelX() {
			return relX;
		}

		public void setRelX(double relX) {
			this.relX = relX;
		}

		public double getRelY() {
			return relY;
		}

		public void setRelY(double relY) {
			this.relY = relY;
		}

		public double getRelZ() {
			return relZ;
		}

		public void setRelZ(double relZ) {
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
			Vec3 pos = Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ);
			pos = SpawnControllerAdapter.this.worldView.convertToLocal(pos);
			if (!inArea(pos.xCoord, pos.yCoord, pos.zCoord))
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

	protected class TickerEntity extends TileEntity {
		public TickerEntity() {
			super();
			xCoord = 0;
			yCoord = 0;
			zCoord = 0;
			tileEntityInvalid = false;
		}

		@Override
		public void updateEntity() {
			SpawnControllerAdapter.this.runSpawnCycle();
		}
	}

	protected Set<Queue<Spawnable>> spawnQueues;
	protected Map<Class<? extends Entity>, Integer> aliveCount;
	protected Map<Class<? extends Entity>, Integer> spawnMaximum;
	protected Set<Entity> managedEntities;
	protected TileEntity tickerEntity;
	protected IWorldView worldView;
	protected IArea areaInstance;

	public void setAreaInstance(IArea areaInstance) {
		this.areaInstance = areaInstance;
	}

	public SpawnControllerAdapter(IWorldView worldView) {
		Objects.requireNonNull(worldView);
		this.worldView = worldView;
		this.spawnQueues = new HashSet<>();
		this.aliveCount = new HashMap<>();
		this.spawnMaximum = new HashMap<>();
		this.managedEntities = new HashSet<>();
		this.tickerEntity = new TickerEntity();
		worldView.addWorldAccess(new WorldAccessor());
		worldView.addTileEntity(tickerEntity);
	}

	@Override
	public void defaultSpawnsEnabled(boolean defaultSpawnsEnabled) {
		if (defaultSpawnsEnabled)
			enqueDefaultSpawns();
	}

	protected abstract void enqueDefaultSpawns();

	protected abstract SpawnInformation constructDefaultSpawnInformation(Spawnable entity);

	@Override
	public void spawnEntity(String entityID) {
		spawnEntity((world) -> EntityList.createEntityByName(entityID, world));
	}

	@Override
	public void spawnEntity(String entityID, double x, double y, double z) {
		spawnEntity(new SpawnInformation((world) -> EntityList.createEntityByName(entityID, world), x, y, z));
	}

	@Override
	public void spawnEntity(Spawnable entity) {
		spawnEntity(constructDefaultSpawnInformation(entity));
	}

	public void spawnEntity(Spawnable entity, double x, double y, double z) {
		spawnEntity(new SpawnInformation(entity, x, y, z));
	}

	@Override
	public void enqueueSpawns(Queue<Spawnable> qu) {
		spawnQueues.add(qu);
	}

	public void dequeueSpawns(Queue<Spawnable> qu) {
		spawnQueues.remove(qu);
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

	public Set<Entity> getControlledEntities() {
		return Collections.unmodifiableSet(this.managedEntities);
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
		return worldView.isManaged(posX, posY, posZ);
	}

	/**
	 * Directly spawns the given entity at the position. Forces the spawn
	 */
	protected boolean spawnEntity(SpawnInformation information) {
		Entity entity = information.spawnable.apply(worldView.getWorldObject());
		AreaTeleportation.assignAreaForEntity(entity, areaInstance);
		worldView.spawnEntityAt(entity, information.relX, information.relY, information.relZ);
		return controlEntity(entity);
	}

	/**
	 * Directly despawns the entity (if it is managed) and removes it from this manager.
	 * 
	 * @param entity
	 * @return
	 */
	protected boolean despawnEntity(Entity entity) {
		worldView.removeEntity(entity);
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
	 * This method should be called by all methods removing entities from the area. It only handles the internal lists
	 * and maps, not the actual world.
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
		Iterator<Queue<Spawnable>> it = spawnQueues.iterator();
		while (it.hasNext()) {
			Queue<Spawnable> spawnQ = it.next();
			Spawnable firstEnt = spawnQ.peek();
			if (firstEnt == null)
				it.remove();
			if (spawnIfFreeSlots(firstEnt))
				spawnQ.poll();
		}
	}

	private boolean spawnIfFreeSlots(Spawnable firstEnt) {
		Entity entity = firstEnt.apply(worldView.getWorldObject());
		if (aliveCount.getOrDefault(entity.getClass(), 0) >= spawnMaximum.getOrDefault(entity.getClass(), 0))
			return false;
		spawnEntity((world) -> entity);
		return true;
	}

}
