package mhfc.net.client.render.projectile;

import static org.lwjgl.opengl.GL11.GL_QUADS;

import org.lwjgl.opengl.GL11;

import mhfc.net.common.entity.projectile.EntityBeam;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderBeam extends Render<EntityBeam> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(
			MHFCReference.main_modid,
			"textures/projectile/beam.png");
	private static final double beamTextureWidth = 256;
	private static final double beamTextureHeight = 32;
	private static final double beamStartingEntry = 1.3;
	private static final double beamAoE = 1;

	public RenderBeam(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(EntityBeam entity, double x, double y, double z, float yaw, float delta) {

		boolean sightClear = entity.beamCaster instanceof EntityPlayer
				&& Minecraft.getMinecraft().thePlayer == entity.beamCaster
				&& Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
		double length = Math.sqrt(
				Math.pow(entity.collidePosX - entity.posX, 2) + Math.pow(entity.collidePosY - entity.posY, 2)
						+ Math.pow(entity.collidePosZ - entity.posZ, 2));
		int frame = MathHelper.floor_double((entity.appear - 1 + delta) * 2);
		if (frame < 0) {
			frame = 6;
		}
		setupGL();
		GlStateManager.translate(x, y, z);
		bindEntityTexture(entity);

		GlStateManager.depthMask(false);
		renderStart(sightClear, frame);
		renderBeam(sightClear, length, 180 / Math.PI * entity.getYaw(), 180 / Math.PI * entity.getPitch(), frame);
		GlStateManager.translate(
				entity.collidePosX - entity.posX,
				entity.collidePosY - entity.posY,
				entity.collidePosZ - entity.posZ);
		renderEnd(frame, entity.blockSide);
		GlStateManager.depthMask(true);

		GlStateManager.translate(
				entity.posX - entity.collidePosX,
				entity.posY - entity.collidePosY,
				entity.posZ - entity.collidePosZ);

		GlStateManager.colorMask(false, false, false, true);
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0) {
			renderStart(sightClear, frame);
		}
		renderBeam(sightClear, length, 180 / Math.PI * entity.getYaw(), 180 / Math.PI * entity.getPitch(), frame);
		GlStateManager.translate(
				entity.collidePosX - entity.posX,
				entity.collidePosY - entity.posY,
				entity.collidePosZ - entity.posZ);
		renderEnd(frame, -1);
		GlStateManager.colorMask(true, true, true, true);

		revertGL();
	}

	private void drawQuad(
			double startPos,
			double endPos1,
			double endPos2,
			double minimumU,
			double minimumV,
			double maximumU,
			double maximumV) {
		VertexBuffer t = Tessellator.getInstance().getBuffer();
		t.begin(GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		t.pos(-startPos, -endPos1, 0).tex(minimumU, minimumV).endVertex();
		t.pos(-startPos, endPos2, 0).tex(minimumU, maximumV).endVertex();
		t.pos(startPos, endPos2, 0).tex(maximumU, maximumV).endVertex();
		t.pos(startPos, -endPos1, 0).tex(maximumU, minimumV).endVertex();
		Tessellator.getInstance().draw();
	}

	private void drawBeamCap(int frame, int brightness) {
		double minimumU = 0 + 16D / beamTextureWidth * frame;
		double minimumV = 0;
		double maximumU = minimumU + 16D / beamTextureWidth;
		double maximumV = minimumV + 16D / beamTextureHeight;

		drawQuad(beamStartingEntry, beamStartingEntry, beamStartingEntry, minimumU, minimumV, maximumU, maximumV);
	}

	private void drawBeamSide(int frame, int brightness, double length) {
		double minimumU = 0;
		double minimumV = 16 / beamTextureHeight + 1 / beamTextureHeight * frame;
		double maximumU = minimumU + 20 / beamTextureWidth;
		double maximumV = minimumV + 1 / beamTextureHeight;

		drawQuad(beamAoE, 0, length, minimumU, minimumV, maximumU, maximumV);
	}

	private void renderStart(boolean sightClear, int frame) {
		if (sightClear) {
			return;
		}
		GL11.glRotatef(-renderManager.playerViewY, 0, 1, 0);
		GL11.glRotatef(renderManager.playerViewX, 1, 0, 0);

		drawBeamCap(frame, 240);

		GL11.glRotatef(renderManager.playerViewX, -1, 0, 0);
		GL11.glRotatef(-renderManager.playerViewY, 0, -1, 0);
	}

	private void renderEnd(int frame, int side) {
		GL11.glRotatef(-renderManager.playerViewY, 0, 1, 0);
		GL11.glRotatef(renderManager.playerViewX, 1, 0, 0);

		drawBeamCap(frame, -450);

		GL11.glRotatef(renderManager.playerViewX, -1, 0, 0);
		GL11.glRotatef(-renderManager.playerViewY, 0, -1, 0);

		if (side == -1) {
			return;
		}
		if (side == 5) {
			GL11.glRotatef(270, 0, 1, 0);
			GL11.glTranslatef(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GL11.glTranslatef(0, 0, 0.01f);
			GL11.glRotatef(-270, 0, 1, 0);
		} else if (side == 4) {
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glTranslatef(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GL11.glTranslatef(0, 0, 0.01f);
			GL11.glRotatef(-90, 0, 1, 0);
		} else if (side == 3) {
			GL11.glRotatef(180, 0, 1, 0);
			GL11.glTranslatef(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GL11.glTranslatef(0, 0, 0.01f);
			GL11.glRotatef(-180, 0, 1, 0);
		} else if (side == 2) {
			GL11.glTranslatef(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GL11.glTranslatef(0, 0, 0.01f);
		} else if (side == 0) {
			GL11.glRotatef(-90, 1, 0, 0);
			GL11.glTranslatef(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GL11.glTranslatef(0, 0, 0.01f);
			GL11.glRotatef(90, 1, 0, 0);
		} else if (side == 1) {
			GL11.glRotatef(90, 1, 0, 0);
			GL11.glTranslatef(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GL11.glTranslatef(0, 0, 0.01f);
			GL11.glRotatef(-90, 1, 0, 0);
		}
	}

	private void renderBeam(boolean sightClear, double length, double yaw, double pitch, int frame) {
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glRotatef((float) yaw, 1, 0, 0);
		GL11.glRotatef((float) pitch, 0, 0, 1);

		if (sightClear) {
			GL11.glRotatef(90, 0, 1, 0);
		} else {
			GL11.glRotatef(renderManager.playerViewX, 0, 1, 0);
		}
		drawBeamSide(frame, 80, length);
		if (sightClear) {
			GL11.glRotatef(-90, 0, 1, 0);
		} else {
			GL11.glRotatef(-renderManager.playerViewX, 0, 1, 0);
		}

		if (!sightClear) {
			GL11.glRotatef(-renderManager.playerViewX, 0, 1, 0);
			GL11.glRotatef(180, 0, 1, 0);
			drawBeamSide(frame, 240, length);
			GL11.glRotatef(-180, 0, 1, 0);
			GL11.glRotatef(renderManager.playerViewX, 0, 1, 0);
		}

		GL11.glRotatef((float) -pitch, 0, 0, 1);
		GL11.glRotatef((float) -yaw, 1, 0, 0);
		GL11.glRotatef(90, 0, 0, 1);
	}

	private void setupGL() {
		GL11.glPushMatrix();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
	}

	private void revertGL() {
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBeam entity) {
		return RenderBeam.TEXTURE;
	}

}
