package mhfc.net;

import cpw.mods.fml.relauncher.Side;

public abstract class ProxyBase {
	public abstract void regStuff();
	public abstract void regTick();
	public abstract void regSounds();
	public abstract void regCapes();

	public abstract Side getSide();
}
