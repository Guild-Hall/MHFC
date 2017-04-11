package mhfc.net.common.core.capability;

import mhfc.net.common.core.capability.player.PlayerPropertiesStorage;
import mhfc.net.common.core.data.PlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityPlayerHunterProvider implements ICapabilityProvider, ICapabilitySerializable<NBTBase> {

	@CapabilityInject(PlayerProperties.class)
	public static final Capability<PlayerProperties> HUNTER_CAPABILITY = null;

	public static void registerCapabilities(CapabilityManager manager) {
		manager.register(PlayerProperties.class, new PlayerPropertiesStorage(), PlayerProperties::new);
	}

	private PlayerProperties playerProperties;

	public CapabilityPlayerHunterProvider(EntityPlayer player) {
		this.playerProperties = HUNTER_CAPABILITY.getDefaultInstance();
		playerProperties.setPlayer(player);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		HUNTER_CAPABILITY.readNBT(playerProperties, null, nbt);
	}

	@Override
	public NBTBase serializeNBT() {
		return HUNTER_CAPABILITY.writeNBT(playerProperties, null);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == HUNTER_CAPABILITY) {
			return true;
		}
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == HUNTER_CAPABILITY) {
			return HUNTER_CAPABILITY.cast(playerProperties);
		}
		return null;
	}
}
