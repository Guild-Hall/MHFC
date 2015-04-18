package mhfc.net.common.world.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mhfc.net.common.world.area.ActiveAreaAdapter;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DefaultAreaManager implements IAreaManager {
	private static class Active extends ActiveAreaAdapter {
		private IArea area;
		private IAreaType type;
		private DefaultAreaManager ref;

		public Active(IArea area, IAreaType type, DefaultAreaManager ref) {
			this.area = Objects.requireNonNull(area);
			this.type = Objects.requireNonNull(type);
			this.ref = Objects.requireNonNull(ref);
		}

		@Override
		public IArea getArea() {
			return area;
		}

		@Override
		public IAreaType getType() {
			return type;
		}

		@Override
		protected void onDismiss() {
			ref.dismiss(this);
		}

	}

	private static class Proxy implements IWorldProxy {

		@Override
		public void setBlockAt(int x, int y, int z, Block block) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setTileEntityAt(int x, int y, int z, TileEntity tile) {
			// TODO Auto-generated method stub

		}

		public void applyTo(World world, int chunkOffX, int chunkOffZ) {
			// TODO Auto-generated method stub
		}
	}
	private Map<IAreaType, List<IArea>> spawnedAreas = new HashMap<>();
	private World world;

	public DefaultAreaManager(World world) {
		this.world = Objects.requireNonNull(world);
	}

	private void dismiss(IActiveArea active) {
		this.spawnedAreas.get(active.getType()).add(active.getArea());
	}

	@Override
	public IActiveArea getEmptyInstance(IAreaType type) {
		List<IArea> list = spawnedAreas.get(type);
		if (list == null) {
			IArea newArea = newArea(type);
			spawnedAreas.put(type, Arrays.asList(newArea));
			return new Active(newArea, type, this);
		}
		for (IArea area : list) {
			if (area.isBlocked())
				continue;
			return new Active(area, type, this);
		}
		IArea newArea = newArea(type);
		return new Active(newArea, type, this);
	}

	private IArea newArea(IAreaType type) {
		Proxy proxy = newProxy();
		type.populate(proxy);
		// TODO
		proxy.applyTo(world, 0, 0);
		return null;
	}

	private static Proxy newProxy() {
		return new Proxy();
	}

}
