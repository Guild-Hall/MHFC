package mhfc.net.client.gui.hud;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class RenderEventListener {

	@SubscribeEvent
	public void eventHandler(RenderGameOverlayEvent.Post event) {
		if (event.type != ElementType.CROSSHAIRS) {
			return;
		}
		GL11.glDisable(GL11.GL_LIGHTING);

		WeaponOverlay.instance.render();

		GL11.glEnable(GL11.GL_LIGHTING);
	}
}
