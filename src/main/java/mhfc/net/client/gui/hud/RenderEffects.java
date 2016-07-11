package mhfc.net.client.gui.hud;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import mhfc.net.client.util.gui.MHFCGuiUtil;
import mhfc.net.common.core.registry.MHFCPotionRegistry;
import mhfc.net.common.entity.projectile.EntityFlashBomb;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class RenderEffects {
	private static final Minecraft mc = Minecraft.getMinecraft();

	public static final int EXPLOSION_DURATION = EntityFlashBomb.EXPLOSION_TICKS;
	public static final int PHASE_IN_DURATION = 5;

	public static void displayFlashBomb() {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		PotionEffect flashPotion = player.getActivePotionEffect(MHFCPotionRegistry.getRegistry().flashed);
		if (flashPotion == null) {
			return;
		}
		int baseStrength = flashPotion.getAmplifier();
		int remaining = flashPotion.getDuration();

		float alpha = getFlashBombAlpha(baseStrength, EXPLOSION_DURATION - remaining);

		glColor4f(1, 1, 1, alpha);
		int screenWidth = MHFCGuiUtil.minecraftWidth(mc), screenHeight = MHFCGuiUtil.minecraftHeight(mc);
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDisable(GL_DEPTH_TEST);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		MHFCGuiUtil.drawTexturedRectangle(0, 0, screenWidth, screenHeight, 0, 0, 0, 0);

		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);
	}

	private static float getFlashBombAlpha(int strength, int duration) {
		if (duration < 0 || duration > EXPLOSION_DURATION) {
			return 0;
		}
		if (duration < PHASE_IN_DURATION) {
			return (duration + 1) / (float) PHASE_IN_DURATION;
		}
		return (float) (EXPLOSION_DURATION - duration) / (EXPLOSION_DURATION - PHASE_IN_DURATION);
	}
}
