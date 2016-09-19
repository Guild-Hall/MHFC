package mhfc.net.common.world.area;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.chunk.Chunk;

/**
 * A custom interface for various methods of a world which do not affect global state or could also be viewed with a
 * different center
 */
public interface IWorld {

	/**
	 * Checks whether its daytime by seeing if the light subtracted from the skylight is less than 4
	 */
	boolean isDaytime();

	/**
	 * Returns true if the current thunder strength (weighted with the rain strength) is greater than 0.9
	 */
	boolean isThundering();

	/**
	 * Returns true if the current rain strength is greater than 0.2
	 */
	boolean isRaining();

	/**
	 * Returns true if the block at the specified coordinates is empty
	 */
	boolean isAirBlock(BlockPos pos);

	/**
	 * Returns whether a block exists at world coordinates x, y, z
	 */
	boolean blockExists(BlockPos pos);

	/**
	 * Returns a chunk looked up by block coordinates. Args: x, z
	 */
	Chunk getChunkFromBlockCoords(BlockPos pos);

	IBlockState getTopBlock(BlockPos pos);

	/**
	 * Finds the highest block on the x, z coordinate that is solid and returns its y coord. Args x, z
	 */
	BlockPos getTopSolidOrLiquidBlock(BlockPos pos);

	IBlockState getBlockState(BlockPos pos);

	/**
	 * Sets a block to 0 and notifies relevant systems with the block change Args: x, y, z
	 */
	boolean setBlockToAir(BlockPos pos);

	/**
	 * Sets a block by a coordinate
	 */
	boolean setBlockState(BlockPos pos, IBlockState block);

	/**
	 * Sets the block ID and metadata at a given location. Args: X, Y, Z, new block ID, new metadata, flags. Flag 1 will
	 * cause a block update. Flag 2 will send the change to clients (you almost always want this). Flag 4 prevents the
	 * block from being re-rendered, if this is a client world. Flags can be added together.
	 */
	boolean setBlockState(BlockPos pos, IBlockState block, int flags);

	/**
	 *
	 */
	void playSound(
			double x,
			double y,
			double z,
			SoundEvent sound,
			SoundCategory category,
			float volume,
			float pitch,
			boolean honorDistance);

	/**
	 * Spawns a particle. Args particleName, x, y, z, velX, velY, velZ
	 */
	void spawnParticle(
			EnumParticleTypes particle,
			double x,
			double y,
			double z,
			double velX,
			double velY,
			double velZ,
			int... params);

	void onEntityAdded(Entity entity);

	void onEntityRemoved(Entity entity);

	/**
	 * Schedule the entity for removal during the next tick. Marks the entity dead in anticipation.
	 */
	void removeEntity(Entity entity);

	/**
	 * Adds a IWorldAccess to the list of worldAccesses
	 */
	void addWorldAccess(IWorldEventListener worldAccess);

	/**
	 * Removes a worldAccess from the worldAccesses object
	 */
	void removeWorldAccess(IWorldEventListener worldAccess);

	TileEntity getTileEntity(BlockPos pos);

	void setTileEntity(BlockPos pos, TileEntity tileEntity);

	void removeTileEntity(BlockPos pos);

	/**
	 * Counts how many entities of an entity class exist in the world. Args: entityClass
	 */
	int countEntities(Class<? extends Entity> entityClass);

	/**
	 * Returns the highest redstone signal strength powering the given block. Args: X, Y, Z.
	 */
	int getBlockPowerInput(BlockPos position, EnumFacing face);

	/**
	 * Called when checking if a certain block can be mined or not. The 'spawn safe zone' check is located here.
	 */
	boolean canMineBlock(EntityPlayer player, BlockPos pos);

	/**
	 * Adds a single TileEntity to the world.
	 *
	 * @param entity
	 *            The TileEntity to be added.
	 */
	void addTileEntity(TileEntity entity);

	/**
	 * Determine if the given block is considered solid on the specified side. Used by placement logic.
	 *
	 * @param x
	 *            Block X Position
	 * @param y
	 *            Block Y Position
	 * @param z
	 *            Block Z Position
	 * @param side
	 *            The Side in question
	 * @return True if the side is solid
	 */
	boolean isSideSolid(BlockPos pos, EnumFacing side);

	boolean spawnEntityAt(Entity entity, double x, double y, double z);

	default void spawnEntityAt(Entity entity, BlockPos position) {
		spawnEntityAt(entity, position.getX(), position.getY(), position.getZ());
	}

	void moveEntityTo(Entity entity, double x, double y, double z);

	default void moveEntityTo(Entity entity, BlockPos position) {
		moveEntityTo(entity, position.getX(), position.getY(), position.getZ());
	}
}
