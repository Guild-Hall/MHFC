package mhfc.net.common.core.capability;

import mhfc.net.common.core.data.PlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityPlayerHunterProvider implements ICapabilityProvider, ICapabilitySerializable<NBTBase> {
	private PlayerProperties playerProperties;

	public CapabilityPlayerHunterProvider(EntityPlayer player) {
		this.playerProperties = CapabilityPlayerHunter.HUNTER_CAPABILITY.getDefaultInstance();
		playerProperties.setPlayer(player);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		CapabilityPlayerHunter.HUNTER_CAPABILITY.readNBT(playerProperties, null, nbt);
	}

	@Override
	public NBTBase serializeNBT() {
		return CapabilityPlayerHunter.HUNTER_CAPABILITY.writeNBT(playerProperties, null);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityPlayerHunter.HUNTER_CAPABILITY) {
			return true;
		}
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityPlayerHunter.HUNTER_CAPABILITY) {
			return CapabilityPlayerHunter.HUNTER_CAPABILITY.cast(playerProperties);
		}
		return null;
	}
}
