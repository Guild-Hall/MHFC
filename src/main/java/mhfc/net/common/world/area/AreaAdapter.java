package mhfc.net.common.world.area;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.world.IQuestArea;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public abstract class AreaAdapter implements IArea {

	protected static enum State {
		RAW,
		INITIALIZED,
		ACQUIRED;
	}

	private final WeakReference<World> worldRef;
	protected AreaConfiguration config;
	protected IWorldView worldView;
	protected State state;
	protected IQuestAreaSpawnController spawnController;
	protected Map<ResourceLocation, BlockPos> namedPositions;

	/**
	 * Constructs and initializes the area
	 */
	public AreaAdapter(World world, AreaConfiguration config) {
		this.worldRef = new WeakReference<>(Objects.requireNonNull(world));
		this.config = Objects.requireNonNull(config);
		this.worldView = new DisplacedView(config.getPosition(), config, world);
		this.spawnController = initializeSpawnController();
		this.state = State.INITIALIZED;
		this.namedPositions = new HashMap<>();
	}

	protected World getWorld() {
		return worldRef.get();
	}

	protected abstract IQuestAreaSpawnController initializeSpawnController();

	protected CornerPosition getChunkPosition() {
		return this.config.getPosition();
	}

	protected boolean isAccessLegal() {
		return state != State.RAW;
	}

	@Override
	public IWorldView getWorldView() {
		if (!isAccessLegal()) {
			throw new IllegalStateException("Area is not yet initialized");
		}
		return worldView;
	}

	@Override
	public void onAcquire() {
		if (state != State.INITIALIZED) {
			throw new IllegalStateException("Area is not acquireable");
		}
		MinecraftForge.EVENT_BUS.register(this);
		state = State.ACQUIRED;
	}

	@Override
	public void onDismiss() {
		if (state != State.ACQUIRED) {
			throw new IllegalStateException("Area is not dismissable");
		}
		MinecraftForge.EVENT_BUS.unregister(this);
		state = State.INITIALIZED;
	}

	@Override
	public IQuestAreaSpawnController getSpawnController() {
		return spawnController;
	}

	private boolean isInArea(double xCoord, double zCoord) {
		CornerPosition chunkPos = getChunkPosition();
		return xCoord / 16 >= chunkPos.posX && xCoord / 16 < chunkPos.posX + config.getChunkSizeX()
		&& zCoord / 16 >= chunkPos.posY && zCoord / 16 < chunkPos.posY + config.getChunkSizeZ();
	}

	private boolean isInArea(BlockEvent event) {
		if (event.getWorld() != getWorld()) {
			return false;
		}
		return isInArea(event.getPos().getX(), event.getPos().getZ());
	}

	@SubscribeEvent
	public void onBlockBreakEvent(BreakEvent event) {
		if (!isInArea(event)) {
			return;
		}
		event.setCanceled(true);
	}

	@SubscribeEvent
	public void onBlockPlaceEvent(PlaceEvent event) {
		// FIXME allow the placement of the bbq item or stasis traps for example but revert them on leaving the area
		if (!isInArea(event)) {
			return;
		}
		event.setCanceled(true);
	}

	protected abstract BlockPos getPlayerSpawnPosition();

	protected abstract BlockPos getMonsterSpawnPosition();

	@Override
	public Optional<BlockPos> resolveLocation(ResourceLocation location) {
		return getLocationXY(location).map(pos -> {
			if(pos.getY() < 0) {
				BlockPos polledXZ = new BlockPos(pos.getX(), 0, pos.getZ());
				return worldView.getTopSolidOrLiquidBlock(polledXZ).up();
			}
			return pos;
		});
	}

	private Optional<BlockPos> getLocationXY(ResourceLocation location) {
		if (location == null) {
			return Optional.empty();
		}
		if (IQuestArea.PLAYER_SPAWN.equals(location)) {
			return Optional.of(getPlayerSpawnPosition());
		}
		if (IQuestArea.MONSTER_SPAWN.equals(location)) {
			return Optional.of(getMonsterSpawnPosition());
		}
		return Optional.ofNullable(namedPositions.get(location));
	}

}
