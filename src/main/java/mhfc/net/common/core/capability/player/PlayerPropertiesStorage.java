package mhfc.net.common.core.capability.player;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.data.PlayerProperties;
import mhfc.net.common.quests.properties.NBTType;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public final class PlayerPropertiesStorage implements Capability.IStorage<PlayerProperties> {
	private static class SaveDataConverter {
		private NBTTagCompound saveData;
		private int currentVersion;

		public SaveDataConverter(NBTBase readSaveData) {
			this.saveData = NBTType.TAG_COMPOUND.assureTagType(readSaveData);
			this.currentVersion = saveData.getInteger(VERSION_KEY);
		}

		public boolean convertOnce() {
			if (currentVersion == 0) {
				initializeNewSaveData();
			}

			/*if(currentVersion == 1) {
				convertFromVersion1();
			} else*/ if (currentVersion == CURRENT_VERSION) {
				; // no op
			} else {
				handleUnknownVersion();
			}

			return currentVersion == CURRENT_VERSION;
		}

		private void handleUnknownVersion() {
			MHFCMain.logger()
					.error("Read player data of unknown version {}. Falling back to default data", currentVersion);

			initializeNewSaveData();
		}

		private void initializeNewSaveData() {
			MHFCMain.logger().debug("No mhfc properties found for player, creating new ones");

			saveData = new NBTTagCompound();
			currentVersion = CURRENT_VERSION;
		}

		public NBTTagCompound getSaveData() {
			return saveData;
		}
	}

	private static final int CURRENT_VERSION = 1;
	private static final String VERSION_KEY = "version";

	public static final String KEY_EXPLORATION = "explorationData";

	// Not forced to implement a SaveHandler, it's just convenient
	@Override
	public void readNBT(
			Capability<PlayerProperties> capability,
			PlayerProperties instance,
			EnumFacing side,
			NBTBase nbt) {
		SaveDataConverter converter = new SaveDataConverter(nbt);
		boolean finished;
		do {
			finished = converter.convertOnce();
		} while (!finished);

		NBTTagCompound saveData = converter.getSaveData();

		load(instance, saveData);
	}

	@Override
	public NBTBase writeNBT(Capability<PlayerProperties> capability, PlayerProperties instance, EnumFacing side) {
		NBTTagCompound compound = new NBTTagCompound();

		save(instance, compound);
		compound.setInteger(VERSION_KEY, CURRENT_VERSION);

		return compound;
	}

	private void load(PlayerProperties instance, NBTTagCompound nbt) {
		NBTTagCompound exploration = nbt.getCompoundTag(KEY_EXPLORATION);
		instance.getExploration().loadNBTData(exploration);
	}

	private void save(PlayerProperties instance, NBTTagCompound nbt) {
		NBTBase explorationTag = instance.getExploration().saveNBTData(new NBTTagCompound());
		nbt.setTag(KEY_EXPLORATION, explorationTag);
	}

}
