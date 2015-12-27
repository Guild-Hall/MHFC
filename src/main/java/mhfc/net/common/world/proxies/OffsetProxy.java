package mhfc.net.common.world.proxies;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import com.google.common.collect.ImmutableSetMultimap;

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

public class OffsetProxy implements IWorldProxy {
	private final IWorldProxy orginal;
	private int chunksOffsetX;
	private int chunksOffsetZ;

	public OffsetProxy(IWorldProxy proxy, int chunksOffsetX, int chunksOffsetZ) {
		this.orginal = Objects.requireNonNull(proxy);
		this.chunksOffsetX = chunksOffsetX;
		this.chunksOffsetZ = chunksOffsetZ;
	}

	private int offsetX(int originalX) {
		return originalX + chunksOffsetX << 4;
	}

	private int offsetZ(int originalZ) {
		return originalZ + chunksOffsetZ << 4;
	}

	private int offsetChunkX(int originalX) {
		return originalX + chunksOffsetX;
	}

	private int offsetChunkZ(int originalZ) {
		return originalZ + chunksOffsetZ;
	}

	public ArrayList<BlockSnapshot> getCapturedBlockSnapshots() {
		return orginal.getCapturedBlockSnapshots();
	}

	public EnumDifficulty getDifficultySetting() {
		return orginal.getDifficultySetting();
	}

	public boolean isRemote() {
		return orginal.isRemote();
	}

	public MapStorage getMapStorage() {
		return orginal.getMapStorage();
	}

	public MapStorage getPerWorldStorage() {
		return orginal.getPerWorldStorage();
	}

	public List<EntityPlayer> getPlayerEntities() {
		return orginal.getPlayerEntities();
	}

	public WorldProvider getProvider() {
		return orginal.getProvider();
	}

	public Random getRand() {
		return orginal.getRand();
	}

	public Profiler getProfiler() {
		return orginal.getProfiler();
	}

	public VillageCollection getVillageCollection() {
		return orginal.getVillageCollection();
	}

	public List<Entity> getWeatherEffects() {
		return orginal.getWeatherEffects();
	}

	public BiomeGenBase getBiomeGenForCoords(int x, int z) {
		return orginal.getBiomeGenForCoords(offsetX(x), offsetZ(z));
	}

	public BiomeGenBase getBiomeGenForCoordsBody(int x, int z) {
		return orginal.getBiomeGenForCoordsBody(offsetX(x), offsetZ(z));
	}

	public WorldChunkManager getWorldChunkManager() {
		return orginal.getWorldChunkManager();
	}

	public void setSpawnLocation() {
		orginal.setSpawnLocation();
	}

	public Block getTopBlock(int x, int z) {
		return orginal.getTopBlock(offsetX(x), offsetZ(z));
	}

	public Block getBlock(int x, int y, int z) {
		return orginal.getBlock(offsetX(x), y, offsetZ(z));
	}

	public boolean isAirBlock(int x, int y, int z) {
		return orginal.isAirBlock(offsetX(x), y, offsetZ(z));
	}

	public boolean blockExists(int x, int y, int z) {
		return orginal.blockExists(offsetX(x), y, offsetZ(z));
	}

	public boolean doChunksNearChunkExist(int x, int y, int z, int range) {
		return orginal.doChunksNearChunkExist(offsetX(x), y, offsetZ(z), range);
	}

	public boolean checkChunksExist(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		return orginal.checkChunksExist(offsetX(minX), minY, offsetZ(minZ), offsetX(maxX), maxY, offsetZ(maxZ));
	}

	public Chunk getChunkFromBlockCoords(int x, int z) {
		return orginal.getChunkFromBlockCoords(offsetX(x), offsetZ(z));
	}

	public Chunk getChunkFromChunkCoords(int x, int z) {
		return orginal.getChunkFromChunkCoords(offsetChunkX(x), offsetChunkZ(z));
	}

	public boolean setBlock(int x, int y, int z, Block p_147465_4_, int meta, int flags) {
		return orginal.setBlock(offsetX(x), y, offsetZ(z), p_147465_4_, meta, flags);
	}

	public void markAndNotifyBlock(int x, int y, int z, Chunk chunk, Block oldBlock, Block newBlock, int flag) {
		orginal.markAndNotifyBlock(offsetX(x), y, offsetZ(z), chunk, oldBlock, newBlock, flag);
	}

	public int getBlockMetadata(int x, int y, int z) {
		return orginal.getBlockMetadata(offsetX(x), y, offsetZ(z));
	}

	public boolean setBlockMetadataWithNotify(int x, int y, int z, int meta, int flags) {
		return orginal.setBlockMetadataWithNotify(offsetX(x), y, offsetZ(z), meta, flags);
	}

	public boolean setBlockToAir(int x, int y, int z) {
		return orginal.setBlockToAir(offsetX(x), y, offsetZ(z));
	}

	public boolean func_147480_a(int x, int y, int z, boolean p_147480_4_) {
		return orginal.func_147480_a(offsetX(x), y, offsetZ(z), p_147480_4_);
	}

	public boolean setBlock(int x, int y, int z, Block p_147449_4_) {
		return orginal.setBlock(offsetX(x), y, offsetZ(z), p_147449_4_);
	}

	public void markBlockForUpdate(int x, int z, int y) {
		orginal.markBlockForUpdate(offsetX(x), z, offsetZ(z));
	}

	public void notifyBlockChange(int x, int y, int z, Block p_147444_4_) {
		orginal.notifyBlockChange(offsetX(x), y, offsetZ(z), p_147444_4_);
	}

	public void markBlocksDirtyVertical(int x, int z, int minY, int minZ) {
		orginal.markBlocksDirtyVertical(offsetX(x), offsetZ(z), minY, minZ);
	}

	public void markBlockRangeForRenderUpdate(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
		orginal.markBlockRangeForRenderUpdate(offsetX(minX), minY, offsetZ(minZ), offsetX(maxX), maxY, offsetZ(maxZ));
	}

	public void notifyBlocksOfNeighborChange(int x, int y, int z, Block p_147459_4_) {
		orginal.notifyBlocksOfNeighborChange(offsetX(x), y, offsetZ(z), p_147459_4_);
	}

	public void notifyBlocksOfNeighborChange(int x, int y, int z, Block block, int side) {
		orginal.notifyBlocksOfNeighborChange(offsetX(x), y, offsetZ(z), block, side);
	}

	public void notifyBlockOfNeighborChange(int x, int y, int z, Block p_147460_4_) {
		orginal.notifyBlockOfNeighborChange(offsetX(x), y, offsetZ(z), p_147460_4_);
	}

	public boolean isBlockTickScheduledThisTick(int x, int y, int z, Block p_147477_4_) {
		return orginal.isBlockTickScheduledThisTick(offsetX(x), y, offsetZ(z), p_147477_4_);
	}

	public boolean canBlockSeeTheSky(int x, int y, int z) {
		return orginal.canBlockSeeTheSky(offsetX(x), y, offsetZ(z));
	}

	public int getFullBlockLightValue(int p_72883_1_, int p_72883_2_, int p_72883_3_) {
		return orginal.getFullBlockLightValue(p_72883_1_, p_72883_2_, p_72883_3_);
	}

	public int getBlockLightValue(int p_72957_1_, int p_72957_2_, int p_72957_3_) {
		return orginal.getBlockLightValue(p_72957_1_, p_72957_2_, p_72957_3_);
	}

	public int getBlockLightValue_do(int p_72849_1_, int p_72849_2_, int p_72849_3_, boolean p_72849_4_) {
		return orginal.getBlockLightValue_do(p_72849_1_, p_72849_2_, p_72849_3_, p_72849_4_);
	}

	public int getHeightValue(int p_72976_1_, int p_72976_2_) {
		return orginal.getHeightValue(p_72976_1_, p_72976_2_);
	}

	public int getChunkHeightMapMinimum(int p_82734_1_, int p_82734_2_) {
		return orginal.getChunkHeightMapMinimum(p_82734_1_, p_82734_2_);
	}

	public int getSkyBlockTypeBrightness(EnumSkyBlock p_72925_1_, int p_72925_2_, int p_72925_3_, int p_72925_4_) {
		return orginal.getSkyBlockTypeBrightness(p_72925_1_, p_72925_2_, p_72925_3_, p_72925_4_);
	}

	public int getSavedLightValue(EnumSkyBlock p_72972_1_, int p_72972_2_, int p_72972_3_, int p_72972_4_) {
		return orginal.getSavedLightValue(p_72972_1_, p_72972_2_, p_72972_3_, p_72972_4_);
	}

	public void setLightValue(EnumSkyBlock p_72915_1_, int p_72915_2_, int p_72915_3_, int p_72915_4_, int p_72915_5_) {
		orginal.setLightValue(p_72915_1_, p_72915_2_, p_72915_3_, p_72915_4_, p_72915_5_);
	}

	public void notifyForRenderUpdate(int x, int y, int z) {
		orginal.notifyForRenderUpdate(x, y, z);
	}

	public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_) {
		return orginal.getLightBrightnessForSkyBlocks(p_72802_1_, p_72802_2_, p_72802_3_, p_72802_4_);
	}

	public float getLightBrightness(int p_72801_1_, int p_72801_2_, int p_72801_3_) {
		return orginal.getLightBrightness(p_72801_1_, p_72801_2_, p_72801_3_);
	}

	public boolean isDaytime() {
		return orginal.isDaytime();
	}

	public MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_) {
		return orginal.rayTraceBlocks(p_72933_1_, p_72933_2_);
	}

	public MovingObjectPosition rayTraceBlocks(Vec3 p_72901_1_, Vec3 p_72901_2_, boolean p_72901_3_) {
		return orginal.rayTraceBlocks(p_72901_1_, p_72901_2_, p_72901_3_);
	}

	public MovingObjectPosition checkCollisionUnprecise(
			Vec3 startPos,
			Vec3 endPos,
			boolean holdsBoat,
			boolean requireCollisionBox,
			boolean collideWithNonBlocks) {
		return orginal.checkCollisionUnprecise(startPos, endPos, holdsBoat, requireCollisionBox, collideWithNonBlocks);
	}

	public void playSoundAtEntity(Entity p_72956_1_, String p_72956_2_, float p_72956_3_, float p_72956_4_) {
		orginal.playSoundAtEntity(p_72956_1_, p_72956_2_, p_72956_3_, p_72956_4_);
	}

	public void playSoundToNearExcept(EntityPlayer p_85173_1_, String p_85173_2_, float p_85173_3_, float p_85173_4_) {
		orginal.playSoundToNearExcept(p_85173_1_, p_85173_2_, p_85173_3_, p_85173_4_);
	}

	public void playSoundEffect(
			double p_72908_1_,
			double p_72908_3_,
			double p_72908_5_,
			String p_72908_7_,
			float p_72908_8_,
			float p_72908_9_) {
		orginal.playSoundEffect(p_72908_1_, p_72908_3_, p_72908_5_, p_72908_7_, p_72908_8_, p_72908_9_);
	}

	public void playSound(
			double p_72980_1_,
			double p_72980_3_,
			double p_72980_5_,
			String p_72980_7_,
			float p_72980_8_,
			float p_72980_9_,
			boolean p_72980_10_) {
		orginal.playSound(p_72980_1_, p_72980_3_, p_72980_5_, p_72980_7_, p_72980_8_, p_72980_9_, p_72980_10_);
	}

	public void playRecord(String p_72934_1_, int p_72934_2_, int p_72934_3_, int p_72934_4_) {
		orginal.playRecord(p_72934_1_, p_72934_2_, p_72934_3_, p_72934_4_);
	}

	public void spawnParticle(
			String p_72869_1_,
			double p_72869_2_,
			double p_72869_4_,
			double p_72869_6_,
			double p_72869_8_,
			double p_72869_10_,
			double p_72869_12_) {
		orginal.spawnParticle(p_72869_1_, p_72869_2_, p_72869_4_, p_72869_6_, p_72869_8_, p_72869_10_, p_72869_12_);
	}

	public boolean addWeatherEffect(Entity p_72942_1_) {
		return orginal.addWeatherEffect(p_72942_1_);
	}

	public boolean spawnEntityInWorld(Entity p_72838_1_) {
		return orginal.spawnEntityInWorld(p_72838_1_);
	}

	public void onEntityAdded(Entity p_72923_1_) {
		orginal.onEntityAdded(p_72923_1_);
	}

	public void onEntityRemoved(Entity p_72847_1_) {
		orginal.onEntityRemoved(p_72847_1_);
	}

	public void removeEntity(Entity p_72900_1_) {
		orginal.removeEntity(p_72900_1_);
	}

	public void removePlayerEntityDangerously(Entity p_72973_1_) {
		orginal.removePlayerEntityDangerously(p_72973_1_);
	}

	public void addWorldAccess(IWorldAccess p_72954_1_) {
		orginal.addWorldAccess(p_72954_1_);
	}

	public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity p_72945_1_, AxisAlignedBB p_72945_2_) {
		return orginal.getCollidingBoundingBoxes(p_72945_1_, p_72945_2_);
	}

	public List<AxisAlignedBB> getCollidingBlockBoundingBoxes(AxisAlignedBB p_147461_1_) {
		return orginal.getCollidingBlockBoundingBoxes(p_147461_1_);
	}

	public int calculateSkylightSubtracted(float p_72967_1_) {
		return orginal.calculateSkylightSubtracted(p_72967_1_);
	}

	public float getSunBrightnessFactor(float p_72967_1_) {
		return orginal.getSunBrightnessFactor(p_72967_1_);
	}

	public void removeWorldAccess(IWorldAccess p_72848_1_) {
		orginal.removeWorldAccess(p_72848_1_);
	}

	public float getSunBrightness(float p_72971_1_) {
		return orginal.getSunBrightness(p_72971_1_);
	}

	public float getSunBrightnessBody(float p_72971_1_) {
		return orginal.getSunBrightnessBody(p_72971_1_);
	}

	public Vec3 getSkyColor(Entity p_72833_1_, float p_72833_2_) {
		return orginal.getSkyColor(p_72833_1_, p_72833_2_);
	}

	public Vec3 getSkyColorBody(Entity p_72833_1_, float p_72833_2_) {
		return orginal.getSkyColorBody(p_72833_1_, p_72833_2_);
	}

	public float getCelestialAngle(float p_72826_1_) {
		return orginal.getCelestialAngle(p_72826_1_);
	}

	public int getMoonPhase() {
		return orginal.getMoonPhase();
	}

	public float getCurrentMoonPhaseFactor() {
		return orginal.getCurrentMoonPhaseFactor();
	}

	public float getCurrentMoonPhaseFactorBody() {
		return orginal.getCurrentMoonPhaseFactorBody();
	}

	public float getCelestialAngleRadians(float p_72929_1_) {
		return orginal.getCelestialAngleRadians(p_72929_1_);
	}

	public Vec3 getCloudColour(float p_72824_1_) {
		return orginal.getCloudColour(p_72824_1_);
	}

	public Vec3 getFogColor(float p_72948_1_) {
		return orginal.getFogColor(p_72948_1_);
	}

	public int getPrecipitationHeight(int p_72874_1_, int p_72874_2_) {
		return orginal.getPrecipitationHeight(p_72874_1_, p_72874_2_);
	}

	public int getTopSolidOrLiquidBlock(int p_72825_1_, int p_72825_2_) {
		return orginal.getTopSolidOrLiquidBlock(p_72825_1_, p_72825_2_);
	}

	public float getStarBrightness(float p_72880_1_) {
		return orginal.getStarBrightness(p_72880_1_);
	}

	public float getStarBrightnessBody(float par1) {
		return orginal.getStarBrightnessBody(par1);
	}

	public void scheduleBlockUpdate(
			int p_147464_1_,
			int p_147464_2_,
			int p_147464_3_,
			Block p_147464_4_,
			int p_147464_5_) {
		orginal.scheduleBlockUpdate(p_147464_1_, p_147464_2_, p_147464_3_, p_147464_4_, p_147464_5_);
	}

	public void scheduleBlockUpdateWithPriority(
			int p_147454_1_,
			int p_147454_2_,
			int p_147454_3_,
			Block p_147454_4_,
			int p_147454_5_,
			int p_147454_6_) {
		orginal.scheduleBlockUpdateWithPriority(
				p_147454_1_,
				p_147454_2_,
				p_147454_3_,
				p_147454_4_,
				p_147454_5_,
				p_147454_6_);
	}

	public void func_147446_b(
			int p_147446_1_,
			int p_147446_2_,
			int p_147446_3_,
			Block p_147446_4_,
			int p_147446_5_,
			int p_147446_6_) {
		orginal.func_147446_b(p_147446_1_, p_147446_2_, p_147446_3_, p_147446_4_, p_147446_5_, p_147446_6_);
	}

	public void updateEntities() {
		orginal.updateEntities();
	}

	public void addTileEntitiesToUpdate(Collection<TileEntity> p_147448_1_) {
		orginal.addTileEntitiesToUpdate(p_147448_1_);
	}

	public void updateEntity(Entity p_72870_1_) {
		orginal.updateEntity(p_72870_1_);
	}

	public void updateEntityWithOptionalForce(Entity p_72866_1_, boolean p_72866_2_) {
		orginal.updateEntityWithOptionalForce(p_72866_1_, p_72866_2_);
	}

	public boolean checkNoEntityCollision(AxisAlignedBB p_72855_1_) {
		return orginal.checkNoEntityCollision(p_72855_1_);
	}

	public boolean checkNoEntityCollision(AxisAlignedBB p_72917_1_, Entity p_72917_2_) {
		return orginal.checkNoEntityCollision(p_72917_1_, p_72917_2_);
	}

	public boolean checkBlockCollision(AxisAlignedBB p_72829_1_) {
		return orginal.checkBlockCollision(p_72829_1_);
	}

	public boolean isAnyLiquid(AxisAlignedBB p_72953_1_) {
		return orginal.isAnyLiquid(p_72953_1_);
	}

	public boolean isAnyOnFire(AxisAlignedBB p_147470_1_) {
		return orginal.isAnyOnFire(p_147470_1_);
	}

	public boolean handleMaterialAcceleration(AxisAlignedBB p_72918_1_, Material p_72918_2_, Entity p_72918_3_) {
		return orginal.handleMaterialAcceleration(p_72918_1_, p_72918_2_, p_72918_3_);
	}

	public boolean isMaterialInBB(AxisAlignedBB p_72875_1_, Material p_72875_2_) {
		return orginal.isMaterialInBB(p_72875_1_, p_72875_2_);
	}

	public boolean isAABBInMaterial(AxisAlignedBB p_72830_1_, Material p_72830_2_) {
		return orginal.isAABBInMaterial(p_72830_1_, p_72830_2_);
	}

	public Explosion createExplosion(
			Entity p_72876_1_,
			double p_72876_2_,
			double p_72876_4_,
			double p_72876_6_,
			float p_72876_8_,
			boolean p_72876_9_) {
		return orginal.createExplosion(p_72876_1_, p_72876_2_, p_72876_4_, p_72876_6_, p_72876_8_, p_72876_9_);
	}

	public Explosion newExplosion(
			Entity p_72885_1_,
			double p_72885_2_,
			double p_72885_4_,
			double p_72885_6_,
			float p_72885_8_,
			boolean p_72885_9_,
			boolean p_72885_10_) {
		return orginal
				.newExplosion(p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, p_72885_9_, p_72885_10_);
	}

	public float getBlockDensity(Vec3 p_72842_1_, AxisAlignedBB p_72842_2_) {
		return orginal.getBlockDensity(p_72842_1_, p_72842_2_);
	}

	public boolean extinguishFire(
			EntityPlayer p_72886_1_,
			int p_72886_2_,
			int p_72886_3_,
			int p_72886_4_,
			int p_72886_5_) {
		return orginal.extinguishFire(p_72886_1_, p_72886_2_, p_72886_3_, p_72886_4_, p_72886_5_);
	}

	public String getProviderName() {
		return orginal.getProviderName();
	}

	public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_) {
		return orginal.getTileEntity(p_147438_1_, p_147438_2_, p_147438_3_);
	}

	public void setTileEntity(int p_147455_1_, int p_147455_2_, int p_147455_3_, TileEntity p_147455_4_) {
		orginal.setTileEntity(p_147455_1_, p_147455_2_, p_147455_3_, p_147455_4_);
	}

	public void removeTileEntity(int p_147475_1_, int p_147475_2_, int p_147475_3_) {
		orginal.removeTileEntity(p_147475_1_, p_147475_2_, p_147475_3_);
	}

	public void listenToChunkUnload(TileEntity p_147457_1_) {
		orginal.listenToChunkUnload(p_147457_1_);
	}

	public boolean isOversizedCube(int x, int y, int z) {
		return orginal.isOversizedCube(x, y, z);
	}

	public boolean isBlockNormalCubeDefault(int p_147445_1_, int p_147445_2_, int p_147445_3_, boolean p_147445_4_) {
		return orginal.isBlockNormalCubeDefault(p_147445_1_, p_147445_2_, p_147445_3_, p_147445_4_);
	}

	public void calculateInitialSkylight() {
		orginal.calculateInitialSkylight();
	}

	public void setAllowedSpawnTypes(boolean p_72891_1_, boolean p_72891_2_) {
		orginal.setAllowedSpawnTypes(p_72891_1_, p_72891_2_);
	}

	public void calculateInitialWeatherBody() {
		orginal.calculateInitialWeatherBody();
	}

	public void updateWeatherBody() {
		orginal.updateWeatherBody();
	}

	public boolean isBlockFreezable(int p_72884_1_, int p_72884_2_, int p_72884_3_) {
		return orginal.isBlockFreezable(p_72884_1_, p_72884_2_, p_72884_3_);
	}

	public boolean isBlockFreezableNaturally(int p_72850_1_, int p_72850_2_, int p_72850_3_) {
		return orginal.isBlockFreezableNaturally(p_72850_1_, p_72850_2_, p_72850_3_);
	}

	public boolean canBlockFreeze(int p_72834_1_, int p_72834_2_, int p_72834_3_, boolean p_72834_4_) {
		return orginal.canBlockFreeze(p_72834_1_, p_72834_2_, p_72834_3_, p_72834_4_);
	}

	public boolean canBlockFreezeBody(int p_72834_1_, int p_72834_2_, int p_72834_3_, boolean p_72834_4_) {
		return orginal.canBlockFreezeBody(p_72834_1_, p_72834_2_, p_72834_3_, p_72834_4_);
	}

	public boolean canSnowAt(int x, int y, int z, boolean checkLight) {
		return orginal.canSnowAt(x, y, z, checkLight);
	}

	public boolean canSnowAtBody(int p_147478_1_, int p_147478_2_, int p_147478_3_, boolean p_147478_4_) {
		return orginal.canSnowAtBody(p_147478_1_, p_147478_2_, p_147478_3_, p_147478_4_);
	}

	public boolean updateLight(int x, int y, int z) {
		return orginal.updateLight(x, y, z);
	}

	public boolean updateLightByType(EnumSkyBlock p_147463_1_, int p_147463_2_, int p_147463_3_, int p_147463_4_) {
		return orginal.updateLightByType(p_147463_1_, p_147463_2_, p_147463_3_, p_147463_4_);
	}

	public List<Entity> getEntitiesWithinAABBExcludingEntity(Entity p_72839_1_, AxisAlignedBB p_72839_2_) {
		return orginal.getEntitiesWithinAABBExcludingEntity(p_72839_1_, p_72839_2_);
	}

	public List<Entity> getEntitiesWithinAABBExcludingEntity(
			Entity p_94576_1_,
			AxisAlignedBB p_94576_2_,
			IEntitySelector p_94576_3_) {
		return orginal.getEntitiesWithinAABBExcludingEntity(p_94576_1_, p_94576_2_, p_94576_3_);
	}

	public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> p_72872_1_, AxisAlignedBB p_72872_2_) {
		return orginal.getEntitiesWithinAABB(p_72872_1_, p_72872_2_);
	}

	public <T extends Entity> List<T> selectEntitiesWithinAABB(
			Class<? extends T> p_82733_1_,
			AxisAlignedBB p_82733_2_,
			IEntitySelector p_82733_3_) {
		return orginal.selectEntitiesWithinAABB(p_82733_1_, p_82733_2_, p_82733_3_);
	}

	public Entity findNearestEntityWithinAABB(Class<?> p_72857_1_, AxisAlignedBB p_72857_2_, Entity p_72857_3_) {
		return orginal.findNearestEntityWithinAABB(p_72857_1_, p_72857_2_, p_72857_3_);
	}

	public Entity getEntityByID(int p_73045_1_) {
		return orginal.getEntityByID(p_73045_1_);
	}

	public List<Entity> getLoadedEntityList() {
		return orginal.getLoadedEntityList();
	}

	public void markTileEntityChunkModified(int p_147476_1_, int p_147476_2_, int p_147476_3_, TileEntity p_147476_4_) {
		orginal.markTileEntityChunkModified(p_147476_1_, p_147476_2_, p_147476_3_, p_147476_4_);
	}

	public int countEntities(Class<?> p_72907_1_) {
		return orginal.countEntities(p_72907_1_);
	}

	public void addLoadedEntities(List<Entity> p_72868_1_) {
		orginal.addLoadedEntities(p_72868_1_);
	}

	public void unloadEntities(List<Entity> p_72828_1_) {
		orginal.unloadEntities(p_72828_1_);
	}

	public boolean canPlaceEntityOnSide(
			Block p_147472_1_,
			int p_147472_2_,
			int p_147472_3_,
			int p_147472_4_,
			boolean p_147472_5_,
			int p_147472_6_,
			Entity p_147472_7_,
			ItemStack p_147472_8_) {
		return orginal.canPlaceEntityOnSide(
				p_147472_1_,
				p_147472_2_,
				p_147472_3_,
				p_147472_4_,
				p_147472_5_,
				p_147472_6_,
				p_147472_7_,
				p_147472_8_);
	}

	public PathEntity getPathEntityToEntity(
			Entity p_72865_1_,
			Entity p_72865_2_,
			float p_72865_3_,
			boolean p_72865_4_,
			boolean p_72865_5_,
			boolean p_72865_6_,
			boolean p_72865_7_) {
		return orginal.getPathEntityToEntity(
				p_72865_1_,
				p_72865_2_,
				p_72865_3_,
				p_72865_4_,
				p_72865_5_,
				p_72865_6_,
				p_72865_7_);
	}

	public PathEntity getEntityPathToXYZ(
			Entity p_72844_1_,
			int p_72844_2_,
			int p_72844_3_,
			int p_72844_4_,
			float p_72844_5_,
			boolean p_72844_6_,
			boolean p_72844_7_,
			boolean p_72844_8_,
			boolean p_72844_9_) {
		return orginal.getEntityPathToXYZ(
				p_72844_1_,
				p_72844_2_,
				p_72844_3_,
				p_72844_4_,
				p_72844_5_,
				p_72844_6_,
				p_72844_7_,
				p_72844_8_,
				p_72844_9_);
	}

	public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_) {
		return orginal.isBlockProvidingPowerTo(p_72879_1_, p_72879_2_, p_72879_3_, p_72879_4_);
	}

	public int getBlockPowerInput(int p_94577_1_, int p_94577_2_, int p_94577_3_) {
		return orginal.getBlockPowerInput(p_94577_1_, p_94577_2_, p_94577_3_);
	}

	public boolean getIndirectPowerOutput(int p_94574_1_, int p_94574_2_, int p_94574_3_, int p_94574_4_) {
		return orginal.getIndirectPowerOutput(p_94574_1_, p_94574_2_, p_94574_3_, p_94574_4_);
	}

	public int getIndirectPowerLevelTo(int p_72878_1_, int p_72878_2_, int p_72878_3_, int p_72878_4_) {
		return orginal.getIndirectPowerLevelTo(p_72878_1_, p_72878_2_, p_72878_3_, p_72878_4_);
	}

	public boolean isBlockIndirectlyGettingPowered(int p_72864_1_, int p_72864_2_, int p_72864_3_) {
		return orginal.isBlockIndirectlyGettingPowered(p_72864_1_, p_72864_2_, p_72864_3_);
	}

	public int getStrongestIndirectPower(int p_94572_1_, int p_94572_2_, int p_94572_3_) {
		return orginal.getStrongestIndirectPower(p_94572_1_, p_94572_2_, p_94572_3_);
	}

	public EntityPlayer getClosestPlayerToEntity(Entity p_72890_1_, double p_72890_2_) {
		return orginal.getClosestPlayerToEntity(p_72890_1_, p_72890_2_);
	}

	public EntityPlayer getClosestPlayer(double p_72977_1_, double p_72977_3_, double p_72977_5_, double p_72977_7_) {
		return orginal.getClosestPlayer(p_72977_1_, p_72977_3_, p_72977_5_, p_72977_7_);
	}

	public EntityPlayer getClosestVulnerablePlayerToEntity(Entity p_72856_1_, double p_72856_2_) {
		return orginal.getClosestVulnerablePlayerToEntity(p_72856_1_, p_72856_2_);
	}

	public EntityPlayer getClosestVulnerablePlayer(
			double p_72846_1_,
			double p_72846_3_,
			double p_72846_5_,
			double p_72846_7_) {
		return orginal.getClosestVulnerablePlayer(p_72846_1_, p_72846_3_, p_72846_5_, p_72846_7_);
	}

	public EntityPlayer getPlayerEntityByName(String p_72924_1_) {
		return orginal.getPlayerEntityByName(p_72924_1_);
	}

	public EntityPlayer getPlayerByUUID(UUID uuid) {
		return orginal.getPlayerByUUID(uuid);
	}

	public void checkSessionLock() throws MinecraftException {
		orginal.checkSessionLock();
	}

	public long getSeed() {
		return orginal.getSeed();
	}

	public long getTotalWorldTime() {
		return orginal.getTotalWorldTime();
	}

	public long getWorldTime() {
		return orginal.getWorldTime();
	}

	public ChunkCoordinates getSpawnPoint() {
		return orginal.getSpawnPoint();
	}

	public void setSpawnLocation(int p_72950_1_, int p_72950_2_, int p_72950_3_) {
		orginal.setSpawnLocation(p_72950_1_, p_72950_2_, p_72950_3_);
	}

	public void joinEntityInSurroundings(Entity p_72897_1_) {
		orginal.joinEntityInSurroundings(p_72897_1_);
	}

	public boolean canMineBlock(EntityPlayer p_72962_1_, int p_72962_2_, int p_72962_3_, int p_72962_4_) {
		return orginal.canMineBlock(p_72962_1_, p_72962_2_, p_72962_3_, p_72962_4_);
	}

	public boolean canMineBlockBody(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
		return orginal.canMineBlockBody(par1EntityPlayer, par2, par3, par4);
	}

	public void setEntityState(Entity p_72960_1_, byte p_72960_2_) {
		orginal.setEntityState(p_72960_1_, p_72960_2_);
	}

	public IChunkProvider getChunkProvider() {
		return orginal.getChunkProvider();
	}

	public void addBlockEvent(
			int p_147452_1_,
			int p_147452_2_,
			int p_147452_3_,
			Block p_147452_4_,
			int p_147452_5_,
			int p_147452_6_) {
		orginal.addBlockEvent(p_147452_1_, p_147452_2_, p_147452_3_, p_147452_4_, p_147452_5_, p_147452_6_);
	}

	public ISaveHandler getSaveHandler() {
		return orginal.getSaveHandler();
	}

	public WorldInfo getWorldInfo() {
		return orginal.getWorldInfo();
	}

	public GameRules getGameRules() {
		return orginal.getGameRules();
	}

	public void updateAllPlayersSleepingFlag() {
		orginal.updateAllPlayersSleepingFlag();
	}

	public float getWeightedThunderStrength(float p_72819_1_) {
		return orginal.getWeightedThunderStrength(p_72819_1_);
	}

	public float getRainStrength(float p_72867_1_) {
		return orginal.getRainStrength(p_72867_1_);
	}

	public boolean isThundering() {
		return orginal.isThundering();
	}

	public boolean isRaining() {
		return orginal.isRaining();
	}

	public boolean canLightningStrikeAt(int p_72951_1_, int p_72951_2_, int p_72951_3_) {
		return orginal.canLightningStrikeAt(p_72951_1_, p_72951_2_, p_72951_3_);
	}

	public boolean isBlockHighHumidity(int p_72958_1_, int p_72958_2_, int p_72958_3_) {
		return orginal.isBlockHighHumidity(p_72958_1_, p_72958_2_, p_72958_3_);
	}

	public void setItemData(String p_72823_1_, WorldSavedData p_72823_2_) {
		orginal.setItemData(p_72823_1_, p_72823_2_);
	}

	public WorldSavedData loadItemData(Class<?> p_72943_1_, String p_72943_2_) {
		return orginal.loadItemData(p_72943_1_, p_72943_2_);
	}

	public int getUniqueDataId(String p_72841_1_) {
		return orginal.getUniqueDataId(p_72841_1_);
	}

	public void playBroadcastSound(int p_82739_1_, int p_82739_2_, int p_82739_3_, int p_82739_4_, int p_82739_5_) {
		orginal.playBroadcastSound(p_82739_1_, p_82739_2_, p_82739_3_, p_82739_4_, p_82739_5_);
	}

	public void playAuxSFX(int p_72926_1_, int p_72926_2_, int p_72926_3_, int p_72926_4_, int p_72926_5_) {
		orginal.playAuxSFX(p_72926_1_, p_72926_2_, p_72926_3_, p_72926_4_, p_72926_5_);
	}

	public void playAuxSFXAtEntity(
			EntityPlayer p_72889_1_,
			int p_72889_2_,
			int p_72889_3_,
			int p_72889_4_,
			int p_72889_5_,
			int p_72889_6_) {
		orginal.playAuxSFXAtEntity(p_72889_1_, p_72889_2_, p_72889_3_, p_72889_4_, p_72889_5_, p_72889_6_);
	}

	public int getHeight() {
		return orginal.getHeight();
	}

	public int getActualHeight() {
		return orginal.getActualHeight();
	}

	public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_) {
		return orginal.setRandomSeed(p_72843_1_, p_72843_2_, p_72843_3_);
	}

	public ChunkPosition findClosestStructure(String p_147440_1_, int p_147440_2_, int p_147440_3_, int p_147440_4_) {
		return orginal.findClosestStructure(p_147440_1_, p_147440_2_, p_147440_3_, p_147440_4_);
	}

	public boolean extendedLevelsInChunkCache() {
		return orginal.extendedLevelsInChunkCache();
	}

	public double getHorizon() {
		return orginal.getHorizon();
	}

	public void destroyBlockInWorldPartially(
			int p_147443_1_,
			int p_147443_2_,
			int p_147443_3_,
			int p_147443_4_,
			int p_147443_5_) {
		orginal.destroyBlockInWorldPartially(p_147443_1_, p_147443_2_, p_147443_3_, p_147443_4_, p_147443_5_);
	}

	public Calendar getCurrentDate() {
		return orginal.getCurrentDate();
	}

	public void makeFireworks(
			double p_92088_1_,
			double p_92088_3_,
			double p_92088_5_,
			double p_92088_7_,
			double p_92088_9_,
			double p_92088_11_,
			NBTTagCompound p_92088_13_) {
		orginal.makeFireworks(p_92088_1_, p_92088_3_, p_92088_5_, p_92088_7_, p_92088_9_, p_92088_11_, p_92088_13_);
	}

	public Scoreboard getScoreboard() {
		return orginal.getScoreboard();
	}

	public void firePropagatingBlockUpdate(int x, int y, int z, Block unused) {
		orginal.firePropagatingBlockUpdate(x, y, z, unused);
	}

	public float difficultyEnhancedRandom(double locX, double locY, double locZ) {
		return orginal.difficultyEnhancedRandom(locX, locY, locZ);
	}

	public float difficultyEnhancedRandom(int x, int y, int z) {
		return orginal.difficultyEnhancedRandom(x, y, z);
	}

	public void fireStaticEntityChanged() {
		orginal.fireStaticEntityChanged();
	}

	public void addTileEntity(TileEntity entity) {
		orginal.addTileEntity(entity);
	}

	public boolean isSideSolid(int x, int y, int z, ForgeDirection side) {
		return orginal.isSideSolid(x, y, z, side);
	}

	public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
		return orginal.isSideSolid(x, y, z, side, _default);
	}

	public ImmutableSetMultimap<ChunkCoordIntPair, Ticket> getPersistentChunks() {
		return orginal.getPersistentChunks();
	}

	public int getBlockLightOpacity(int x, int y, int z) {
		return orginal.getBlockLightOpacity(x, y, z);
	}

	public int countEntities(EnumCreatureType type, boolean forSpawnCount) {
		return orginal.countEntities(type, forSpawnCount);
	}
	// TODO: finish offsetting all coordinate-specific calls
}
