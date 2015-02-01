package mhfc.net.client.core.registry;

import mhfc.net.MHFCMain;
import mhfc.net.common.sound.MHFCSound;
import net.minecraftforge.common.MinecraftForge;

public class MHFCSoundRegistry {
	static {
		MHFCMain.checkPreInitialized();
	}

	public static void init() {
		MinecraftForge.EVENT_BUS.register(MHFCSound.instance);
	}
}
