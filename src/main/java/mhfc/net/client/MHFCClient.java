package mhfc.net.client;

import mhfc.net.ProxyBase;
import mhfc.net.common.core.MHFCReg;
import mhfc.net.common.sound.MHFCSound;
import mhfc.net.common.util.MHFCCapes;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.relauncher.Side;

public class MHFCClient extends ProxyBase {
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
		MinecraftForge.EVENT_BUS.register(MHFCSound.instance);
	}

	@Override
	public Side getSide() {
		return Side.CLIENT;
	}
}
