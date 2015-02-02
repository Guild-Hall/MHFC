package mhfc.net.common.eventhandler;

import mhfc.net.MHFCMain;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class MHFCTickHandler{
	
	private static MHFCMain x;
	
	
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event){
		if(event.phase == TickEvent.Phase.END) x.onRenderTick();
	}
	
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event){
		if(event.phase == TickEvent.Phase.END) x.onClientTick();
		
	}
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event){
		if(event.phase == TickEvent.Phase.END)
			try {
				x.onServerTick();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent play)
	{
	}
	
	
	
	
}
