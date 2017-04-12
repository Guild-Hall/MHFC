package mhfc.net.common.world.exploration;

import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import mhfc.net.MHFCMain;
import mhfc.net.common.core.registry.MHFCExplorationRegistry;
import mhfc.net.common.quests.properties.NBTType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public final class ExplorationProperties {

	public static final String KEY_MANAGER = "manager";
	public static final String KEY_AREA_TYPE = "targetAreaType";

	private IExplorationManager manager;
	private EntityPlayer player;

	public NBTBase saveNBTData(NBTTagCompound compound) {
		IExplorationManager currentManager = getManager();
		ResourceLocation managerId = MHFCExplorationRegistry.getExplorationManagerName(currentManager);
		String managerName = managerId == null ? StringUtils.EMPTY : managerId.toString();
		compound.setString(KEY_MANAGER, managerName);

		return compound;
	}

	public void loadNBTData(NBTBase tag) {
		NBTTagCompound compound = NBTType.TAG_COMPOUND.assureTagType(tag);
		ResourceLocation managerName = new ResourceLocation(compound.getString(KEY_MANAGER));
		if (player instanceof EntityPlayerMP) {
			Function<EntityPlayerMP, ? extends IExplorationManager> factory = MHFCExplorationRegistry
					.getExplorationManagerByName(managerName);
			if (factory == null) {
				MHFCMain.logger().debug("Defaulted exploration manager for key {}", managerName);
				factory = OverworldManager::new;
			}
			IExplorationManager manager = factory.apply((EntityPlayerMP) player);
			setManager(manager);
		}
	}

	public IExplorationManager getManager() {
		return manager;
	}

	private void setManager(IExplorationManager manager) {
		this.manager = manager;
	}

	public IExplorationManager replaceManager(IExplorationManager manager) {
		Objects.requireNonNull(manager);
		IExplorationManager current = this.manager;
		MHFCMain.logger().debug("Moving player from exploration manager {} to {}", current, manager);
		if (current != manager) {
			if (current != null) {
				current.onPlayerRemove();
			}
			setManager(manager);
			if (manager != null) {
				manager.onPlayerAdded();
			}
		}
		return this.manager;
	}

	public void cloneFrom(ExplorationProperties originalProperties) {
		this.manager = originalProperties.manager;
	}

	public void setPlayer(EntityPlayer player) {
		this.player = player;
	}

}
