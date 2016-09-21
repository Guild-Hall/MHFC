package mhfc.net.client.render.projectile;

import static org.lwjgl.opengl.GL11.GL_QUADS;

import org.lwjgl.opengl.GL11;

import mhfc.net.common.entity.projectile.EntityBeam;
import mhfc.net.common.util.Libraries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class RenderBeam extends Render<EntityBeam> {

	private static final ResourceLocation TEXTURE = new ResourceLocation(
			Libraries.main_modid,
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

		EntityLivingBase beamCaster = entity.getCaster();
		boolean sightClear = Minecraft.getMinecraft().thePlayer == beamCaster
				&& Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
		double length = Math.sqrt(
				Math.pow(entity.collidePosX - entity.posX, 2) + Math.pow(entity.collidePosY - entity.posY, 2)
						+ Math.pow(entity.collidePosZ - entity.posZ, 2));
		int frame = MathHelper.floor_double((entity.getDuration() - 1 + delta) * 2);
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
		renderEnd(frame, null);
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
		GlStateManager.rotate(-renderManager.playerViewY, 0, 1, 0);
		GlStateManager.rotate(renderManager.playerViewX, 1, 0, 0);

		drawBeamCap(frame, 240);

		GlStateManager.rotate(-renderManager.playerViewX, 1, 0, 0);
		GlStateManager.rotate(renderManager.playerViewY, 0, 1, 0);
	}

	private void renderEnd(int frame, EnumFacing blockSide) {
		GlStateManager.rotate(-renderManager.playerViewY, 0, 1, 0);
		GlStateManager.rotate(renderManager.playerViewX, 1, 0, 0);

		drawBeamCap(frame, -450);

		GlStateManager.rotate(-renderManager.playerViewX, 1, 0, 0);
		GlStateManager.rotate(renderManager.playerViewY, 0, 1, 0);

		switch (blockSide) {
		case DOWN:
			GlStateManager.rotate(-90, 1, 0, 0);
			GlStateManager.translate(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GlStateManager.translate(0, 0, 0.01f);
			GlStateManager.rotate(90, 1, 0, 0);
			break;
		case UP:
			GlStateManager.rotate(90, 1, 0, 0);
			GlStateManager.translate(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GlStateManager.translate(0, 0, 0.01f);
			GlStateManager.rotate(-90, 1, 0, 0);
			break;
		case NORTH:
			GlStateManager.translate(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GlStateManager.translate(0, 0, 0.01f);
			break;
		case SOUTH:
			GlStateManager.rotate(180, 0, 1, 0);
			GlStateManager.translate(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GlStateManager.translate(0, 0, 0.01f);
			GlStateManager.rotate(-180, 0, 1, 0);
			break;
		case EAST:
			GlStateManager.rotate(270, 0, 1, 0);
			GlStateManager.translate(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GlStateManager.translate(0, 0, 0.01f);
			GlStateManager.rotate(-270, 0, 1, 0);
			break;
		case WEST:
			GlStateManager.rotate(90, 0, 1, 0);
			GlStateManager.translate(0, 0, -0.01f);
			drawBeamCap(frame, 240);
			GlStateManager.translate(0, 0, 0.01f);
			GlStateManager.rotate(-90, 0, 1, 0);
			break;
		default:
			break;
		}
	}

	private void renderBeam(boolean sightClear, double length, double yaw, double pitch, int frame) {
		GlStateManager.rotate(-90, 0, 0, 1);
		GlStateManager.rotate((float) yaw, 1, 0, 0);
		GlStateManager.rotate((float) pitch, 0, 0, 1);

		if (sightClear) {
			GlStateManager.rotate(90, 0, 1, 0);
		} else {
			GlStateManager.rotate(renderManager.playerViewX, 0, 1, 0);
		}
		drawBeamSide(frame, 80, length);
		if (sightClear) {
			GlStateManager.rotate(-90, 0, 1, 0);
		} else {
			GlStateManager.rotate(-renderManager.playerViewX, 0, 1, 0);
		}

		if (!sightClear) {
			GlStateManager.rotate(-renderManager.playerViewX, 0, 1, 0);
			GlStateManager.rotate(180, 0, 1, 0);
			drawBeamSide(frame, 240, length);
			GlStateManager.rotate(-180, 0, 1, 0);
			GlStateManager.rotate(renderManager.playerViewX, 0, 1, 0);
		}

		GlStateManager.rotate((float) -pitch, 0, 0, 1);
		GlStateManager.rotate((float) -yaw, 1, 0, 0);
		GlStateManager.rotate(90, 0, 0, 1);
	}

	private void setupGL() {
		GlStateManager.pushMatrix();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.enableBlend();
		GlStateManager.disableLighting();
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0);
	}

	private void revertGL() {
		GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.enableLighting();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBeam entity) {
		return RenderBeam.TEXTURE;
	}

}
