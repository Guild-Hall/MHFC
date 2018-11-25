package mhfc.net.common.world;

import mhfc.net.MHFCMain;
import mhfc.net.common.quests.world.QuestFlair;
import mhfc.net.common.world.area.AreaConfiguration;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;
import mhfc.net.common.world.area.IExtendedConfiguration;
import mhfc.net.common.world.controller.*;
import mhfc.net.common.world.types.AreaTypePlayfield;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;


public class MHFCWorldData extends WorldSavedData {
	private static enum AreaStatus {
		NEW,
		FULLY_GENERATED;
	}

	public static class AreaInformation {
		public final IAreaType type;
		public final AreaConfiguration config;
		private AreaStatus status;

		public AreaInformation(IAreaType type, AreaConfiguration config) {
			this(type, config, AreaStatus.NEW);
		}

		private AreaInformation(IAreaType type, AreaConfiguration config, AreaStatus status) {
			this.type = Objects.requireNonNull(type);
			this.config = Objects.requireNonNull(config);
			this.status = status;
		}

		private boolean shouldSafe() {
			return status == AreaStatus.FULLY_GENERATED;
		}

		private void setFullyGenerated() {
			this.status = AreaStatus.FULLY_GENERATED;
		}
	}

	// Because MC doesn't hand the world over to the constructor when loading, we do
	private static ThreadLocal<World> WORLD_CONTEXT = new ThreadLocal<>();

	private Collection<AreaInformation> spawnedAreas = new HashSet<>();
	private IRectanglePlacer rectanglePlacer;
	private IAreaManager areaManager;

	public MHFCWorldData(String nbtPropName) {
		this(WORLD_CONTEXT.get(), nbtPropName);
	}

	public MHFCWorldData(World world, String nbtPropName) {
		super(nbtPropName);
		rectanglePlacer = new SimpleRectanglePlacer();
		areaManager = new AreaManager(world, this, getFlairFromWorld(world));
		this.markDirty();
	}

	private static QuestFlair getFlairFromWorld(World world) {
		WorldProvider worldProvider = world.provider;
		if (worldProvider instanceof WorldProviderQuesting) {
			WorldProviderQuesting questingProvider = (WorldProviderQuesting) worldProvider;
			return questingProvider.getQuestFlair();
		}
		return QuestFlair.DAYTIME;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		NBTTagList list = nbtTag.getTagList("spawnedAreas", NBT.TAG_COMPOUND);
		NBTTagCompound placerTag = nbtTag.getCompoundTag("placer");
		this.rectanglePlacer.readFrom(placerTag);
		spawnedAreas.clear();
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound confTag = list.getCompoundTagAt(i);
			String typeS = confTag.getString("type");
			NBTTagCompound dataTag = confTag.getCompoundTag("data");
			IAreaType type = AreaRegistry.instance.getType(typeS);
			if (type == null) {// and warn about invalid area
				MHFCMain.logger().error("Invalid area was found in save data of the hunting world!");
				IExtendedConfiguration config = AreaTypePlayfield.PLAYFIELD_TYPE.configForLoading();
				@SuppressWarnings("unused")
				AreaConfiguration areaConfig = AreaConfiguration.newConfigForLoading(config);
				// TODO maybe clear the world data at this position
				continue;
			}
			IExtendedConfiguration config = type.configForLoading();
			AreaConfiguration areaConfig = AreaConfiguration.newConfigForLoading(config);
			areaConfig.readFrom(dataTag);
			spawnedAreas.add(new AreaInformation(type, areaConfig, AreaStatus.FULLY_GENERATED));
		}
		areaManager.onLoaded();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbtTag) {
		NBTTagList list = new NBTTagList();
		for (AreaInformation conf : spawnedAreas) {
			if (!conf.shouldSafe()) {
				continue;
			}
			String typeS = AreaRegistry.instance.getName(conf.type);
			if (typeS == null) {
				continue;
			}
			NBTTagCompound dataTag = new NBTTagCompound();
			conf.config.saveTo(dataTag);
			NBTTagCompound confTag = new NBTTagCompound();
			confTag.setString("type", typeS);
			confTag.setTag("data", dataTag);
			list.appendTag(confTag);
		}
		NBTTagCompound placerTag = new NBTTagCompound();
		this.rectanglePlacer.saveTo(placerTag);
		nbtTag.setTag("spawnedAreas", list);
		nbtTag.setTag("placer", placerTag);
		return nbtTag;
	}

	public AreaInformation newArea(IAreaType type, AreaConfiguration config) {
		CornerPosition pos = rectanglePlacer.addRectangle(config.getChunkSizeX() + 2, config.getChunkSizeZ() + 2);
		CornerPosition actual = new CornerPosition(pos.posX + 1, pos.posY + 1);
		AreaInformation areaId = new AreaInformation(type, config);
		spawnedAreas.add(areaId);
		this.markDirty();
		config.setPosition(actual);
		return areaId;
	}

	public void onAreaFullyGenerated(AreaInformation areaId) {
		areaId.setFullyGenerated();
	}

	public Collection<AreaInformation> getAllSpawnedAreas() {
		return Collections.unmodifiableCollection(this.spawnedAreas);
	}

	public static IAreaManager retrieveManagerForWorld(World world) {
		//FIXME (1.10): implement as Capability in 1.10.2
		WORLD_CONTEXT.set(world);
		MHFCWorldData data = (MHFCWorldData) world.getPerWorldStorage().getOrLoadData(MHFCWorldData.class, "mhfcareas");
		if (data == null) {
			data = new MHFCWorldData(world, "mhfcareas");
			world.getPerWorldStorage().setData("mhfcareas", data);
		}
		WORLD_CONTEXT.set(null);
		return data.areaManager;
	}

	public void onAreaCanceled(AreaInformation info) {
		spawnedAreas.remove(info);
	}

}
