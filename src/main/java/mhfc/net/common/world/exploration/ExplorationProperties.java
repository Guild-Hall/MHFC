package mhfc.net.common.world.exploration;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.properties.NBTType;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public final class ExplorationProperties {

	@CapabilityInject(ExplorationProperties.class)
	public static final Capability<ExplorationProperties> EXPLORATION_CAPABILITY = null;

	public static final String KEY_MANAGER = "manager";
	public static final String KEY_AREA_TYPE = "areaType";

	private IExplorationManager manager = OverworldManager.instance;
	private IAreaType areaType = null;

	public NBTBase saveNBTData(NBTTagCompound compound) {
		String managerName = MHFCExplorationRegistry.getExplorationManagerName(getManager());
		if (managerName == null) {
			MHFCMain.logger().warn(
					"The exploration manager {} did not have a public name, this will default on load",
					getManager());
			managerName = "";
		}
		compound.setString(KEY_MANAGER, managerName);
		String areaTypeName = AreaRegistry.instance.getName(getAreaType());
		if (areaTypeName == null) {
			if (manager != OverworldManager.instance) {
				MHFCMain.logger()
						.warn("The area type {} did not have a public name, this will default on load", getAreaType());
			}
			areaTypeName = "";
		}
		compound.setString(KEY_AREA_TYPE, areaTypeName);
		return compound;
	}

	public void loadNBTData(NBTBase tag) {
		NBTTagCompound compound = NBTType.TAG_COMPOUND.assureTagType(tag);
		String managerName = compound.getString(KEY_MANAGER);
		String areaTypeName = compound.getString(KEY_AREA_TYPE);
		IExplorationManager manager = MHFCExplorationRegistry.getExplorationManagerByName(managerName);
		if (manager == null) {
			MHFCMain.logger().debug("Defaulted exploration manager for key {}", managerName);
			manager = OverworldManager.instance;
		}
		setManager(manager);
		setAreaType(AreaRegistry.instance.getType(areaTypeName));
	}

	public IAreaType getAreaType() {
		return areaType;
	}

	public void setAreaType(IAreaType areaType) {
		this.areaType = areaType;
	}

	public IExplorationManager getManager() {
		return manager;
	}

	public void setManager(IExplorationManager manager) {
		this.manager = manager;
	}

	public void cloneProperties(ExplorationProperties originalProperties) {
		this.manager = originalProperties.manager;
		this.areaType = originalProperties.areaType;
	}

}
