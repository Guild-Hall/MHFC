package mhfc.net.common.core.registry;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class MHFCRegRenderID {
	
	public static int armorstandid;
	public static int armorstandbaseid;
	public static int hunterbenchid;
	public static int stuntrapid;
	private static RenderingRegistry render;
	
	
	public static void renderID() {
		getThemRenderID(armorstandid);
		getThemRenderID(armorstandbaseid);
		getThemRenderID(hunterbenchid);
		getThemRenderID(stuntrapid);
		
	}
	
	private static void getThemRenderID(int type){
		type = render.getNextAvailableRenderId();
	}

}
