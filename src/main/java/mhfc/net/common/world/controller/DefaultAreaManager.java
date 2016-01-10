package mhfc.net.common.world.controller;

import java.util.Objects;

import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.proxies.DirectWorldProxy;
import mhfc.net.common.world.proxies.IWorldProxy;
import mhfc.net.common.world.proxies.OffsetProxy;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class DefaultAreaManager extends AreaManagerAdapter {
	private World world;
	private IRectanglePlacer rectangles;

	public DefaultAreaManager(World world) {
		this.world = Objects.requireNonNull(world);
		this.rectangles = new SimpleRectanglePlacer();
	}

	public World getWorld() {
		return this.world;
	}

	@Override
	protected IWorldProxy fitNewArea(AreaConfiguration config) {
		CornerPosition offset = rectangles.addRectangle(config.sizeX, config.sizeZ);
		return new OffsetProxy(new DirectWorldProxy(world), offset.posX, offset.posY);
	}

	@Override
	public void saveTo(NBTTagCompound nbtTag) {
		NBTTagCompound placerTag = new NBTTagCompound();
		this.rectangles.saveTo(placerTag);
		nbtTag.setTag("placer", placerTag);
	}

	@Override
	public void readFrom(NBTTagCompound nbtTag) {
		NBTTagCompound placerTag = nbtTag.getCompoundTag("placer");
		this.rectangles.readFrom(placerTag);
	}

}
