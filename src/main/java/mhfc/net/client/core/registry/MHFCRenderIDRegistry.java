package mhfc.net.client.core.registry;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class MHFCRenderIDRegistry {
	public static int armorstandid;
	public static int armorstandbaseid;
	public static int hunterbenchid;
	public static int stuntrapid;

	public static void init() {
		armorstandid = getNextRenderID();
		armorstandbaseid = getNextRenderID();
		hunterbenchid = getNextRenderID();
		stuntrapid = getNextRenderID();
	}

	private static int getNextRenderID() {
		return RenderingRegistry.getNextAvailableRenderId();
	}
}
