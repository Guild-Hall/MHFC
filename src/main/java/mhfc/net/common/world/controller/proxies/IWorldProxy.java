package mhfc.net.common.world.controller.proxies;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.ImmutableSetMultimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.profiler.Profiler;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.common.util.ForgeDirection;

public interface IWorldProxy {

	ArrayList<BlockSnapshot> getCapturedBlockSnapshots();

	EnumDifficulty getDifficultySetting();

	boolean isRemote();

	MapStorage getMapStorage();

	MapStorage getPerWorldStorage();

	List<EntityPlayer> getPlayerEntities();

	WorldProvider getProvider();

	Random getRand();

	Profiler getProfiler();

	VillageCollection getVillageCollection();

	List<Entity> getWeatherEffects();

	BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_);

	BiomeGenBase getBiomeGenForCoordsBody(int p_72807_1_, int p_72807_2_);

	WorldChunkManager getWorldChunkManager();

	void setSpawnLocation();

	Block getTopBlock(int p_147474_1_, int p_147474_2_);

	Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_);

	boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_);

	boolean blockExists(int p_72899_1_, int p_72899_2_, int p_72899_3_);

	boolean doChunksNearChunkExist(int p_72873_1_, int p_72873_2_, int p_72873_3_, int p_72873_4_);

	boolean checkChunksExist(
			int p_72904_1_,
			int p_72904_2_,
			int p_72904_3_,
			int p_72904_4_,
			int p_72904_5_,
			int p_72904_6_);

	Chunk getChunkFromBlockCoords(int p_72938_1_, int p_72938_2_);

	Chunk getChunkFromChunkCoords(int p_72964_1_, int p_72964_2_);

	boolean setBlock(
			int p_147465_1_,
			int p_147465_2_,
			int p_147465_3_,
			Block p_147465_4_,
			int p_147465_5_,
			int p_147465_6_);

	void markAndNotifyBlock(int x, int y, int z, Chunk chunk, Block oldBlock, Block newBlock, int flag);

	int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_);

	boolean setBlockMetadataWithNotify(int p_72921_1_, int p_72921_2_, int p_72921_3_, int p_72921_4_, int p_72921_5_);

	boolean setBlockToAir(int p_147468_1_, int p_147468_2_, int p_147468_3_);

	boolean func_147480_a(int p_147480_1_, int p_147480_2_, int p_147480_3_, boolean p_147480_4_);

	boolean setBlock(int p_147449_1_, int p_147449_2_, int p_147449_3_, Block p_147449_4_);

	void markBlockForUpdate(int p_147471_1_, int p_147471_2_, int p_147471_3_);

	void notifyBlockChange(int p_147444_1_, int p_147444_2_, int p_147444_3_, Block p_147444_4_);

	void markBlocksDirtyVertical(int p_72975_1_, int p_72975_2_, int p_72975_3_, int p_72975_4_);

	void markBlockRangeForRenderUpdate(
			int p_147458_1_,
			int p_147458_2_,
			int p_147458_3_,
			int p_147458_4_,
			int p_147458_5_,
			int p_147458_6_);

	void notifyBlocksOfNeighborChange(int p_147459_1_, int p_147459_2_, int p_147459_3_, Block p_147459_4_);

	void notifyBlocksOfNeighborChange(
			int p_147441_1_,
			int p_147441_2_,
			int p_147441_3_,
			Block p_147441_4_,
			int p_147441_5_);

	void notifyBlockOfNeighborChange(int p_147460_1_, int p_147460_2_, int p_147460_3_, Block p_147460_4_);

	boolean isBlockTickScheduledThisTick(int p_147477_1_, int p_147477_2_, int p_147477_3_, Block p_147477_4_);

	boolean canBlockSeeTheSky(int p_72937_1_, int p_72937_2_, int p_72937_3_);

	int getFullBlockLightValue(int p_72883_1_, int p_72883_2_, int p_72883_3_);

	int getBlockLightValue(int p_72957_1_, int p_72957_2_, int p_72957_3_);

	int getBlockLightValue_do(int p_72849_1_, int p_72849_2_, int p_72849_3_, boolean p_72849_4_);

	int getHeightValue(int p_72976_1_, int p_72976_2_);

	int getChunkHeightMapMinimum(int p_82734_1_, int p_82734_2_);

	int getSkyBlockTypeBrightness(EnumSkyBlock p_72925_1_, int p_72925_2_, int p_72925_3_, int p_72925_4_);

	int getSavedLightValue(EnumSkyBlock p_72972_1_, int p_72972_2_, int p_72972_3_, int p_72972_4_);

	void setLightValue(EnumSkyBlock p_72915_1_, int p_72915_2_, int p_72915_3_, int p_72915_4_, int p_72915_5_);

	void notifyForRenderUpdate(int x, int y, int z);

	int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_);

	float getLightBrightness(int p_72801_1_, int p_72801_2_, int p_72801_3_);

	boolean isDaytime();

	MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_);

	MovingObjectPosition rayTraceBlocks(Vec3 p_72901_1_, Vec3 p_72901_2_, boolean p_72901_3_);

	MovingObjectPosition checkCollisionUnprecise(
			Vec3 startPos,
			Vec3 endPos,
			boolean holdsBoat,
			boolean requireCollisionBox,
			boolean collideWithNonBlocks);

	void playSoundAtEntity(Entity p_72956_1_, String p_72956_2_, float p_72956_3_, float p_72956_4_);

	void playSoundToNearExcept(EntityPlayer p_85173_1_, String p_85173_2_, float p_85173_3_, float p_85173_4_);

	void playSoundEffect(
			double p_72908_1_,
			double p_72908_3_,
			double p_72908_5_,
			String p_72908_7_,
			float p_72908_8_,
			float p_72908_9_);

	void playSound(
			double p_72980_1_,
			double p_72980_3_,
			double p_72980_5_,
			String p_72980_7_,
			float p_72980_8_,
			float p_72980_9_,
			boolean p_72980_10_);

	void playRecord(String p_72934_1_, int p_72934_2_, int p_72934_3_, int p_72934_4_);

	void spawnParticle(
			String p_72869_1_,
			double p_72869_2_,
			double p_72869_4_,
			double p_72869_6_,
			double p_72869_8_,
			double p_72869_10_,
			double p_72869_12_);

	boolean addWeatherEffect(Entity p_72942_1_);

	boolean spawnEntityInWorld(Entity p_72838_1_);

	void onEntityAdded(Entity p_72923_1_);

	void onEntityRemoved(Entity p_72847_1_);

	void removeEntity(Entity p_72900_1_);

	void removePlayerEntityDangerously(Entity p_72973_1_);

	void addWorldAccess(IWorldAccess p_72954_1_);

	List<AxisAlignedBB> getCollidingBoundingBoxes(Entity p_72945_1_, AxisAlignedBB p_72945_2_);

	List<AxisAlignedBB> getCollidingBlockBoundingBoxes(AxisAlignedBB p_147461_1_);

	int calculateSkylightSubtracted(float p_72967_1_);

	float getSunBrightnessFactor(float p_72967_1_);

	void removeWorldAccess(IWorldAccess p_72848_1_);

	float getSunBrightness(float p_72971_1_);

	float getSunBrightnessBody(float p_72971_1_);

	Vec3 getSkyColor(Entity p_72833_1_, float p_72833_2_);

	Vec3 getSkyColorBody(Entity p_72833_1_, float p_72833_2_);

	float getCelestialAngle(float p_72826_1_);

	int getMoonPhase();

	float getCurrentMoonPhaseFactor();

	float getCurrentMoonPhaseFactorBody();

	float getCelestialAngleRadians(float p_72929_1_);

	Vec3 getCloudColour(float p_72824_1_);

	Vec3 getFogColor(float p_72948_1_);

	int getPrecipitationHeight(int p_72874_1_, int p_72874_2_);

	int getTopSolidOrLiquidBlock(int p_72825_1_, int p_72825_2_);

	float getStarBrightness(float p_72880_1_);

	float getStarBrightnessBody(float par1);

	void scheduleBlockUpdate(int p_147464_1_, int p_147464_2_, int p_147464_3_, Block p_147464_4_, int p_147464_5_);

	void scheduleBlockUpdateWithPriority(
			int p_147454_1_,
			int p_147454_2_,
			int p_147454_3_,
			Block p_147454_4_,
			int p_147454_5_,
			int p_147454_6_);

	void func_147446_b(
			int p_147446_1_,
			int p_147446_2_,
			int p_147446_3_,
			Block p_147446_4_,
			int p_147446_5_,
			int p_147446_6_);

	void updateEntities();

	void addTileEntitiesToUpdate(Collection<TileEntity> p_147448_1_);

	void updateEntity(Entity p_72870_1_);

	void updateEntityWithOptionalForce(Entity p_72866_1_, boolean p_72866_2_);

	boolean checkNoEntityCollision(AxisAlignedBB p_72855_1_);

	boolean checkNoEntityCollision(AxisAlignedBB p_72917_1_, Entity p_72917_2_);

	boolean checkBlockCollision(AxisAlignedBB p_72829_1_);

	boolean isAnyLiquid(AxisAlignedBB p_72953_1_);

	boolean isAnyOnFire(AxisAlignedBB p_147470_1_);

	boolean handleMaterialAcceleration(AxisAlignedBB p_72918_1_, Material p_72918_2_, Entity p_72918_3_);

	boolean isMaterialInBB(AxisAlignedBB p_72875_1_, Material p_72875_2_);

	boolean isAABBInMaterial(AxisAlignedBB p_72830_1_, Material p_72830_2_);

	Explosion createExplosion(
			Entity p_72876_1_,
			double p_72876_2_,
			double p_72876_4_,
			double p_72876_6_,
			float p_72876_8_,
			boolean p_72876_9_);

	Explosion newExplosion(
			Entity p_72885_1_,
			double p_72885_2_,
			double p_72885_4_,
			double p_72885_6_,
			float p_72885_8_,
			boolean p_72885_9_,
			boolean p_72885_10_);

	float getBlockDensity(Vec3 p_72842_1_, AxisAlignedBB p_72842_2_);

	boolean extinguishFire(EntityPlayer p_72886_1_, int p_72886_2_, int p_72886_3_, int p_72886_4_, int p_72886_5_);

	String getProviderName();

	TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_);

	void setTileEntity(int p_147455_1_, int p_147455_2_, int p_147455_3_, TileEntity p_147455_4_);

	void removeTileEntity(int p_147475_1_, int p_147475_2_, int p_147475_3_);

	void listenToChunkUnload(TileEntity p_147457_1_);

	boolean isOversizedCube(int x, int y, int z);

	boolean isBlockNormalCubeDefault(int p_147445_1_, int p_147445_2_, int p_147445_3_, boolean p_147445_4_);

	void calculateInitialSkylight();

	void setAllowedSpawnTypes(boolean p_72891_1_, boolean p_72891_2_);

	void calculateInitialWeatherBody();

	void updateWeatherBody();

	boolean isBlockFreezable(int p_72884_1_, int p_72884_2_, int p_72884_3_);

	boolean isBlockFreezableNaturally(int p_72850_1_, int p_72850_2_, int p_72850_3_);

	boolean canBlockFreeze(int p_72834_1_, int p_72834_2_, int p_72834_3_, boolean p_72834_4_);

	boolean canBlockFreezeBody(int p_72834_1_, int p_72834_2_, int p_72834_3_, boolean p_72834_4_);

	boolean canSnowAt(int x, int y, int z, boolean checkLight);

	boolean canSnowAtBody(int p_147478_1_, int p_147478_2_, int p_147478_3_, boolean p_147478_4_);

	boolean updateLight(int x, int y, int z);

	boolean updateLightByType(EnumSkyBlock p_147463_1_, int p_147463_2_, int p_147463_3_, int p_147463_4_);

	List<Entity> getEntitiesWithinAABBExcludingEntity(Entity p_72839_1_, AxisAlignedBB p_72839_2_);

	List<Entity> getEntitiesWithinAABBExcludingEntity(
			Entity p_94576_1_,
			AxisAlignedBB p_94576_2_,
			IEntitySelector p_94576_3_);

	<T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> p_72872_1_, AxisAlignedBB p_72872_2_);

	<T extends Entity> List<T> selectEntitiesWithinAABB(
			Class<? extends T> p_82733_1_,
			AxisAlignedBB p_82733_2_,
			IEntitySelector p_82733_3_);

	Entity findNearestEntityWithinAABB(Class<?> p_72857_1_, AxisAlignedBB p_72857_2_, Entity p_72857_3_);

	Entity getEntityByID(int p_73045_1_);

	List<Entity> getLoadedEntityList();

	void markTileEntityChunkModified(int p_147476_1_, int p_147476_2_, int p_147476_3_, TileEntity p_147476_4_);

	int countEntities(Class<?> p_72907_1_);

	void addLoadedEntities(List<Entity> p_72868_1_);

	void unloadEntities(List<Entity> p_72828_1_);

	boolean canPlaceEntityOnSide(
			Block p_147472_1_,
			int p_147472_2_,
			int p_147472_3_,
			int p_147472_4_,
			boolean p_147472_5_,
			int p_147472_6_,
			Entity p_147472_7_,
			ItemStack p_147472_8_);

	PathEntity getPathEntityToEntity(
			Entity p_72865_1_,
			Entity p_72865_2_,
			float p_72865_3_,
			boolean p_72865_4_,
			boolean p_72865_5_,
			boolean p_72865_6_,
			boolean p_72865_7_);

	PathEntity getEntityPathToXYZ(
			Entity p_72844_1_,
			int p_72844_2_,
			int p_72844_3_,
			int p_72844_4_,
			float p_72844_5_,
			boolean p_72844_6_,
			boolean p_72844_7_,
			boolean p_72844_8_,
			boolean p_72844_9_);

	int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_);

	int getBlockPowerInput(int p_94577_1_, int p_94577_2_, int p_94577_3_);

	boolean getIndirectPowerOutput(int p_94574_1_, int p_94574_2_, int p_94574_3_, int p_94574_4_);

	int getIndirectPowerLevelTo(int p_72878_1_, int p_72878_2_, int p_72878_3_, int p_72878_4_);

	boolean isBlockIndirectlyGettingPowered(int p_72864_1_, int p_72864_2_, int p_72864_3_);

	int getStrongestIndirectPower(int p_94572_1_, int p_94572_2_, int p_94572_3_);

	EntityPlayer getClosestPlayerToEntity(Entity p_72890_1_, double p_72890_2_);

	EntityPlayer getClosestPlayer(double p_72977_1_, double p_72977_3_, double p_72977_5_, double p_72977_7_);

	EntityPlayer getClosestVulnerablePlayerToEntity(Entity p_72856_1_, double p_72856_2_);

	EntityPlayer getClosestVulnerablePlayer(double p_72846_1_, double p_72846_3_, double p_72846_5_, double p_72846_7_);

	EntityPlayer getPlayerEntityByName(String p_72924_1_);

	EntityPlayer getPlayerByUUID(UUID uuid);

	void checkSessionLock() throws MinecraftException;

	long getSeed();

	long getTotalWorldTime();

	long getWorldTime();

	ChunkCoordinates getSpawnPoint();

	void setSpawnLocation(int p_72950_1_, int p_72950_2_, int p_72950_3_);

	void joinEntityInSurroundings(Entity p_72897_1_);

	boolean canMineBlock(EntityPlayer p_72962_1_, int p_72962_2_, int p_72962_3_, int p_72962_4_);

	boolean canMineBlockBody(EntityPlayer par1EntityPlayer, int par2, int par3, int par4);

	void setEntityState(Entity p_72960_1_, byte p_72960_2_);

	IChunkProvider getChunkProvider();

	void addBlockEvent(
			int p_147452_1_,
			int p_147452_2_,
			int p_147452_3_,
			Block p_147452_4_,
			int p_147452_5_,
			int p_147452_6_);

	ISaveHandler getSaveHandler();

	WorldInfo getWorldInfo();

	GameRules getGameRules();

	void updateAllPlayersSleepingFlag();

	float getWeightedThunderStrength(float p_72819_1_);

	float getRainStrength(float p_72867_1_);

	boolean isThundering();

	boolean isRaining();

	boolean canLightningStrikeAt(int p_72951_1_, int p_72951_2_, int p_72951_3_);

	boolean isBlockHighHumidity(int p_72958_1_, int p_72958_2_, int p_72958_3_);

	void setItemData(String p_72823_1_, WorldSavedData p_72823_2_);

	WorldSavedData loadItemData(Class<?> p_72943_1_, String p_72943_2_);

	int getUniqueDataId(String p_72841_1_);

	void playBroadcastSound(int p_82739_1_, int p_82739_2_, int p_82739_3_, int p_82739_4_, int p_82739_5_);

	void playAuxSFX(int p_72926_1_, int p_72926_2_, int p_72926_3_, int p_72926_4_, int p_72926_5_);

	void playAuxSFXAtEntity(
			EntityPlayer p_72889_1_,
			int p_72889_2_,
			int p_72889_3_,
			int p_72889_4_,
			int p_72889_5_,
			int p_72889_6_);

	int getHeight();

	int getActualHeight();

	Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_);

	ChunkPosition findClosestStructure(String p_147440_1_, int p_147440_2_, int p_147440_3_, int p_147440_4_);

	boolean extendedLevelsInChunkCache();

	double getHorizon();

	void destroyBlockInWorldPartially(
			int p_147443_1_,
			int p_147443_2_,
			int p_147443_3_,
			int p_147443_4_,
			int p_147443_5_);

	Calendar getCurrentDate();

	void makeFireworks(
			double p_92088_1_,
			double p_92088_3_,
			double p_92088_5_,
			double p_92088_7_,
			double p_92088_9_,
			double p_92088_11_,
			NBTTagCompound p_92088_13_);

	Scoreboard getScoreboard();

	void firePropagatingBlockUpdate(int x, int y, int z, Block unused);

	float difficultyEnhancedRandom(double locX, double locY, double locZ);

	float difficultyEnhancedRandom(int x, int y, int z);

	void fireStaticEntityChanged();

	void addTileEntity(TileEntity entity);

	boolean isSideSolid(int x, int y, int z, ForgeDirection side);

	boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default);

	ImmutableSetMultimap<ChunkCoordIntPair, Ticket> getPersistentChunks();

	int getBlockLightOpacity(int x, int y, int z);

	int countEntities(EnumCreatureType type, boolean forSpawnCount);

}
