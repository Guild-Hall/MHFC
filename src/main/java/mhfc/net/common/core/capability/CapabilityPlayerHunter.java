package mhfc.net.common.core.capability;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.PlayerProperties;
import mhfc.net.common.quests.properties.NBTType;
import mhfc.net.common.world.exploration.ExplorationProperties;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityPlayerHunter {
	private static final int SaveFormatVersion = 1;
	private static final String VERSION_KEY = "version";

	@CapabilityInject(PlayerProperties.class)
	public static final Capability<PlayerProperties> HUNTER_CAPABILITY = null;

	public static void registerCapabilities(CapabilityManager manager) {
		manager.register(PlayerProperties.class, new Capability.IStorage<PlayerProperties>() {
			// Not forced to implement a SaveHandler, it's just convenient
			@Override
			public void readNBT(
					Capability<PlayerProperties> capability,
					PlayerProperties instance,
					EnumFacing side,
					NBTBase nbt) {
				NBTTagCompound compound = NBTType.TAG_COMPOUND.assureTagType(nbt);

				int saveVersion = compound.getInteger(VERSION_KEY);
				if (saveVersion == SaveFormatVersion) {
					HandlePropertiesV1 loadDirector = new HandlePropertiesV1(instance);
					loadDirector.load(compound);
				} else if (saveVersion < SaveFormatVersion && saveVersion > 0) {
					MHFCMain.logger().debug("Old mhfc properties found, converting");
				} else {
					if (saveVersion == 0) {
						MHFCMain.logger().debug("No mhfc properties found for player, creating new ones");
					} else {
						MHFCMain.logger().error("Read player data of unknown version. Falling back to default data");
					}
					CreateNewProperties creator = new CreateNewProperties(instance);
					creator.construct();
				}
			}

			@Override
			public NBTBase writeNBT(
					Capability<PlayerProperties> capability,
					PlayerProperties instance,
					EnumFacing side) {
				NBTTagCompound compound = new NBTTagCompound();

				HandlePropertiesV1 saveDirector = new HandlePropertiesV1(instance);
				saveDirector.save(compound);
				compound.setInteger(VERSION_KEY, SaveFormatVersion);

				return compound;
			}
		}, PlayerProperties::new);
	}

	private static class CreateNewProperties {
		private PlayerProperties properties;

		public CreateNewProperties(PlayerProperties props) {
			properties = props;
		}

		public void construct() {
			properties.setExploration(new ExplorationProperties());
		}
	}

	private static class HandlePropertiesV1 {
		public static final String KEY_EXPLORATION = "explorationData";
		private PlayerProperties properties;

		public HandlePropertiesV1(PlayerProperties props) {
			this.properties = props;
		}

		public void load(NBTTagCompound nbt) {
			NBTTagCompound exploration = nbt.getCompoundTag(KEY_EXPLORATION);
			properties.getExploration().loadNBTData(exploration);
		}

		public void save(NBTTagCompound nbt) {
			NBTBase explorationTag = properties.getExploration().saveNBTData(new NBTTagCompound());
			nbt.setTag(KEY_EXPLORATION, explorationTag);
		}
	}

	private CapabilityPlayerHunter() {}
}
