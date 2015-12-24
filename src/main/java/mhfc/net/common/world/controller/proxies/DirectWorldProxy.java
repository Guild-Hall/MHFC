package mhfc.net.common.world.controller.proxies;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
import net.minecraft.world.World;
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

public class DirectWorldProxy implements IWorldProxy {
	private final World world;

	public DirectWorldProxy(World world) {
		this.world = Objects.requireNonNull(world);
	}

	@Override
	public ArrayList<BlockSnapshot> getCapturedBlockSnapshots() {
		return this.world.capturedBlockSnapshots;
	}

	@Override
	public EnumDifficulty getDifficultySetting() {
		return this.world.difficultySetting;
	}

	@Override
	public boolean isRemote() {
		return this.world.isRemote;
	}

	@Override
	public MapStorage getMapStorage() {
		return this.world.mapStorage;
	}

	@Override
	public MapStorage getPerWorldStorage() {
		return this.world.perWorldStorage;
	}

	@Override
	public List<EntityPlayer> getPlayerEntities() {
		return this.world.playerEntities;
	}

	@Override
	public WorldProvider getProvider() {
		return this.world.provider;
	}

	@Override
	public Random getRand() {
		return this.world.rand;
	}

	@Override
	public Profiler getProfiler() {
		return this.world.theProfiler;
	}

	@Override
	public VillageCollection getVillageCollection() {
		return this.world.villageCollectionObj;
	}

	@Override
	public List<Entity> getWeatherEffects() {
		return this.world.weatherEffects;
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_) {
		return world.getBiomeGenForCoords(p_72807_1_, p_72807_2_);
	}

	@Override
	public BiomeGenBase getBiomeGenForCoordsBody(int p_72807_1_, int p_72807_2_) {
		return world.getBiomeGenForCoordsBody(p_72807_1_, p_72807_2_);
	}

	@Override
	public WorldChunkManager getWorldChunkManager() {
		return world.getWorldChunkManager();
	}

	@Override
	public void setSpawnLocation() {
		world.setSpawnLocation();
	}

	@Override
	public Block getTopBlock(int p_147474_1_, int p_147474_2_) {
		return world.getTopBlock(p_147474_1_, p_147474_2_);
	}

	@Override
	public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_) {
		return world.getBlock(p_147439_1_, p_147439_2_, p_147439_3_);
	}

	@Override
	public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
		return world.isAirBlock(p_147437_1_, p_147437_2_, p_147437_3_);
	}

	@Override
	public boolean blockExists(int p_72899_1_, int p_72899_2_, int p_72899_3_) {
		return world.blockExists(p_72899_1_, p_72899_2_, p_72899_3_);
	}

	@Override
	public boolean doChunksNearChunkExist(int p_72873_1_, int p_72873_2_, int p_72873_3_, int p_72873_4_) {
		return world.doChunksNearChunkExist(p_72873_1_, p_72873_2_, p_72873_3_, p_72873_4_);
	}

	@Override
	public boolean checkChunksExist(
			int p_72904_1_,
			int p_72904_2_,
			int p_72904_3_,
			int p_72904_4_,
			int p_72904_5_,
			int p_72904_6_) {
		return world.checkChunksExist(p_72904_1_, p_72904_2_, p_72904_3_, p_72904_4_, p_72904_5_, p_72904_6_);
	}

	@Override
	public Chunk getChunkFromBlockCoords(int p_72938_1_, int p_72938_2_) {
		return world.getChunkFromBlockCoords(p_72938_1_, p_72938_2_);
	}

	@Override
	public Chunk getChunkFromChunkCoords(int p_72964_1_, int p_72964_2_) {
		return world.getChunkFromChunkCoords(p_72964_1_, p_72964_2_);
	}

	@Override
	public boolean setBlock(
			int p_147465_1_,
			int p_147465_2_,
			int p_147465_3_,
			Block p_147465_4_,
			int p_147465_5_,
			int p_147465_6_) {
		return world.setBlock(p_147465_1_, p_147465_2_, p_147465_3_, p_147465_4_, p_147465_5_, p_147465_6_);
	}

	@Override
	public void markAndNotifyBlock(int x, int y, int z, Chunk chunk, Block oldBlock, Block newBlock, int flag) {
		world.markAndNotifyBlock(x, y, z, chunk, oldBlock, newBlock, flag);
	}

	@Override
	public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_) {
		return world.getBlockMetadata(p_72805_1_, p_72805_2_, p_72805_3_);
	}

	@Override
	public boolean setBlockMetadataWithNotify(
			int p_72921_1_,
			int p_72921_2_,
			int p_72921_3_,
			int p_72921_4_,
			int p_72921_5_) {
		return world.setBlockMetadataWithNotify(p_72921_1_, p_72921_2_, p_72921_3_, p_72921_4_, p_72921_5_);
	}

	@Override
	public boolean setBlockToAir(int p_147468_1_, int p_147468_2_, int p_147468_3_) {
		return world.setBlockToAir(p_147468_1_, p_147468_2_, p_147468_3_);
	}

	@Override
	public boolean func_147480_a(int p_147480_1_, int p_147480_2_, int p_147480_3_, boolean p_147480_4_) {
		return world.func_147480_a(p_147480_1_, p_147480_2_, p_147480_3_, p_147480_4_);
	}

	@Override
	public boolean setBlock(int p_147449_1_, int p_147449_2_, int p_147449_3_, Block p_147449_4_) {
		return world.setBlock(p_147449_1_, p_147449_2_, p_147449_3_, p_147449_4_);
	}

	@Override
	public void markBlockForUpdate(int p_147471_1_, int p_147471_2_, int p_147471_3_) {
		world.markBlockForUpdate(p_147471_1_, p_147471_2_, p_147471_3_);
	}

	@Override
	public void notifyBlockChange(int p_147444_1_, int p_147444_2_, int p_147444_3_, Block p_147444_4_) {
		world.notifyBlockChange(p_147444_1_, p_147444_2_, p_147444_3_, p_147444_4_);
	}

	@Override
	public void markBlocksDirtyVertical(int p_72975_1_, int p_72975_2_, int p_72975_3_, int p_72975_4_) {
		world.markBlocksDirtyVertical(p_72975_1_, p_72975_2_, p_72975_3_, p_72975_4_);
	}

	@Override
	public void markBlockRangeForRenderUpdate(
			int p_147458_1_,
			int p_147458_2_,
			int p_147458_3_,
			int p_147458_4_,
			int p_147458_5_,
			int p_147458_6_) {
		world.markBlockRangeForRenderUpdate(
				p_147458_1_,
				p_147458_2_,
				p_147458_3_,
				p_147458_4_,
				p_147458_5_,
				p_147458_6_);
	}

	@Override
	public void notifyBlocksOfNeighborChange(int p_147459_1_, int p_147459_2_, int p_147459_3_, Block p_147459_4_) {
		world.notifyBlocksOfNeighborChange(p_147459_1_, p_147459_2_, p_147459_3_, p_147459_4_);
	}

	@Override
	public void notifyBlocksOfNeighborChange(
			int p_147441_1_,
			int p_147441_2_,
			int p_147441_3_,
			Block p_147441_4_,
			int p_147441_5_) {
		world.notifyBlocksOfNeighborChange(p_147441_1_, p_147441_2_, p_147441_3_, p_147441_4_, p_147441_5_);
	}

	@Override
	public void notifyBlockOfNeighborChange(int p_147460_1_, int p_147460_2_, int p_147460_3_, Block p_147460_4_) {
		world.notifyBlockOfNeighborChange(p_147460_1_, p_147460_2_, p_147460_3_, p_147460_4_);
	}

	@Override
	public boolean isBlockTickScheduledThisTick(int p_147477_1_, int p_147477_2_, int p_147477_3_, Block p_147477_4_) {
		return world.isBlockTickScheduledThisTick(p_147477_1_, p_147477_2_, p_147477_3_, p_147477_4_);
	}

	@Override
	public boolean canBlockSeeTheSky(int p_72937_1_, int p_72937_2_, int p_72937_3_) {
		return world.canBlockSeeTheSky(p_72937_1_, p_72937_2_, p_72937_3_);
	}

	@Override
	public int getFullBlockLightValue(int p_72883_1_, int p_72883_2_, int p_72883_3_) {
		return world.getFullBlockLightValue(p_72883_1_, p_72883_2_, p_72883_3_);
	}

	@Override
	public int getBlockLightValue(int p_72957_1_, int p_72957_2_, int p_72957_3_) {
		return world.getBlockLightValue(p_72957_1_, p_72957_2_, p_72957_3_);
	}

	@Override
	public int getBlockLightValue_do(int p_72849_1_, int p_72849_2_, int p_72849_3_, boolean p_72849_4_) {
		return world.getBlockLightValue_do(p_72849_1_, p_72849_2_, p_72849_3_, p_72849_4_);
	}

	@Override
	public int getHeightValue(int p_72976_1_, int p_72976_2_) {
		return world.getHeightValue(p_72976_1_, p_72976_2_);
	}

	@Override
	public int getChunkHeightMapMinimum(int p_82734_1_, int p_82734_2_) {
		return world.getChunkHeightMapMinimum(p_82734_1_, p_82734_2_);
	}

	@Override
	public int getSkyBlockTypeBrightness(EnumSkyBlock p_72925_1_, int p_72925_2_, int p_72925_3_, int p_72925_4_) {
		return world.getSkyBlockTypeBrightness(p_72925_1_, p_72925_2_, p_72925_3_, p_72925_4_);
	}

	@Override
	public int getSavedLightValue(EnumSkyBlock p_72972_1_, int p_72972_2_, int p_72972_3_, int p_72972_4_) {
		return world.getSavedLightValue(p_72972_1_, p_72972_2_, p_72972_3_, p_72972_4_);
	}

	@Override
	public void setLightValue(EnumSkyBlock p_72915_1_, int p_72915_2_, int p_72915_3_, int p_72915_4_, int p_72915_5_) {
		world.setLightValue(p_72915_1_, p_72915_2_, p_72915_3_, p_72915_4_, p_72915_5_);
	}

	@Override
	public void notifyForRenderUpdate(int x, int y, int z) {
		world.func_147479_m(x, y, z);
	}

	@Override
	public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_) {
		return world.getLightBrightnessForSkyBlocks(p_72802_1_, p_72802_2_, p_72802_3_, p_72802_4_);
	}

	@Override
	public float getLightBrightness(int p_72801_1_, int p_72801_2_, int p_72801_3_) {
		return world.getLightBrightness(p_72801_1_, p_72801_2_, p_72801_3_);
	}

	@Override
	public boolean isDaytime() {
		return world.isDaytime();
	}

	@Override
	public MovingObjectPosition rayTraceBlocks(Vec3 p_72933_1_, Vec3 p_72933_2_) {
		return world.rayTraceBlocks(p_72933_1_, p_72933_2_);
	}

	@Override
	public MovingObjectPosition rayTraceBlocks(Vec3 p_72901_1_, Vec3 p_72901_2_, boolean p_72901_3_) {
		return world.rayTraceBlocks(p_72901_1_, p_72901_2_, p_72901_3_);
	}

	@Override
	public MovingObjectPosition checkCollisionUnprecise(
			Vec3 startPos,
			Vec3 endPos,
			boolean holdsBoat,
			boolean requireCollisionBox,
			boolean collideWithNonBlocks) {
		return world.func_147447_a(startPos, endPos, holdsBoat, requireCollisionBox, collideWithNonBlocks);
	}

	@Override
	public void playSoundAtEntity(Entity p_72956_1_, String p_72956_2_, float p_72956_3_, float p_72956_4_) {
		world.playSoundAtEntity(p_72956_1_, p_72956_2_, p_72956_3_, p_72956_4_);
	}

	@Override
	public void playSoundToNearExcept(EntityPlayer p_85173_1_, String p_85173_2_, float p_85173_3_, float p_85173_4_) {
		world.playSoundToNearExcept(p_85173_1_, p_85173_2_, p_85173_3_, p_85173_4_);
	}

	@Override
	public void playSoundEffect(
			double p_72908_1_,
			double p_72908_3_,
			double p_72908_5_,
			String p_72908_7_,
			float p_72908_8_,
			float p_72908_9_) {
		world.playSoundEffect(p_72908_1_, p_72908_3_, p_72908_5_, p_72908_7_, p_72908_8_, p_72908_9_);
	}

	@Override
	public void playSound(
			double p_72980_1_,
			double p_72980_3_,
			double p_72980_5_,
			String p_72980_7_,
			float p_72980_8_,
			float p_72980_9_,
			boolean p_72980_10_) {
		world.playSound(p_72980_1_, p_72980_3_, p_72980_5_, p_72980_7_, p_72980_8_, p_72980_9_, p_72980_10_);
	}

	@Override
	public void playRecord(String p_72934_1_, int p_72934_2_, int p_72934_3_, int p_72934_4_) {
		world.playRecord(p_72934_1_, p_72934_2_, p_72934_3_, p_72934_4_);
	}

	@Override
	public void spawnParticle(
			String p_72869_1_,
			double p_72869_2_,
			double p_72869_4_,
			double p_72869_6_,
			double p_72869_8_,
			double p_72869_10_,
			double p_72869_12_) {
		world.spawnParticle(p_72869_1_, p_72869_2_, p_72869_4_, p_72869_6_, p_72869_8_, p_72869_10_, p_72869_12_);
	}

	@Override
	public boolean addWeatherEffect(Entity p_72942_1_) {
		return world.addWeatherEffect(p_72942_1_);
	}

	@Override
	public boolean spawnEntityInWorld(Entity p_72838_1_) {
		return world.spawnEntityInWorld(p_72838_1_);
	}

	@Override
	public void onEntityAdded(Entity p_72923_1_) {
		world.onEntityAdded(p_72923_1_);
	}

	@Override
	public void onEntityRemoved(Entity p_72847_1_) {
		world.onEntityRemoved(p_72847_1_);
	}

	@Override
	public void removeEntity(Entity p_72900_1_) {
		world.removeEntity(p_72900_1_);
	}

	@Override
	public void removePlayerEntityDangerously(Entity p_72973_1_) {
		world.removePlayerEntityDangerously(p_72973_1_);
	}

	@Override
	public void addWorldAccess(IWorldAccess p_72954_1_) {
		world.addWorldAccess(p_72954_1_);
	}

	@Override
	public List<AxisAlignedBB> getCollidingBoundingBoxes(Entity p_72945_1_, AxisAlignedBB p_72945_2_) {
		return world.getCollidingBoundingBoxes(p_72945_1_, p_72945_2_);
	}

	@Override
	public List<AxisAlignedBB> getCollidingBlockBoundingBoxes(AxisAlignedBB p_147461_1_) {
		return world.func_147461_a(p_147461_1_);
	}

	@Override
	public int calculateSkylightSubtracted(float p_72967_1_) {
		return world.calculateSkylightSubtracted(p_72967_1_);
	}

	@Override
	public float getSunBrightnessFactor(float p_72967_1_) {
		return world.getSunBrightnessFactor(p_72967_1_);
	}

	@Override
	public void removeWorldAccess(IWorldAccess p_72848_1_) {
		world.removeWorldAccess(p_72848_1_);
	}

	@Override
	public float getSunBrightness(float p_72971_1_) {
		return world.getSunBrightness(p_72971_1_);
	}

	@Override
	public float getSunBrightnessBody(float p_72971_1_) {
		return world.getSunBrightnessBody(p_72971_1_);
	}

	@Override
	public Vec3 getSkyColor(Entity p_72833_1_, float p_72833_2_) {
		return world.getSkyColor(p_72833_1_, p_72833_2_);
	}

	@Override
	public Vec3 getSkyColorBody(Entity p_72833_1_, float p_72833_2_) {
		return world.getSkyColorBody(p_72833_1_, p_72833_2_);
	}

	@Override
	public float getCelestialAngle(float p_72826_1_) {
		return world.getCelestialAngle(p_72826_1_);
	}

	@Override
	public int getMoonPhase() {
		return world.getMoonPhase();
	}

	@Override
	public float getCurrentMoonPhaseFactor() {
		return world.getCurrentMoonPhaseFactor();
	}

	@Override
	public float getCurrentMoonPhaseFactorBody() {
		return world.getCurrentMoonPhaseFactorBody();
	}

	@Override
	public float getCelestialAngleRadians(float p_72929_1_) {
		return world.getCelestialAngleRadians(p_72929_1_);
	}

	@Override
	public Vec3 getCloudColour(float p_72824_1_) {
		return world.getCloudColour(p_72824_1_);
	}

	@Override
	public Vec3 getFogColor(float p_72948_1_) {
		return world.getFogColor(p_72948_1_);
	}

	@Override
	public int getPrecipitationHeight(int p_72874_1_, int p_72874_2_) {
		return world.getPrecipitationHeight(p_72874_1_, p_72874_2_);
	}

	@Override
	public int getTopSolidOrLiquidBlock(int p_72825_1_, int p_72825_2_) {
		return world.getTopSolidOrLiquidBlock(p_72825_1_, p_72825_2_);
	}

	@Override
	public float getStarBrightness(float p_72880_1_) {
		return world.getStarBrightness(p_72880_1_);
	}

	@Override
	public float getStarBrightnessBody(float par1) {
		return world.getStarBrightnessBody(par1);
	}

	@Override
	public void scheduleBlockUpdate(
			int p_147464_1_,
			int p_147464_2_,
			int p_147464_3_,
			Block p_147464_4_,
			int p_147464_5_) {
		world.scheduleBlockUpdate(p_147464_1_, p_147464_2_, p_147464_3_, p_147464_4_, p_147464_5_);
	}

	@Override
	public void scheduleBlockUpdateWithPriority(
			int p_147454_1_,
			int p_147454_2_,
			int p_147454_3_,
			Block p_147454_4_,
			int p_147454_5_,
			int p_147454_6_) {
		world.scheduleBlockUpdateWithPriority(
				p_147454_1_,
				p_147454_2_,
				p_147454_3_,
				p_147454_4_,
				p_147454_5_,
				p_147454_6_);
	}

	@Override
	public void func_147446_b(
			int p_147446_1_,
			int p_147446_2_,
			int p_147446_3_,
			Block p_147446_4_,
			int p_147446_5_,
			int p_147446_6_) {
		world.func_147446_b(p_147446_1_, p_147446_2_, p_147446_3_, p_147446_4_, p_147446_5_, p_147446_6_);
	}

	@Override
	public void updateEntities() {
		world.updateEntities();
	}

	@Override
	public void addTileEntitiesToUpdate(Collection<TileEntity> p_147448_1_) {
		world.func_147448_a(p_147448_1_);
	}

	@Override
	public void updateEntity(Entity p_72870_1_) {
		world.updateEntity(p_72870_1_);
	}

	@Override
	public void updateEntityWithOptionalForce(Entity p_72866_1_, boolean p_72866_2_) {
		world.updateEntityWithOptionalForce(p_72866_1_, p_72866_2_);
	}

	@Override
	public boolean checkNoEntityCollision(AxisAlignedBB p_72855_1_) {
		return world.checkNoEntityCollision(p_72855_1_);
	}

	@Override
	public boolean checkNoEntityCollision(AxisAlignedBB p_72917_1_, Entity p_72917_2_) {
		return world.checkNoEntityCollision(p_72917_1_, p_72917_2_);
	}

	@Override
	public boolean checkBlockCollision(AxisAlignedBB p_72829_1_) {
		return world.checkBlockCollision(p_72829_1_);
	}

	@Override
	public boolean isAnyLiquid(AxisAlignedBB p_72953_1_) {
		return world.isAnyLiquid(p_72953_1_);
	}

	@Override
	public boolean isAnyOnFire(AxisAlignedBB p_147470_1_) {
		return world.func_147470_e(p_147470_1_);
	}

	@Override
	public boolean handleMaterialAcceleration(AxisAlignedBB p_72918_1_, Material p_72918_2_, Entity p_72918_3_) {
		return world.handleMaterialAcceleration(p_72918_1_, p_72918_2_, p_72918_3_);
	}

	@Override
	public boolean isMaterialInBB(AxisAlignedBB p_72875_1_, Material p_72875_2_) {
		return world.isMaterialInBB(p_72875_1_, p_72875_2_);
	}

	@Override
	public boolean isAABBInMaterial(AxisAlignedBB p_72830_1_, Material p_72830_2_) {
		return world.isAABBInMaterial(p_72830_1_, p_72830_2_);
	}

	@Override
	public Explosion createExplosion(
			Entity p_72876_1_,
			double p_72876_2_,
			double p_72876_4_,
			double p_72876_6_,
			float p_72876_8_,
			boolean p_72876_9_) {
		return world.createExplosion(p_72876_1_, p_72876_2_, p_72876_4_, p_72876_6_, p_72876_8_, p_72876_9_);
	}

	@Override
	public Explosion newExplosion(
			Entity p_72885_1_,
			double p_72885_2_,
			double p_72885_4_,
			double p_72885_6_,
			float p_72885_8_,
			boolean p_72885_9_,
			boolean p_72885_10_) {
		return world.newExplosion(p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, p_72885_9_, p_72885_10_);
	}

	@Override
	public float getBlockDensity(Vec3 p_72842_1_, AxisAlignedBB p_72842_2_) {
		return world.getBlockDensity(p_72842_1_, p_72842_2_);
	}

	@Override
	public boolean extinguishFire(
			EntityPlayer p_72886_1_,
			int p_72886_2_,
			int p_72886_3_,
			int p_72886_4_,
			int p_72886_5_) {
		return world.extinguishFire(p_72886_1_, p_72886_2_, p_72886_3_, p_72886_4_, p_72886_5_);
	}

	@Override
	public String getProviderName() {
		return world.getProviderName();
	}

	@Override
	public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_) {
		return world.getTileEntity(p_147438_1_, p_147438_2_, p_147438_3_);
	}

	@Override
	public void setTileEntity(int p_147455_1_, int p_147455_2_, int p_147455_3_, TileEntity p_147455_4_) {
		world.setTileEntity(p_147455_1_, p_147455_2_, p_147455_3_, p_147455_4_);
	}

	@Override
	public void removeTileEntity(int p_147475_1_, int p_147475_2_, int p_147475_3_) {
		world.removeTileEntity(p_147475_1_, p_147475_2_, p_147475_3_);
	}

	@Override
	public void listenToChunkUnload(TileEntity p_147457_1_) {
		world.func_147457_a(p_147457_1_);
	}

	@Override
	public boolean isOversizedCube(int x, int y, int z) {
		return world.func_147469_q(x, y, z);
	}

	@Override
	public boolean isBlockNormalCubeDefault(int p_147445_1_, int p_147445_2_, int p_147445_3_, boolean p_147445_4_) {
		return world.isBlockNormalCubeDefault(p_147445_1_, p_147445_2_, p_147445_3_, p_147445_4_);
	}

	@Override
	public void calculateInitialSkylight() {
		world.calculateInitialSkylight();
	}

	@Override
	public void setAllowedSpawnTypes(boolean p_72891_1_, boolean p_72891_2_) {
		world.setAllowedSpawnTypes(p_72891_1_, p_72891_2_);
	}

	@Override
	public void calculateInitialWeatherBody() {
		world.calculateInitialWeatherBody();
	}

	@Override
	public void updateWeatherBody() {
		world.updateWeatherBody();
	}

	@Override
	public boolean isBlockFreezable(int p_72884_1_, int p_72884_2_, int p_72884_3_) {
		return world.isBlockFreezable(p_72884_1_, p_72884_2_, p_72884_3_);
	}

	@Override
	public boolean isBlockFreezableNaturally(int p_72850_1_, int p_72850_2_, int p_72850_3_) {
		return world.isBlockFreezableNaturally(p_72850_1_, p_72850_2_, p_72850_3_);
	}

	@Override
	public boolean canBlockFreeze(int p_72834_1_, int p_72834_2_, int p_72834_3_, boolean p_72834_4_) {
		return world.canBlockFreeze(p_72834_1_, p_72834_2_, p_72834_3_, p_72834_4_);
	}

	@Override
	public boolean canBlockFreezeBody(int p_72834_1_, int p_72834_2_, int p_72834_3_, boolean p_72834_4_) {
		return world.canBlockFreezeBody(p_72834_1_, p_72834_2_, p_72834_3_, p_72834_4_);
	}

	@Override
	public boolean canSnowAt(int x, int y, int z, boolean checkLight) {
		return world.func_147478_e(x, y, z, checkLight);
	}

	@Override
	public boolean canSnowAtBody(int p_147478_1_, int p_147478_2_, int p_147478_3_, boolean p_147478_4_) {
		return world.canSnowAtBody(p_147478_1_, p_147478_2_, p_147478_3_, p_147478_4_);
	}

	@Override
	public boolean updateLight(int x, int y, int z) {
		return world.func_147451_t(x, y, z);
	}

	@Override
	public boolean updateLightByType(EnumSkyBlock p_147463_1_, int p_147463_2_, int p_147463_3_, int p_147463_4_) {
		return world.updateLightByType(p_147463_1_, p_147463_2_, p_147463_3_, p_147463_4_);
	}

	@Override
	public List<Entity> getEntitiesWithinAABBExcludingEntity(Entity p_72839_1_, AxisAlignedBB p_72839_2_) {
		return world.getEntitiesWithinAABBExcludingEntity(p_72839_1_, p_72839_2_);
	}

	@Override
	public List<Entity> getEntitiesWithinAABBExcludingEntity(
			Entity p_94576_1_,
			AxisAlignedBB p_94576_2_,
			IEntitySelector p_94576_3_) {
		return world.getEntitiesWithinAABBExcludingEntity(p_94576_1_, p_94576_2_, p_94576_3_);
	}

	@Override
	public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> p_72872_1_, AxisAlignedBB p_72872_2_) {
		return world.getEntitiesWithinAABB(p_72872_1_, p_72872_2_);
	}

	@Override
	public <T extends Entity> List<T> selectEntitiesWithinAABB(
			Class<? extends T> p_82733_1_,
			AxisAlignedBB p_82733_2_,
			IEntitySelector p_82733_3_) {
		return world.selectEntitiesWithinAABB(p_82733_1_, p_82733_2_, p_82733_3_);
	}

	@Override
	public Entity findNearestEntityWithinAABB(Class<?> p_72857_1_, AxisAlignedBB p_72857_2_, Entity p_72857_3_) {
		return world.findNearestEntityWithinAABB(p_72857_1_, p_72857_2_, p_72857_3_);
	}

	@Override
	public Entity getEntityByID(int p_73045_1_) {
		return world.getEntityByID(p_73045_1_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public List<Entity> getLoadedEntityList() {
		return world.getLoadedEntityList();
	}

	@Override
	public void markTileEntityChunkModified(int p_147476_1_, int p_147476_2_, int p_147476_3_, TileEntity p_147476_4_) {
		world.markTileEntityChunkModified(p_147476_1_, p_147476_2_, p_147476_3_, p_147476_4_);
	}

	@Override
	public int countEntities(Class<?> p_72907_1_) {
		return world.countEntities(p_72907_1_);
	}

	@Override
	public void addLoadedEntities(List<Entity> p_72868_1_) {
		world.addLoadedEntities(p_72868_1_);
	}

	@Override
	public void unloadEntities(List<Entity> p_72828_1_) {
		world.unloadEntities(p_72828_1_);
	}

	@Override
	public boolean canPlaceEntityOnSide(
			Block p_147472_1_,
			int p_147472_2_,
			int p_147472_3_,
			int p_147472_4_,
			boolean p_147472_5_,
			int p_147472_6_,
			Entity p_147472_7_,
			ItemStack p_147472_8_) {
		return world.canPlaceEntityOnSide(
				p_147472_1_,
				p_147472_2_,
				p_147472_3_,
				p_147472_4_,
				p_147472_5_,
				p_147472_6_,
				p_147472_7_,
				p_147472_8_);
	}

	@Override
	public PathEntity getPathEntityToEntity(
			Entity p_72865_1_,
			Entity p_72865_2_,
			float p_72865_3_,
			boolean p_72865_4_,
			boolean p_72865_5_,
			boolean p_72865_6_,
			boolean p_72865_7_) {
		return world.getPathEntityToEntity(
				p_72865_1_,
				p_72865_2_,
				p_72865_3_,
				p_72865_4_,
				p_72865_5_,
				p_72865_6_,
				p_72865_7_);
	}

	@Override
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
		return world.getEntityPathToXYZ(
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

	@Override
	public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_) {
		return world.isBlockProvidingPowerTo(p_72879_1_, p_72879_2_, p_72879_3_, p_72879_4_);
	}

	@Override
	public int getBlockPowerInput(int p_94577_1_, int p_94577_2_, int p_94577_3_) {
		return world.getBlockPowerInput(p_94577_1_, p_94577_2_, p_94577_3_);
	}

	@Override
	public boolean getIndirectPowerOutput(int p_94574_1_, int p_94574_2_, int p_94574_3_, int p_94574_4_) {
		return world.getIndirectPowerOutput(p_94574_1_, p_94574_2_, p_94574_3_, p_94574_4_);
	}

	@Override
	public int getIndirectPowerLevelTo(int p_72878_1_, int p_72878_2_, int p_72878_3_, int p_72878_4_) {
		return world.getIndirectPowerLevelTo(p_72878_1_, p_72878_2_, p_72878_3_, p_72878_4_);
	}

	@Override
	public boolean isBlockIndirectlyGettingPowered(int p_72864_1_, int p_72864_2_, int p_72864_3_) {
		return world.isBlockIndirectlyGettingPowered(p_72864_1_, p_72864_2_, p_72864_3_);
	}

	@Override
	public int getStrongestIndirectPower(int p_94572_1_, int p_94572_2_, int p_94572_3_) {
		return world.getStrongestIndirectPower(p_94572_1_, p_94572_2_, p_94572_3_);
	}

	@Override
	public EntityPlayer getClosestPlayerToEntity(Entity p_72890_1_, double p_72890_2_) {
		return world.getClosestPlayerToEntity(p_72890_1_, p_72890_2_);
	}

	@Override
	public EntityPlayer getClosestPlayer(double p_72977_1_, double p_72977_3_, double p_72977_5_, double p_72977_7_) {
		return world.getClosestPlayer(p_72977_1_, p_72977_3_, p_72977_5_, p_72977_7_);
	}

	@Override
	public EntityPlayer getClosestVulnerablePlayerToEntity(Entity p_72856_1_, double p_72856_2_) {
		return world.getClosestVulnerablePlayerToEntity(p_72856_1_, p_72856_2_);
	}

	@Override
	public EntityPlayer getClosestVulnerablePlayer(
			double p_72846_1_,
			double p_72846_3_,
			double p_72846_5_,
			double p_72846_7_) {
		return world.getClosestVulnerablePlayer(p_72846_1_, p_72846_3_, p_72846_5_, p_72846_7_);
	}

	@Override
	public EntityPlayer getPlayerEntityByName(String p_72924_1_) {
		return world.getPlayerEntityByName(p_72924_1_);
	}

	@Override
	public EntityPlayer getPlayerByUUID(UUID uuid) {
		return world.func_152378_a(uuid);
	}

	@Override
	public void checkSessionLock() throws MinecraftException {
		world.checkSessionLock();
	}

	@Override
	public long getSeed() {
		return world.getSeed();
	}

	@Override
	public long getTotalWorldTime() {
		return world.getTotalWorldTime();
	}

	@Override
	public long getWorldTime() {
		return world.getWorldTime();
	}

	@Override
	public ChunkCoordinates getSpawnPoint() {
		return world.getSpawnPoint();
	}

	@Override
	public void setSpawnLocation(int p_72950_1_, int p_72950_2_, int p_72950_3_) {
		world.setSpawnLocation(p_72950_1_, p_72950_2_, p_72950_3_);
	}

	@Override
	public void joinEntityInSurroundings(Entity p_72897_1_) {
		world.joinEntityInSurroundings(p_72897_1_);
	}

	@Override
	public boolean canMineBlock(EntityPlayer p_72962_1_, int p_72962_2_, int p_72962_3_, int p_72962_4_) {
		return world.canMineBlock(p_72962_1_, p_72962_2_, p_72962_3_, p_72962_4_);
	}

	@Override
	public boolean canMineBlockBody(EntityPlayer par1EntityPlayer, int par2, int par3, int par4) {
		return world.canMineBlockBody(par1EntityPlayer, par2, par3, par4);
	}

	@Override
	public void setEntityState(Entity p_72960_1_, byte p_72960_2_) {
		world.setEntityState(p_72960_1_, p_72960_2_);
	}

	@Override
	public IChunkProvider getChunkProvider() {
		return world.getChunkProvider();
	}

	@Override
	public void addBlockEvent(
			int p_147452_1_,
			int p_147452_2_,
			int p_147452_3_,
			Block p_147452_4_,
			int p_147452_5_,
			int p_147452_6_) {
		world.addBlockEvent(p_147452_1_, p_147452_2_, p_147452_3_, p_147452_4_, p_147452_5_, p_147452_6_);
	}

	@Override
	public ISaveHandler getSaveHandler() {
		return world.getSaveHandler();
	}

	@Override
	public WorldInfo getWorldInfo() {
		return world.getWorldInfo();
	}

	@Override
	public GameRules getGameRules() {
		return world.getGameRules();
	}

	@Override
	public void updateAllPlayersSleepingFlag() {
		world.updateAllPlayersSleepingFlag();
	}

	@Override
	public float getWeightedThunderStrength(float p_72819_1_) {
		return world.getWeightedThunderStrength(p_72819_1_);
	}

	@Override
	public float getRainStrength(float p_72867_1_) {
		return world.getRainStrength(p_72867_1_);
	}

	@Override
	public boolean isThundering() {
		return world.isThundering();
	}

	@Override
	public boolean isRaining() {
		return world.isRaining();
	}

	@Override
	public boolean canLightningStrikeAt(int p_72951_1_, int p_72951_2_, int p_72951_3_) {
		return world.canLightningStrikeAt(p_72951_1_, p_72951_2_, p_72951_3_);
	}

	@Override
	public boolean isBlockHighHumidity(int p_72958_1_, int p_72958_2_, int p_72958_3_) {
		return world.isBlockHighHumidity(p_72958_1_, p_72958_2_, p_72958_3_);
	}

	@Override
	public void setItemData(String p_72823_1_, WorldSavedData p_72823_2_) {
		world.setItemData(p_72823_1_, p_72823_2_);
	}

	@Override
	public WorldSavedData loadItemData(Class<?> p_72943_1_, String p_72943_2_) {
		return world.loadItemData(p_72943_1_, p_72943_2_);
	}

	@Override
	public int getUniqueDataId(String p_72841_1_) {
		return world.getUniqueDataId(p_72841_1_);
	}

	@Override
	public void playBroadcastSound(int p_82739_1_, int p_82739_2_, int p_82739_3_, int p_82739_4_, int p_82739_5_) {
		world.playBroadcastSound(p_82739_1_, p_82739_2_, p_82739_3_, p_82739_4_, p_82739_5_);
	}

	@Override
	public void playAuxSFX(int p_72926_1_, int p_72926_2_, int p_72926_3_, int p_72926_4_, int p_72926_5_) {
		world.playAuxSFX(p_72926_1_, p_72926_2_, p_72926_3_, p_72926_4_, p_72926_5_);
	}

	@Override
	public void playAuxSFXAtEntity(
			EntityPlayer p_72889_1_,
			int p_72889_2_,
			int p_72889_3_,
			int p_72889_4_,
			int p_72889_5_,
			int p_72889_6_) {
		world.playAuxSFXAtEntity(p_72889_1_, p_72889_2_, p_72889_3_, p_72889_4_, p_72889_5_, p_72889_6_);
	}

	@Override
	public int getHeight() {
		return world.getHeight();
	}

	@Override
	public int getActualHeight() {
		return world.getActualHeight();
	}

	@Override
	public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_) {
		return world.setRandomSeed(p_72843_1_, p_72843_2_, p_72843_3_);
	}

	@Override
	public ChunkPosition findClosestStructure(String p_147440_1_, int p_147440_2_, int p_147440_3_, int p_147440_4_) {
		return world.findClosestStructure(p_147440_1_, p_147440_2_, p_147440_3_, p_147440_4_);
	}

	@Override
	public boolean extendedLevelsInChunkCache() {
		return world.extendedLevelsInChunkCache();
	}

	@Override
	public double getHorizon() {
		return world.getHorizon();
	}

	@Override
	public void destroyBlockInWorldPartially(
			int p_147443_1_,
			int p_147443_2_,
			int p_147443_3_,
			int p_147443_4_,
			int p_147443_5_) {
		world.destroyBlockInWorldPartially(p_147443_1_, p_147443_2_, p_147443_3_, p_147443_4_, p_147443_5_);
	}

	@Override
	public Calendar getCurrentDate() {
		return world.getCurrentDate();
	}

	@Override
	public void makeFireworks(
			double p_92088_1_,
			double p_92088_3_,
			double p_92088_5_,
			double p_92088_7_,
			double p_92088_9_,
			double p_92088_11_,
			NBTTagCompound p_92088_13_) {
		world.makeFireworks(p_92088_1_, p_92088_3_, p_92088_5_, p_92088_7_, p_92088_9_, p_92088_11_, p_92088_13_);
	}

	@Override
	public Scoreboard getScoreboard() {
		return world.getScoreboard();
	}

	@Override
	public void firePropagatingBlockUpdate(int x, int y, int z, Block unused) {
		world.func_147453_f(x, y, z, unused);
	}

	@Override
	public float difficultyEnhancedRandom(double locX, double locY, double locZ) {
		return world.func_147462_b(locX, locY, locZ);
	}

	@Override
	public float difficultyEnhancedRandom(int x, int y, int z) {
		return world.func_147473_B(x, y, z);
	}

	@Override
	public void fireStaticEntityChanged() {
		world.func_147450_X();
	}

	@Override
	public void addTileEntity(TileEntity entity) {
		world.addTileEntity(entity);
	}

	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side) {
		return world.isSideSolid(x, y, z, side);
	}

	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
		return world.isSideSolid(x, y, z, side, _default);
	}

	@Override
	public ImmutableSetMultimap<ChunkCoordIntPair, Ticket> getPersistentChunks() {
		return world.getPersistentChunks();
	}

	@Override
	public int getBlockLightOpacity(int x, int y, int z) {
		return world.getBlockLightOpacity(x, y, z);
	}

	@Override
	public int countEntities(EnumCreatureType type, boolean forSpawnCount) {
		return world.countEntities(type, forSpawnCount);
	}
}
