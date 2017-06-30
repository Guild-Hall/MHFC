package mhfc.net.client.gui.hud;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderEventListener {

	@SubscribeEvent
	public void eventHandler(RenderGameOverlayEvent.Post event) {
		if (event.getType() != ElementType.HOTBAR) {
			return;
		}

		WeaponOverlay.render();

		RenderEffects.displayFlashBomb();
	}
}
