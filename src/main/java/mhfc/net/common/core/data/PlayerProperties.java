package mhfc.net.common.core.data;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.directors.properties.CreateNewProperties;
import mhfc.net.common.core.directors.properties.HandlePropertiesV1;
import mhfc.net.common.world.exploration.ExplorationProperties;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public final class PlayerProperties implements IExtendedEntityProperties {

	private static final int SaveFormatVersion = 1;
	private static final String VERSION_KEY = "version";

	private int saveVersion;
	private ExplorationProperties exploration;

	public PlayerProperties() {
		exploration = new ExplorationProperties();
		saveVersion = 0;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		HandlePropertiesV1 saveDirector = new HandlePropertiesV1(this);
		saveDirector.save(compound);
		compound.setInteger(VERSION_KEY, SaveFormatVersion);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		int saveVersion = compound.getInteger(VERSION_KEY);

		if (saveVersion == SaveFormatVersion) {
			HandlePropertiesV1 loadDirector = new HandlePropertiesV1(this);
			loadDirector.load(compound);
		} else if (saveVersion == 0) {
			MHFCMain.logger().debug("No mhfc properties found for player, creating new ones");
			CreateNewProperties creator = new CreateNewProperties(this);
			creator.construct();
		} else if (saveVersion < SaveFormatVersion && saveVersion > 0) {
			MHFCMain.logger().debug("Old mhfc properties found, converting");
		} else {
			MHFCMain.logger().error("Read player data of unknown version. Falling back to default data");
			CreateNewProperties creator = new CreateNewProperties(this);
			creator.construct();
		}
		saveVersion = SaveFormatVersion;
	}

	@Override
	public void init(Entity entity, World world) {
		exploration.init(entity, world);
	}

	public ExplorationProperties getExploration() {
		return exploration;
	}

	public void setExploration(ExplorationProperties exploration) {
		this.exploration = exploration;
	}

	public void cloneProperties(PlayerProperties originalProperties) {
		this.exploration.cloneProperties(originalProperties.exploration);
		this.saveVersion = originalProperties.saveVersion;
	}

}
