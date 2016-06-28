package mhfc.net.client.gui.hud;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class RenderEventListener {

	@SubscribeEvent
	public void eventHandler(RenderGameOverlayEvent.Post event) {
		if (event.type != ElementType.CROSSHAIRS) {
			return;
		}

		WeaponOverlay.render();

		RenderEffects.displayFlashBomb();
	}
}
