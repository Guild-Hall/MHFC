package mhfc.net.common.world.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import mhfc.net.common.world.area.ActiveAreaAdapter;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IActiveArea;
import mhfc.net.common.world.area.IArea;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;

public abstract class AreaManagerAdapter implements IAreaManager {
	private static class AreaInformation {
		public final IAreaType type;
		public final IArea controller;

		public AreaInformation(IAreaType type, IArea controller) {
			this.type = Objects.requireNonNull(type);
			this.controller = controller;
		}
	}

	private static class Active extends ActiveAreaAdapter {
		private IArea area;
		private IAreaType type;
		private AreaManagerAdapter ref;

		public Active(IArea area, IAreaType type, AreaManagerAdapter ref) {
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

	protected Map<IAreaType, List<IArea>> nonactiveAreas = new HashMap<>();
	private Collection<AreaInformation> spawnedAreas = new HashSet<>();
	protected final World world;

	public AreaManagerAdapter(World world) {
		this.world = Objects.requireNonNull(world);
	}

	private void dismiss(IActiveArea active) {
		this.nonactiveAreas.get(active.getType()).add(active.getArea());
	}

	@Override
	public Active getUnusedInstance(IAreaType type) {
		List<IArea> list = nonactiveAreas.computeIfAbsent(type, (k) -> new ArrayList<>());
		IArea chosen = null;
		for (Iterator<IArea> it = list.iterator(); it.hasNext();) {
			IArea area = it.next();
			if (area.isUnusable()) {
				continue;
			}
			chosen = area;
			it.remove();
		}
		if (chosen == null) {
			chosen = newArea(type);
		}
		return new Active(chosen, type, this);
	}

	private IArea newArea(IAreaType type) {
		AreaConfiguration config = type.configureNewArea();
		CornerPosition pos = fitNewArea(config);
		// TODO make sure that chunks are cleared?
		IArea controller = type.populate(world, pos, config);
		spawnedAreas.add(new AreaInformation(type, controller));
		return controller;
	}

	protected abstract CornerPosition fitNewArea(AreaConfiguration config);

	@Override
	public void readFrom(NBTTagCompound nbtTag) {
		spawnedAreas.clear();
		nonactiveAreas.clear();
		NBTTagList list = nbtTag.getTagList("spawnedAreas", NBT.TAG_COMPOUND);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound confTag = list.getCompoundTagAt(i);
			String typeS = confTag.getString("type");
			NBTTagCompound dataTag = confTag.getCompoundTag("data");
			IAreaType type = AreaRegistry.instance.getType(typeS);
			if (type == null) {
				continue;
			}
			IArea area = type.provideForLoading(world);
			area.readFrom(dataTag);
			spawnedAreas.add(new AreaInformation(type, area));
			nonactiveAreas.computeIfAbsent(type, (k) -> new ArrayList<>()).add(area);
		}
	}

	@Override
	public void saveTo(NBTTagCompound nbtTag) {
		NBTTagList list = new NBTTagList();
		for (AreaInformation conf : spawnedAreas) {
			NBTTagCompound dataTag = new NBTTagCompound();
			conf.controller.saveTo(dataTag);
			String typeS = AreaRegistry.instance.getName(conf.type);
			if (typeS == null) {
				continue;
			}
			NBTTagCompound confTag = new NBTTagCompound();
			confTag.setString("type", typeS);
			confTag.setTag("data", dataTag);
			list.appendTag(confTag);
		}
		nbtTag.setTag("spawnedAreas", list);
	}
}
