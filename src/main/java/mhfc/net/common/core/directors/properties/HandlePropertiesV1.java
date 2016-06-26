package mhfc.net.common.core.directors.properties;

import mhfc.net.common.core.data.PlayerProperties;
import net.minecraft.nbt.NBTTagCompound;

public class HandlePropertiesV1 {

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
		NBTTagCompound explorationTag = new NBTTagCompound();
		properties.getExploration().saveNBTData(explorationTag);
		nbt.setTag(KEY_EXPLORATION, explorationTag);
	}

}
