package mhfc.net.common.world.controller;

import mhfc.net.common.world.area.IAreaType;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/**
 * A proxy that may lead to a fake or a real world. Used to control the world
 * population by a {@link IAreaType}. A world proxy could have its (0, 0) offset
 * compared to the underlying world, or have no world at all.<br>
 * The proxy offers write-only access to the world so you shouldn't rely on
 * already spawned areas.
 *
 * @author WorldSEnder
 *
 */
public interface IWorldProxy {
	void setBlockAt(int x, int y, int z, Block block);

	void setTileEntityAt(int x, int y, int z, TileEntity tile);
}
