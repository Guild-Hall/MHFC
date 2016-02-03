package mhfc.net.common.world.area;

import java.util.Objects;

import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.world.World;
public abstract class AreaAdapter implements IArea{
	
	protected final World world;
	protected CornerPosition chunkPos;
	protected AreaConfiguration config;
	protected IWorldView worldView;
	protected boolean initialized = false;
	
	/**
	 * Constructs but does not initialize the area
	 */
	public AreaAdapter(World world) {
		this.world = Objects.requireNonNull(world);
		this.chunkPos = null;
		this.config = null;
		this.worldView = null;
	}
	
	/**
	 * Constructs and initializes the area
	 */
	public AreaAdapter(World world, CornerPosition pos, AreaConfiguration config) {
		this.world = Objects.requireNonNull(world);
		this.chunkPos = Objects.requireNonNull(pos);
		this.config = Objects.requireNonNull(config);
		this.worldView = new DisplacedView(chunkPos, config, world);
		this.initialized = true;
	}
	
	/**
	 * Initializes the area
	 */
	@Override
	public void loadFromConfig(CornerPosition pos, AreaConfiguration config) {
		this.chunkPos = Objects.requireNonNull(pos);
		this.config = Objects.requireNonNull(config);
		this.worldView = new DisplacedView(chunkPos, config, world);
		this.initialized = true;
	}

	@Override
	public IWorldView getWorldView() {
		if(!initialized) throw new IllegalStateException("Area is not yet initialized");
		return worldView;
	}

}
