package mhfc.net.common.world.controller;

import mhfc.net.common.world.area.AreaConfiguration;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class DefaultAreaManager extends AreaManagerAdapter {
	private IRectanglePlacer rectangles;

	public DefaultAreaManager(World world) {
		super(world);
		this.rectangles = new SimpleRectanglePlacer();
	}

	public World getWorld() {
		return this.world;
	}

	@Override
	protected CornerPosition fitNewArea(AreaConfiguration config) {
		return rectangles.addRectangle(config.sizeX, config.sizeZ);
	}

	@Override
	public void saveTo(NBTTagCompound nbtTag) {
		super.saveTo(nbtTag);
		NBTTagCompound placerTag = new NBTTagCompound();
		this.rectangles.saveTo(placerTag);
		nbtTag.setTag("placer", placerTag);
	}

	@Override
	public void readFrom(NBTTagCompound nbtTag) {
		super.saveTo(nbtTag);
		NBTTagCompound placerTag = nbtTag.getCompoundTag("placer");
		this.rectangles.readFrom(placerTag);
	}

}
