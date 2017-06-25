package mhfc.net.common.quests.world;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IWorldView;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;

public abstract class SpawnControllerAdapter implements IQuestAreaSpawnController {

	protected class WorldAccessor implements IWorldEventListener {

		@Override
		public void notifyLightSet(BlockPos pos) {}

		@Override
		public void notifyBlockUpdate(World world, BlockPos pos, IBlockState old, IBlockState now, int flags) {}

		@Override
		public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {}

		@Override
		public void spawnParticle(
				int p_190570_1_,
				boolean p_190570_2_,
				boolean p_190570_3_,
				double p_190570_4_,
				double p_190570_6_,
				double p_190570_8_,
				double p_190570_10_,
				double p_190570_12_,
				double p_190570_14_,
				int... p_190570_16_) {}

		@Override
		public void broadcastSound(int soundID, BlockPos pos, int data) {}

		@Override
		public void playRecord(SoundEvent soundIn, BlockPos pos) {}

		@Override
		public void playSoundToAllNearExcept(
				EntityPlayer player,
				SoundEvent sound,
				SoundCategory category,
				double x,
				double y,
				double z,
				float volume,
				float pitch) {}

		@Override
		public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) {}

		@Override
		public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {}

		@Override
		public void spawnParticle(
				int particleID,
				boolean ignoreRange,
				double xCoord,
				double yCoord,
				double zCoord,
				double xSpeed,
				double ySpeed,
				double zSpeed,
				int... parameters) {}

		@Override
		public void onEntityAdded(Entity entity) {
			Vec3d pos = entity.getPositionVector();
			pos = SpawnControllerAdapter.this.worldView.convertToLocal(pos);
			if (!inArea(pos.xCoord, pos.yCoord, pos.zCoord)) {
				return;
			}
			SpawnControllerAdapter.this.controlEntity(entity);
		}

		@Override
		public void onEntityRemoved(Entity entity) {
			SpawnControllerAdapter.this.releaseEntity(entity);
		}
	}

	protected class TickerEntity extends TileEntity implements ITickable {
		public TickerEntity() {
			super();
			pos = new BlockPos(0, 0, 0);
			tileEntityInvalid = false;
		}

		@Override
		public void update() {
			SpawnControllerAdapter.this.runSpawnCycle();
		}
	}

	protected Set<Queue<EntityFactory>> spawnQueues;
	protected Multiset<Class<? extends Entity>> aliveCount;
	protected Multiset<Class<? extends Entity>> spawnMaximum;
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
		this.aliveCount = HashMultiset.create();
		this.spawnMaximum = HashMultiset.create();
		this.managedEntities = new HashSet<>();
		this.tickerEntity = new TickerEntity();
		worldView.addWorldAccess(new WorldAccessor());
		worldView.addTileEntity(tickerEntity);
	}

	@Override
	public void setDefaultSpawnsEnabled(boolean defaultSpawnsEnabled) {
		if (defaultSpawnsEnabled) {
			enqueDefaultSpawns();
		}
	}

	protected abstract void enqueDefaultSpawns();

	@Override
	public void spawnEntity(EntityFactory factory) {
		Objects.requireNonNull(factory);
		BlockPos pos = areaInstance.resolvePosition(IQuestArea.MONSTER_SPAWN);
		pos = worldView.getTopSolidOrLiquidBlock(pos).up();
		spawnEntity(factory, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public void spawnEntity(EntityFactory factory, double x, double y, double z) {
		Objects.requireNonNull(factory);
		EntityFactory factoryWithPos = factory.andThenConfigure(e -> {
			if (e == null) {
				return null;
			}
			e.setPosition(x, y, z);
			return e;
		});
		doSpawnEntity(factoryWithPos);
	}

	@Override
	public void enqueueSpawns(Queue<EntityFactory> qu) {
		spawnQueues.add(qu);
	}

	@Override
	public void dequeueSpawns(Queue<EntityFactory> qu) {
		spawnQueues.remove(qu);
	}

	@Override
	public void clearQueues() {
		spawnQueues.clear();
	}

	@Override
	public void setGenerationMaximum(ResourceLocation entityID, int maxAmount) {
		Class<? extends Entity> clazz = EntityList.getClass(entityID);
		if (clazz == null) {
			return;
		}
		setGenerationMaximum(clazz, maxAmount);
	}

	@Override
	public <T extends Entity> void setGenerationMaximum(Class<T> entityclass, int maxAmount) {
		spawnMaximum.setCount(entityclass, maxAmount);
	}

	@Override
	public Set<Entity> getControlledEntities() {
		return Collections.unmodifiableSet(this.managedEntities);
	}

	@Override
	public int clearAreaOf(Predicate<Entity> predicate) {
		List<Entity> allEntities = worldView.getAllMatchingEntities(predicate);

		return (int) allEntities.stream().filter(predicate).filter(EntitySelectors.NOT_SPECTATING::apply)
				.filter(this::despawnEntity).count();
	}

	protected boolean inArea(double posX, double posY, double posZ) {
		return worldView.isManaged(posX, posY, posZ);
	}

	/**
	 * Directly spawns the given entity at the position. Forces the spawn
	 */
	protected boolean doSpawnEntity(EntityFactory spawnable) {
		Entity entity = spawnable.apply(worldView.getWorldObject(), areaInstance);
		if (entity == null) {
			return false;
		}
		AreaTeleportation.assignAreaForEntity(entity, areaInstance);
		worldView.spawnEntityAt(entity, entity.posX, entity.posY, entity.posZ);
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
		if (!managedEntities.add(entity)) {
			return false;
		}

		Class<? extends Entity> clazz = entity.getClass();
		aliveCount.add(clazz);
		return true;
	}

	/**
	 * This method should be called by all methods removing entities from the area. It only handles the internal lists
	 * and maps, not the actual world.
	 */
	protected boolean releaseEntity(Entity entity) {
		if (!managedEntities.remove(entity)) {
			return false;
		}

		Class<? extends Entity> clazz = entity.getClass();
		boolean removed = aliveCount.remove(clazz);
		assert removed;
		return true;
	}

	@Override
	public void runSpawnCycle() {
		Iterator<Queue<EntityFactory>> it = spawnQueues.iterator();
		while (it.hasNext()) {
			Queue<EntityFactory> spawnQ = it.next();
			EntityFactory firstEnt = spawnQ.peek();
			if (firstEnt == null) {
				it.remove();
				continue;
			}
			if (doSpawnEntity(firstEnt.andThenConfigure(this::enforceSpawnLimit))) {
				// consume the element
				EntityFactory polled = spawnQ.poll();
				assert polled == firstEnt;
			}
		}
	}

	private Entity enforceSpawnLimit(Entity entity) {
		if (entity == null) {
			return null;
		}
		Class<? extends Entity> entityClass = entity.getClass();
		if (spawnMaximum.contains(entityClass) && aliveCount.count(entityClass) >= spawnMaximum.count(entityClass)) {
			return null; // Skip spawn
		}
		return entity;
	}

}
