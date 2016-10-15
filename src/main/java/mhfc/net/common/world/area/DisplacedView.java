package mhfc.net.common.world.area;

import java.util.Objects;
import java.util.stream.Stream;

import mhfc.net.common.world.AreaTeleportation;
import mhfc.net.common.world.controller.CornerPosition;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldEventListener;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class DisplacedView implements IWorldView {

	private int chunkDeltaX, chunkDeltaZ;
	private int chunkDimensionX, chunkDimensionZ;
	private int blockDeltaX, blockDeltaZ;
	private World worldObj;

	public DisplacedView(CornerPosition chunkCorner, AreaConfiguration configuration, World world) {
		chunkDeltaX = chunkCorner.posX;
		chunkDeltaZ = chunkCorner.posY;
		chunkDimensionX = configuration.getChunkSizeX();
		chunkDimensionZ = configuration.getChunkSizeZ();
		worldObj = Objects.requireNonNull(world);

		blockDeltaX = chunkDeltaX << 4;
		blockDeltaZ = chunkDeltaZ << 4;
	}

	public int getChunkDeltaX() {
		return chunkDeltaX;
	}

	public int getChunkDeltaZ() {
		return chunkDeltaZ;
	}

	public int getAddX() {
		return blockDeltaX;
	}

	public int getAddZ() {
		return blockDeltaZ;
	}

	public int getDimensionX() {
		return chunkDimensionX;
	}

	public int getDimensionZ() {
		return chunkDimensionZ;
	}

	private boolean isInBoundary(double x, double y, double z) {
		return x >= 0 && y >= 0 && z >= 0 && x < chunkDimensionX * 16 && z < chunkDimensionZ * 16
				&& y < worldObj.getActualHeight();
	}

	private boolean isInBoundary(BlockPos localPos) {
		return isInBoundary(localPos.getX(), localPos.getY(), localPos.getZ());
	}

	private BlockPos localToGlobal(BlockPos localPos) {
		return localPos.add(blockDeltaX, 0, blockDeltaZ);
	}

	private BlockPos globalToLocal(BlockPos globalPos) {
		return globalPos.add(-blockDeltaX, 0, -blockDeltaZ);
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
	public boolean isAirBlock(BlockPos position) {
		if (!isInBoundary(position)) {
			return true;
		}
		return worldObj.isAirBlock(localToGlobal(position));
	}

	@Override
	public boolean blockExists(BlockPos position) {
		if (!isInBoundary(position)) {
			return false;
		}
		return worldObj.isBlockLoaded(position);
	}

	@Override
	public Chunk getChunkFromBlockCoords(BlockPos position) {
		if (!isInBoundary(position)) {
			return null;
		}
		return worldObj.getChunkFromBlockCoords(localToGlobal(position));
	}

	@Override
	public IBlockState getTopBlock(BlockPos position) {
		if (!isInBoundary(position)) {
			return Blocks.AIR.getDefaultState();
		}
		return worldObj.getBlockState(worldObj.getHeight(localToGlobal(position)));
	}

	@Override
	public BlockPos getTopSolidOrLiquidBlock(BlockPos position) {
		if (!isInBoundary(position)) {
			return new BlockPos(position.getX(), -1, position.getZ());
		}
		return globalToLocal(worldObj.getTopSolidOrLiquidBlock(localToGlobal(position)));
	}

	@Override
	public IBlockState getBlockState(BlockPos position) {
		if (!isInBoundary(position)) {
			return Blocks.AIR.getDefaultState();
		}
		return worldObj.getBlockState(localToGlobal(position));
	}

	@Override
	public boolean setBlockToAir(BlockPos position) {
		if (!isInBoundary(position)) {
			return false;
		}
		return worldObj.setBlockToAir(localToGlobal(position));
	}

	@Override
	public boolean setBlockState(BlockPos position, IBlockState state) {
		if (!isInBoundary(position)) {
			return false;
		}
		return worldObj.setBlockState(localToGlobal(position), state);
	}

	@Override
	public boolean setBlockState(BlockPos position, IBlockState state, int flags) {
		if (!isInBoundary(position)) {
			return false;
		}
		return worldObj.setBlockState(localToGlobal(position), state, flags);
	}

	@Override
	public void playSound(
			double x,
			double y,
			double z,
			SoundEvent sound,
			SoundCategory category,
			float volume,
			float pitch,
			boolean honorDistance) {
		if (!isInBoundary(x, y, z)) {
			return;
		}
		worldObj.playSound(x + blockDeltaX, y, z + blockDeltaZ, sound, category, volume, pitch, honorDistance);
	}

	@Override
	public void spawnParticle(
			EnumParticleTypes particle,
			double x,
			double y,
			double z,
			double velX,
			double velY,
			double velZ,
			int... params) {
		if (!isInBoundary(x, y, z)) {
			return;
		}
		worldObj.spawnParticle(particle, x + blockDeltaX, y, z + blockDeltaZ, velX, velY, velZ, params);
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
	public void addWorldAccess(IWorldEventListener worldAccess) {
		worldObj.addEventListener(worldAccess);
	}

	@Override
	public void removeWorldAccess(IWorldEventListener worldAccess) {
		worldObj.removeEventListener(worldAccess);
	}

	@Override
	public TileEntity getTileEntity(BlockPos position) {
		if (!isInBoundary(position)) {
			return null;
		}
		return worldObj.getTileEntity(localToGlobal(position));
	}

	@Override
	public void setTileEntity(BlockPos position, TileEntity tileEntity) {
		if (!isInBoundary(position)) {
			return;
		}
		worldObj.setTileEntity(localToGlobal(position), tileEntity);
	}

	@Override
	public void removeTileEntity(BlockPos position) {
		if (!isInBoundary(position)) {
			return;
		}
		worldObj.removeTileEntity(localToGlobal(position));
	}

	@Override
	public int countEntities(Class<? extends Entity> entityClass) {
		Stream<Entity> entStream = worldObj.loadedEntityList.stream();
		return (int) entStream.filter(e -> entityClass.isAssignableFrom(e.getClass()))
				.filter(e -> isInBoundary(e.posX - blockDeltaX, e.posY, e.posZ - blockDeltaZ)).count();
	}

	@Override
	public int getBlockPowerInput(BlockPos position, EnumFacing face) {
		if (!isInBoundary(position)) {
			return 0;
		}
		return worldObj.getRedstonePower(localToGlobal(position), face);
	}

	@Override
	public boolean canMineBlock(EntityPlayer player, BlockPos position) {
		if (!isInBoundary(position)) {
			return false;
		}
		return worldObj.canMineBlockBody(player, localToGlobal(position));
	}

	@Override
	public void addTileEntity(TileEntity entity) {
		worldObj.addTileEntity(entity);
		entity.setWorldObj(worldObj);
		entity.setPos(localToGlobal(entity.getPos()));
	}

	@Override
	public boolean isSideSolid(BlockPos position, EnumFacing side) {
		if (!isInBoundary(position)) {
			return false;
		}
		return worldObj.isSideSolid(localToGlobal(position), side);
	}

	@Override
	public boolean isManaged(double relX, double relY, double relZ) {
		return isInBoundary(relX, relY, relZ);
	}

	@Override
	public boolean spawnEntityAt(Entity entity, double x, double y, double z) {
		entity.worldObj = worldObj;
		if (entity instanceof EntityLivingBase) {
			((EntityLivingBase) entity).setPositionAndUpdate(x + blockDeltaX, y, z + blockDeltaZ);
		} else {
			entity.setLocationAndAngles(x + blockDeltaX, y, z + blockDeltaZ, 0, 0);
		}
		if (!worldObj.spawnEntityInWorld(entity)) {
			return false;
		}
		return true;
	}

	@Override
	public World getWorldObject() {
		return worldObj;
	}

	@Override
	public void moveEntityTo(Entity entity, double posX, double posY, double posZ) {
		BlockPos position = new BlockPos(posX, posY, posZ);
		AreaTeleportation.moveEntityTo(entity, localToGlobal(position));
	}

	@Override
	public Vec3d convertToLocal(Vec3d global) {
		return global.addVector(-blockDeltaX, 0, -blockDeltaZ);
	}

	@Override
	public Vec3d convertToGlobal(Vec3d local) {
		return local.addVector(blockDeltaX, 0, blockDeltaZ);
	}
}
