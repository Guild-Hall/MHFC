package mhfc.net.common.world.controller;

import mhfc.net.common.world.area.IAreaType;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * A proxy that may lead to a fake or a real world. Used to control the world
 * population by a {@link IAreaType}. A world proxy could have its (0, 0) offset
 * compared to the underlying world, or have no world at all.<br>
 * Apart from that the proxy aims to give you the same control as a real
 * {@link World} (hence it implements {@link IBlockAccess}) aswell.<br>
 *
 * @author WorldSEnder
 *
 */
public interface IWorldProxy extends IBlockAccess {

	void setBlockAt(int x, int y, int z, Block block);

	void setTileEntityAt(int x, int y, int z, TileEntity tile);

}
