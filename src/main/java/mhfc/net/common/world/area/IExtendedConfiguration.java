package mhfc.net.common.world.area;

import mhfc.net.common.util.ISavableToNBT;
import net.minecraft.nbt.NBTTagCompound;

public interface IExtendedConfiguration extends ISavableToNBT {
	public static IExtendedConfiguration EMPTY = new IExtendedConfiguration() {
		@Override
		public void saveTo(NBTTagCompound nbtTag) {}

		@Override
		public void readFrom(NBTTagCompound nbtTag) {}
	};

}
