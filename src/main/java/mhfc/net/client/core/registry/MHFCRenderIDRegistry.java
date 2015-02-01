package mhfc.net.client.core.registry;

import mhfc.net.MHFCMain;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class MHFCRenderIDRegistry {
	public static final int armorstandid;
	public static final int armorstandbaseid;
	public static final int hunterbenchid;
	public static final int stuntrapid;

	static {
		MHFCMain.checkPreInitialized();
		armorstandid = getNextRenderID();
		armorstandbaseid = getNextRenderID();
		hunterbenchid = getNextRenderID();
		stuntrapid = getNextRenderID();
	}

	public static void init() {}

	private static int getNextRenderID() {
		return RenderingRegistry.getNextAvailableRenderId();
	}
}
