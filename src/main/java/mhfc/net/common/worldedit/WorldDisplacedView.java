package mhfc.net.common.worldedit;

import java.util.ArrayList;
import java.util.List;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.Vector2D;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BaseItemStack;
import com.sk89q.worldedit.entity.BaseEntity;
import com.sk89q.worldedit.entity.Entity;
import com.sk89q.worldedit.forge.ForgeWorld;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionOperationException;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldedit.util.TreeGenerator.TreeType;
import com.sk89q.worldedit.world.AbstractWorld;
import com.sk89q.worldedit.world.biome.BaseBiome;
import com.sk89q.worldedit.world.registry.WorldData;

import mhfc.net.MHFCMain;
import mhfc.net.common.world.area.DisplacedView;

public class WorldDisplacedView extends AbstractWorld {

	protected DisplacedView worldView;
	protected ForgeWorld forgeWorld;
	protected Vector add;
	protected Vector2D chunkAdd;

	public WorldDisplacedView(DisplacedView worldView) {
		this.worldView = worldView;
		this.forgeWorld = MHFCMain.getWorldedit().getWorld(worldView.getWorldObject());
		this.add = new Vector(worldView.getAddX(), 0, worldView.getAddZ());
		this.chunkAdd = new Vector2D(worldView.getChunkDeltaX(), worldView.getChunkDeltaZ());
	}

	@Override
	public String getName() {
		return worldView.getWorldObject().getWorldInfo().getWorldName();
	}

	@Override
	public boolean setBlock(Vector position, BaseBlock block, boolean notifyAndLight) throws WorldEditException {
		return forgeWorld.setBlock(position.add(add), block);
	}

	@Override
	public int getBlockLightLevel(Vector position) {
		return forgeWorld.getBlockLightLevel(position.add(add));
	}

	@Override
	public boolean clearContainerBlockContents(Vector position) {
		return forgeWorld.clearContainerBlockContents(position.add(add));
	}

	@Override
	public void dropItem(Vector position, BaseItemStack item) {
		forgeWorld.dropItem(position.add(add), item);
	}

	@Override
	public boolean regenerate(Region region, EditSession editSession) {
		try {
			region.shift(add);
		} catch (RegionOperationException e) {
			return false;
		}
		return forgeWorld.regenerate(region, editSession);
	}

	@Override
	public boolean generateTree(TreeType type, EditSession editSession, Vector position)
			throws MaxChangedBlocksException {
		return forgeWorld.generateTree(type, editSession, position.add(add));
	}

	@Override
	public WorldData getWorldData() {
		return forgeWorld.getWorldData();
	}

	@Override
	public List<? extends Entity> getEntities(Region region) {
		try {
			region.shift(add);
		} catch (RegionOperationException e) {
			return new ArrayList<>();
		}
		return forgeWorld.getEntities(region);
	}

	@Override
	public List<? extends Entity> getEntities() {
		return forgeWorld.getEntities();
	}

	@Override
	public Entity createEntity(Location location, BaseEntity entity) {
		Vector locationV = new Vector(location.getX(), location.getY(), location.getZ());
		location = new Location(location.getExtent(), locationV.add(add), location.getDirection());
		return forgeWorld.createEntity(location, entity);
	}

	@Override
	public BaseBlock getBlock(Vector position) {
		return forgeWorld.getBlock(position.add(add));
	}

	@Override
	public BaseBlock getLazyBlock(Vector position) {
		return forgeWorld.getLazyBlock(position.add(add));
	}

	@Override
	public BaseBiome getBiome(Vector2D position) {
		return forgeWorld.getBiome(position.add(chunkAdd));
	}

	@Override
	public boolean setBiome(Vector2D position, BaseBiome biome) {
		return forgeWorld.setBiome(position.add(chunkAdd), biome);
	}

}
