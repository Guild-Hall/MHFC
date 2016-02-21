package mhfc.net.common.world.exploration;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.world.area.AreaRegistry;
import mhfc.net.common.world.area.IAreaType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class ExplorationProperties implements IExtendedEntityProperties {

	public static final String KEY_MANAGER = "manager";
	public static final String KEY_AREA_TYPE = "areaType";

	private IExplorationManager manager;
	private IAreaType areaType;

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		String managerName = MHFCExplorationRegistry.getExplorationManagerName(getManager());
		if (managerName == null) {
			MHFCMain.logger.warn(
					"The exploration manager {} did not have a public name, this will default on load",
					getManager());
			managerName = "";
		}
		compound.setString(KEY_MANAGER, managerName);
		String areaTypeName = AreaRegistry.instance.getName(getAreaType());
		if (areaTypeName == null) {
			MHFCMain.logger.warn(
					"The area type {} did not have a public name, this will default on load",
					getAreaType());
			areaTypeName = "";
		}
		compound.setString(KEY_AREA_TYPE, areaTypeName);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		String managerName = compound.getString(KEY_MANAGER);
		String areaTypeName = compound.getString(KEY_AREA_TYPE);
		setManager(MHFCExplorationRegistry.getExplorationManagerByName(managerName));
		setAreaType(AreaRegistry.instance.getType(areaTypeName));
	}

	@Override
	public void init(Entity entity, World world) {}

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

}
