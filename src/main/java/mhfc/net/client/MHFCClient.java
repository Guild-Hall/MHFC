package mhfc.net.client;

import mhfc.net.common.MHFCCommon;
import mhfc.net.common.core.MHFCReg;
import mhfc.net.common.sound.MHFCSound;
import mhfc.net.common.util.MHFCCapes;
import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.common.MinecraftForge;

public class MHFCClient extends MHFCCommon {
	@Override
	public void regStuff() {
		MHFCReg.init();
	}
	@Override
	public void regTick() {}

	@Override
	public void regCapes() {
		MHFCCapes.addDevCapes();
	}

	@Override
	public void regSounds() {
		MinecraftForge.EVENT_BUS.register(new MHFCSound());
	}

	@Override
	public ModelBiped getArmorModel(int id) {
		return null;
	}

	@Override
	public boolean isClient() {
		return true;
	}
}
