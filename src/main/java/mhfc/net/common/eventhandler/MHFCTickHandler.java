package mhfc.net.common.eventhandler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class MHFCTickHandler {
	public static final MHFCTickHandler instance = new MHFCTickHandler();

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {}

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {}

	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {}

	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent play) {}

}
