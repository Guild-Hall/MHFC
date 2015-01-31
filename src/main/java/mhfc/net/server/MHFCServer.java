package mhfc.net.server;

import mhfc.net.ProxyBase;
import cpw.mods.fml.relauncher.Side;

public class MHFCServer extends ProxyBase {

	@Override
	public void regStuff() {}
	@Override
	public void regTick() {}
	@Override
	public void regSounds() {}
	@Override
	public void regCapes() {}

	@Override
	public Side getSide() {
		return Side.SERVER;
	}
}
