package mhfc.net.common.world.area;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

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

	void markBlockForUpdate(int x, int y, int z);

	/**
	 * Returns true if the block at the specified coordinates is empty
	 */
	boolean isAirBlock(int x, int y, int z);

	/**
	 * Returns whether a block exists at world coordinates x, y, z
	 */
	boolean blockExists(int x, int y, int z);

	/**
	 * Returns a chunk looked up by block coordinates. Args: x, z
	 */
	Chunk getChunkFromBlockCoords(int x, int z);

	Block getTopBlock(int x, int z);

	/**
	 * Finds the highest block on the x, z coordinate that is solid and returns its y coord. Args x, z
	 */
	int getTopSolidOrLiquidBlock(int x, int z);

	Block getBlock(int x, int y, int z);

	/**
	 * Returns the block metadata at coords x,y,z
	 */
	int getBlockMetadata(int x, int y, int z);

	/**
	 * Sets a block to 0 and notifies relevant systems with the block change Args: x, y, z
	 */
	boolean setBlockToAir(int x, int y, int z);

	/**
	 * Sets a block by a coordinate
	 */
	boolean setBlock(int x, int y, int z, Block block);

	/**
	 * Sets the block ID and metadata at a given location. Args: X, Y, Z, new block ID, new metadata, flags. Flag 1 will
	 * cause a block update. Flag 2 will send the change to clients (you almost always want this). Flag 4 prevents the
	 * block from being re-rendered, if this is a client world. Flags can be added together.
	 */
	boolean setBlock(int x, int y, int z, Block block, int metadata, int flags);

	/**
	 * Play a sound effect. Many many parameters for this function. Not sure what they do, but a classic call is :
	 * (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 'random.door_open', 1.0F, world.rand.nextFloat() * 0.1F +
	 * 0.9F with i,j,k position of the block.
	 */
	void playSoundEffect(double x, double y, double z, String soundName, float p_72908_8_, float p_72908_9_);

	/**
	 * par8 is loudness, all pars passed to minecraftInstance.sndManager.playSound
	 */
	void playSound(double x, double y, double z, String soundName, float volume, float p_72980_9_, boolean p_72980_10_);

	/**
	 * Spawns a particle. Args particleName, x, y, z, velX, velY, velZ
	 */
	void spawnParticle(String particleName, double x, double y, double z, double velX, double velY, double velZ);

	void onEntityAdded(Entity entity);

	void onEntityRemoved(Entity entity);

	/**
	 * Schedule the entity for removal during the next tick. Marks the entity dead in anticipation.
	 */
	void removeEntity(Entity entity);

	/**
	 * Adds a IWorldAccess to the list of worldAccesses
	 */
	void addWorldAccess(IWorldAccess worldAccess);

	/**
	 * Removes a worldAccess from the worldAccesses object
	 */
	void removeWorldAccess(IWorldAccess worldAccess);

	TileEntity getTileEntity(int x, int y, int z);

	void setTileEntity(int x, int y, int z, TileEntity tileEntity);

	void removeTileEntity(int x, int y, int z);

	/**
	 * Counts how many entities of an entity class exist in the world. Args: entityClass
	 */
	int countEntities(Class<? extends Entity> entityClass);

	/**
	 * Returns the highest redstone signal strength powering the given block. Args: X, Y, Z.
	 */
	int getBlockPowerInput(int x, int y, int z);

	/**
	 * Called when checking if a certain block can be mined or not. The 'spawn safe zone' check is located here.
	 */
	boolean canMineBlock(EntityPlayer player, int x, int y, int z);

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
	boolean isSideSolid(int x, int y, int z, ForgeDirection side);

	boolean spawnEntityAt(Entity entity, double x, double y, double z);

	void moveEntityTo(Entity entity, double x, double y, double z);

}
