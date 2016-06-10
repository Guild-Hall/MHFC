package mhfc.net.common.world.area;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

public class DisplacedView implements IWorldView {

	private int chunkDeltaX, chunkDeltaZ;
	private int addX, addZ;
	private int dimensionX, dimensionZ;
	private World worldObj;

	public DisplacedView(CornerPosition chunkCorner, AreaConfiguration configuration, World world) {
		chunkDeltaX = chunkCorner.posX;
		chunkDeltaZ = chunkCorner.posY;
		dimensionX = configuration.getChunkSizeX();
		dimensionZ = configuration.getChunkSizeZ();
		worldObj = Objects.requireNonNull(world);

		addX = chunkDeltaX << 4;
		addZ = chunkDeltaZ << 4;
	}

	public int getChunkDeltaX() {
		return chunkDeltaX;
	}

	public int getChunkDeltaZ() {
		return chunkDeltaZ;
	}

	public int getAddX() {
		return addX;
	}

	public int getAddZ() {
		return addZ;
	}

	public int getDimensionX() {
		return dimensionX;
	}

	public int getDimensionZ() {
		return dimensionZ;
	}

	private boolean isInBoundary(double x, double y, double z) {
		return x >= 0 && y >= 0 && z >= 0 && x < dimensionX * 16 && z < dimensionZ * 16
				&& y < worldObj.getActualHeight();
	}

	@Override
	public boolean isDaytime() {
		return worldObj.isDaytime();
	}

	@Override
	public boolean isThundering() {
		return worldObj.isThundering();
	}

	@Override
	public boolean isRaining() {
		return worldObj.isRaining();
	}

	@Override
	public void markBlockForUpdate(int x, int y, int z) {
		worldObj.markBlockForUpdate(x + addX, y, z + addZ);
	}

	@Override
	public boolean isAirBlock(int x, int y, int z) {
		if (!isInBoundary(x, y, z))
			return true;
		return worldObj.isAirBlock(x + addX, y, z + addZ);
	}

	@Override
	public boolean blockExists(int x, int y, int z) {
		if (!isInBoundary(x, y, z))
			return false;
		return worldObj.blockExists(x + addX, y, z + addZ);
	}

	@Override
	public Chunk getChunkFromBlockCoords(int x, int z) {
		if (!isInBoundary(x, 0, z))
			return null;
		return worldObj.getChunkFromBlockCoords(x + addX, z + addZ);
	}

	@Override
	public Block getTopBlock(int x, int z) {
		if (!isInBoundary(x, 0, z))
			return Blocks.air;
		return worldObj.getTopBlock(x + addX, z + addZ);
	}

	@Override
	public int getTopSolidOrLiquidBlock(int x, int z) {
		if (!isInBoundary(x, 0, z))
			return -1;
		return worldObj.getTopSolidOrLiquidBlock(x + addX, z + addZ);
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		if (!isInBoundary(x, y, z))
			return Blocks.air;
		return worldObj.getBlock(x + addX, y, z + addZ);
	}

	@Override
	public int getBlockMetadata(int x, int y, int z) {
		if (!isInBoundary(x, y, z))
			return 0;
		return worldObj.getBlockMetadata(x + addX, y, z + addZ);
	}

	@Override
	public boolean setBlockToAir(int x, int y, int z) {
		if (!isInBoundary(x, y, z))
			return false;
		return worldObj.setBlockToAir(x + addX, y, z + addZ);
	}

	@Override
	public boolean setBlock(int x, int y, int z, Block block) {
		if (!isInBoundary(x, y, z))
			return false;
		return worldObj.setBlock(x + addX, y, z + addZ, block);
	}

	@Override
	public boolean setBlock(int x, int y, int z, Block block, int metadata, int flags) {
		if (!isInBoundary(x, y, z))
			return false;
		return worldObj.setBlock(x + addX, y, z + addZ, block, metadata, flags);
	}

	@Override
	public void playSoundEffect(double x, double y, double z, String soundName, float p_72908_8_, float p_72908_9_) {
		if (!isInBoundary(x, y, z))
			return;
		worldObj.playSoundEffect(x + addX, y, z + addZ, soundName, p_72908_8_, p_72908_9_);
	}

	@Override
	public void playSound(
			double x,
			double y,
			double z,
			String soundName,
			float volume,
			float p_72980_9_,
			boolean p_72980_10_) {
		if (!isInBoundary(x, y, z))
			return;
		worldObj.playSound(x + addX, y, z + addZ, soundName, volume, p_72980_9_, p_72980_10_);
	}

	@Override
	public void spawnParticle(
			String particleName,
			double x,
			double y,
			double z,
			double velX,
			double velY,
			double velZ) {
		if (!isInBoundary(x, y, z))
			return;
		worldObj.spawnParticle(particleName, x + addX, y, z + addZ, velX, velY, velZ);
	}

	@Override
	public void onEntityAdded(Entity entity) {
		worldObj.onEntityAdded(entity);
	}

	@Override
	public void onEntityRemoved(Entity entity) {
		worldObj.onEntityRemoved(entity);
	}

	@Override
	public void removeEntity(Entity entity) {
		worldObj.removeEntity(entity);
	}

	@Override
	public void addWorldAccess(IWorldAccess worldAccess) {
		worldObj.addWorldAccess(worldAccess);
	}

	@Override
	public void removeWorldAccess(IWorldAccess worldAccess) {
		worldObj.removeWorldAccess(worldAccess);
	}

	@Override
	public TileEntity getTileEntity(int x, int y, int z) {
		if (!isInBoundary(x, y, z))
			return null;
		return worldObj.getTileEntity(x + addX, y, z + addZ);
	}

	@Override
	public void setTileEntity(int x, int y, int z, TileEntity tileEntity) {
		if (!isInBoundary(x, y, z))
			return;
		worldObj.setTileEntity(x + addX, y, z + addZ, tileEntity);
	}

	@Override
	public void removeTileEntity(int x, int y, int z) {
		if (!isInBoundary(x, y, z))
			return;
		worldObj.removeTileEntity(x + addX, y, z + addZ);
	}

	@Override
	public int countEntities(Class<? extends Entity> entityClass) {
		@SuppressWarnings("unchecked")
		Stream<Entity> entStream = worldObj.loadedEntityList.stream();
		return entStream.filter((e) -> entityClass.isAssignableFrom(e.getClass()))
				.filter((e) -> isInBoundary(e.posX - addX, e.posY, e.posZ - addZ)).collect(Collectors.counting())
				.intValue();
	}

	@Override
	public int getBlockPowerInput(int x, int y, int z) {
		if (!isInBoundary(x, y, z))
			return 0;
		return worldObj.getBlockPowerInput(x + addX, y, z + addZ);
	}

	@Override
	public boolean canMineBlock(EntityPlayer player, int x, int y, int z) {
		if (!isInBoundary(x, y, z))
			return false;
		return worldObj.canMineBlock(player, x + addX, y, z + addZ);
	}

	@Override
	public void addTileEntity(TileEntity entity) {
		worldObj.addTileEntity(entity);
		entity.setWorldObj(worldObj);
		entity.xCoord += addX;
		entity.zCoord += addZ;
	}

	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side) {
		if (!isInBoundary(x, y, z))
			return false;
		return worldObj.isSideSolid(x + addX, y, z + addZ, side);
	}

	@Override
	public boolean isManaged(double relX, double relY, double relZ) {
		return isInBoundary(relX, relY, relZ);
	}

	@Override
	public boolean spawnEntityAt(Entity entity, double x, double y, double z) {
		entity.worldObj = worldObj;
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).setPositionAndUpdate(x + addX, y, z + addZ);
		} else {
			entity.setLocationAndAngles(x + addX, y, z + addZ, 0, 0);
		}
		if (!worldObj.spawnEntityInWorld(entity))
			return false;
		return true;
	}

	@Override
	public World getWorldObject() {
		return worldObj;
	}

	public void moveEntityTo(Entity entity, double posX, double posY, double posZ) {
		posX += addX;
		posZ += addZ;
		AreaTeleportation.moveEntityTo(entity, posX, posY, posZ);
	}

	@Override
	public Vec3 convertToLocal(Vec3 global) {
		return global.addVector(-addX, 0, -addZ);
	}

	@Override
	public Vec3 convertToGlobal(Vec3 local) {
		return local.addVector(addX, 0, addZ);
	}
}
