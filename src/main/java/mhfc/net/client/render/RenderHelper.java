package mhfc.net.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;

public class RenderHelper {

	public static void enableFXLighting() {
		GL11.glPushMatrix();
		GL11.glDisable(2896);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
	}

	public static void disableFXLighting() {
		GL11.glEnable(2896);
		GL11.glPopMatrix();
	}

	public static void enableAlphaBlend() {
		GL11.glPushAttrib(8192);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
	}

	public static void enableAlphaBlendAdditive() {
		GL11.glPushAttrib(8192);
		GL11.glEnable(3042);
		GL11.glBlendFunc(1, 1);
	}



	public static void enableBlendMode(RenderType renderType) {
		GL11.glPushAttrib(8192);
		if (renderType != RenderType.SOLID) {
			GL11.glEnable(3042);
		}
		if (renderType == RenderType.ALPHA) {
			GL11.glBlendFunc(770, 771);
		} else if (renderType == RenderType.ADDITIVE) {
			GL11.glBlendFunc(770, 1);
		}
		if (renderType != RenderType.ALPHA_SHADED)
			enableFXLighting();
	}

	public static void disableBlendMode(RenderType renderType) {
		if (renderType != RenderType.ALPHA_SHADED)
			disableFXLighting();
		if (renderType != RenderType.SOLID) {
			GL11.glDisable(3042);
		}
		GL11.glPopAttrib();
	}

	public static enum RenderType {
		ALPHA,
		ADDITIVE,
		SOLID,
		ALPHA_SHADED,
		NO_Z_TEST;

		private RenderType() {}
	}


}
