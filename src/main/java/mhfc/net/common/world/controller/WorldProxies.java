package mhfc.net.common.world.controller;

import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Utility class for {@link IWorldProxy}. Offers a variety of proxies.
 * 
 * @author WorldSEnder
 *
 */
public class WorldProxies {
	/**
	 * This class can be used when you want to swap the referenced Proxy at a
	 * later point in time. Composition is the key.
	 * 
	 * @author WorldSEnder
	 *
	 */
	public static class WorldProxyProxy implements IWorldProxy {
		private IWorldProxy proxy;

		public WorldProxyProxy(IWorldProxy proxy) {
			this.proxy = proxy;
		}

		public void setProxy(IWorldProxy newProxy) {
			this.proxy = newProxy;
		}

		public IWorldProxy getProxy() {
			return this.proxy;
		}

		@Override
		public Block getBlock(int x, int y, int z) {
			return getProxy().getBlock(x, y, z);
		}

		@Override
		public TileEntity getTileEntity(int x, int y, int z) {
			return getProxy().getTileEntity(x, y, z);
		}

		@Override
		public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean extendedLevelsInChunkCache() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setBlockAt(int x, int y, int z, Block block) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setTileEntityAt(int x, int y, int z, TileEntity tile) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * A {@link RecordingProxy} is a proxy that first is potentially infinitely
	 * large. It offers read and write from any block position. One can spawn
	 * entities anywhere, etc.<br>
	 * The main feature is that it can be applied to another proxy (possibly a
	 * world) given an offset. This records all changes made to the
	 * {@link RecordingProxy} up to this point in the other proxy.<br>
	 * In combination with a
	 * 
	 * @author WorldSEnder
	 *
	 */
	public static class RecordingProxy implements IWorldProxy {

		public IWorldProxy applyTo(IWorldProxy other, int x, int y) {
			return null;
		}

		@Override
		public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean extendedLevelsInChunkCache() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setBlockAt(int x, int y, int z, Block block) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setTileEntityAt(int x, int y, int z, TileEntity tile) {
			// TODO Auto-generated method stub

		}
	}

	public static class OffsetProxy implements IWorldProxy {
		private int chunksX;
		private int chunksZ;
		private IWorldProxy actual;

		public OffsetProxy(IWorldProxy proxy, int offsetChunksX, int offsetChunksZ) {
			this.actual = proxy;
			this.chunksX = offsetChunksX;
			this.chunksZ = offsetChunksZ;
		}

		@Override
		public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean extendedLevelsInChunkCache() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setBlockAt(int x, int y, int z, Block block) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setTileEntityAt(int x, int y, int z, TileEntity tile) {
			// TODO Auto-generated method stub

		}
	}

	public static class WorldProxy implements IWorldProxy {
		private World proxied;

		public WorldProxy(World proxied) {
			this.proxied = Objects.requireNonNull(proxied);
		}

		@Override
		public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public int isBlockProvidingPowerTo(int p_72879_1_, int p_72879_2_, int p_72879_3_, int p_72879_4_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLightBrightnessForSkyBlocks(int p_72802_1_, int p_72802_2_, int p_72802_3_, int p_72802_4_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getBlockMetadata(int p_72805_1_, int p_72805_2_, int p_72805_3_) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public BiomeGenBase getBiomeGenForCoords(int p_72807_1_, int p_72807_2_) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean extendedLevelsInChunkCache() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setTileEntityAt(int x, int y, int z, TileEntity tile) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setBlockAt(int x, int y, int z, Block block) {
			// TODO Auto-generated method stub

		}
	}
}
