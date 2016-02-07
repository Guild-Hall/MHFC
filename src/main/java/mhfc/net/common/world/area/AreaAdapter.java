package mhfc.net.common.world.area;

import java.util.Objects;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

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

	/**
	 * Constructs but does not initialize the area
	 */
	public AreaAdapter(World world) {
		this.world = Objects.requireNonNull(world);
		this.config = null;
		this.worldView = null;
		this.state = State.RAW;
	}

	/**
	 * Constructs and initializes the area
	 */
	public AreaAdapter(World world, AreaConfiguration config) {
		this.world = Objects.requireNonNull(world);
		this.config = Objects.requireNonNull(config);
		this.worldView = new DisplacedView(config.getPosition(), config, world);
		this.state = State.INITIALIZED;
	}

	protected CornerPosition getChunkPosition() {
		return this.config.getPosition();
	}

	/**
	 * Initializes the area
	 */
	@Override
	public void loadFromConfig(AreaConfiguration config) {
		this.config = Objects.requireNonNull(config);
		this.worldView = new DisplacedView(config.getPosition(), config, world);
		this.state = State.INITIALIZED;
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
			throw new IllegalStateException("Area is dismissable");
		}
		MinecraftForge.EVENT_BUS.unregister(this);
		state = State.INITIALIZED;
	}

	@SubscribeEvent
	public void onPlayerClickBlock(PlayerInteractEvent event) {
		if (event.world != world) {
			return;
		}
		if (event.action != Action.LEFT_CLICK_BLOCK) {
			return;
		}
		CornerPosition chunkPos = getChunkPosition();
		if (event.x / 16 >= chunkPos.posX && event.x / 16 < chunkPos.posX + config.getChunkSizeX()
				&& event.z / 16 >= chunkPos.posY && event.z / 16 < chunkPos.posY + config.getChunkSizeZ()) {
			event.setCanceled(true);
		}
	}

}
