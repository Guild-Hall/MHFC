package mhfc.net.common.world.area;

import java.util.Objects;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.common.quests.world.IQuestAreaSpawnController;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;

public abstract class AreaAdapter implements IArea {

	protected static enum State {
		RAW,
		INITIALIZED,
		ACQUIRED;
	}

	protected final World world;
	protected AreaConfiguration config;
	protected IWorldView worldView;
	protected State state;
	protected IQuestAreaSpawnController spawnController;

	/**
	 * Constructs and initializes the area
	 */
	public AreaAdapter(World world, AreaConfiguration config) {
		this.world = Objects.requireNonNull(world);
		this.config = Objects.requireNonNull(config);
		this.worldView = new DisplacedView(config.getPosition(), config, world);
		this.spawnController = initializeSpawnController();
		this.state = State.INITIALIZED;
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
		if (event.world != world) {
			return false;
		}
		return isInArea(event.x, event.z);
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

}
