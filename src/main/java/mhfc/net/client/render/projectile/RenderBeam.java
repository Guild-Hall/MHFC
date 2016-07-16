package mhfc.net.client.render.projectile;

import org.lwjgl.opengl.GL11;

import mhfc.net.common.entity.projectile.EntityBeam;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderBeam extends Render {
	
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(MHFCReference.main_modid, "textures/projectile/beam.png");
    private static final double beamTextureWidth = 256;
    private static final double beamTextureHeight = 32;
    private static final double beamStartingEntry = 1.3;
    private static final double beamAoE = 1;
    private boolean sightClear = false;
    
    
	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float delta) {
		EntityBeam objectRender = (EntityBeam)entity;
		
		sightClear = objectRender.beamCaster instanceof EntityPlayer && Minecraft.getMinecraft().thePlayer == objectRender.beamCaster && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
		double length = Math.sqrt(Math.pow(objectRender.collidePosX - objectRender.posX, 2) + Math.pow(objectRender.collidePosY - objectRender.posY, 2) + Math.pow(objectRender.collidePosZ - objectRender.posZ, 2));
		int frame = MathHelper.floor_double((objectRender.appear - 1 + delta) * 2);
	        if (frame < 0) {
	            frame = 6;
	        }
	    GL11.glPushMatrix();
	    GL11.glTranslated(x, y, z);
	    setupGL();
	    bindEntityTexture(objectRender);
	    
	    GL11.glDepthMask(false);
	    renderStart(frame);
	    renderBeam(length, 180 / Math.PI * objectRender.getYaw(), 180 / Math.PI * objectRender.getPitch(), frame);
	    GL11.glTranslated(objectRender.collidePosX - objectRender.posX, objectRender.collidePosY - objectRender.posY, objectRender.collidePosZ - objectRender.posZ);
	    renderEnd(frame, objectRender.blockSide);
	    GL11.glDepthMask(true);
	    GL11.glTranslated(objectRender.posX - objectRender.collidePosX, objectRender.posY - objectRender.collidePosY, objectRender.posZ - objectRender.collidePosZ);
	    
	    GL11.glColorMask(false, false, false, true);
	    if (Minecraft.getMinecraft().gameSettings.thirdPersonView != 0) {
	    	renderStart(frame);
	        	}
	    renderBeam(length, 180 / Math.PI * objectRender.getYaw(), 180 / Math.PI * objectRender.getPitch(), frame);
	    GL11.glTranslated(objectRender.collidePosX - objectRender.posX, objectRender.collidePosY - objectRender.posY, objectRender.collidePosZ - objectRender.posZ);
	    renderEnd(frame, -1);
	    GL11.glColorMask(true, true, true, true);

	    revertGL();
	    GL11.glPopMatrix();
	
	}
	
	private void renderStart(int frame) {
		if (sightClear) {
			return;
	 	      }
		GL11.glRotatef(-renderManager.playerViewY, 0, 1, 0);
		GL11.glRotatef(renderManager.playerViewX, 1, 0, 0);
		double minimumU = 0 + 16D / beamTextureWidth * frame;
		double minimumV = 0;
		double maximumU = minimumU + 16D / beamTextureWidth;
		double maximumV = minimumV + 16D / beamTextureHeight;
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setBrightness(240);
		t.setColorRGBA_F(1, 1, 1, 1);
		t.addVertexWithUV(-beamStartingEntry, -beamStartingEntry, 0, minimumU, minimumV);
		t.addVertexWithUV(-beamStartingEntry, beamStartingEntry, 0, minimumU, maximumV);
		t.addVertexWithUV(beamStartingEntry, beamStartingEntry, 0, maximumU, maximumV);
		t.addVertexWithUV(beamStartingEntry, -beamStartingEntry, 0, maximumU, minimumV);
		t.draw();
		GL11.glRotatef(renderManager.playerViewX, -1, 0, 0);
		GL11.glRotatef(-renderManager.playerViewY, 0, -1, 0);
	    	}
	  	
	private void renderEnd(int frame, int side) {
		GL11.glRotatef(-renderManager.playerViewY, 0, 1, 0);
		GL11.glRotatef(renderManager.playerViewX, 1, 0, 0);
		double minimumU = 0 + 16D / beamTextureWidth * frame;
		double mininumV = 0;
		double maximumU = minimumU + 16D / beamTextureWidth;
		double maximumV = mininumV + 16D / beamTextureHeight;
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setBrightness(-450);
		t.setColorRGBA_F(17, 5, 6, 4);
		t.addVertexWithUV(-beamStartingEntry, -beamStartingEntry, 0, minimumU, mininumV);
		t.addVertexWithUV(-beamStartingEntry, beamStartingEntry, 0, minimumU, maximumV);
		t.addVertexWithUV(beamStartingEntry, beamStartingEntry, 0, maximumU, maximumV);
		t.addVertexWithUV(beamStartingEntry, -beamStartingEntry, 0, maximumU, mininumV);
		t.draw();
		GL11.glRotatef(renderManager.playerViewX, -1, 0, 0);
		GL11.glRotatef(-renderManager.playerViewY, 0, -1, 0);
		if (side == -1) {
		return;
		}
		t.startDrawingQuads();
		t.setBrightness(240);
		t.setColorRGBA_F(1, 1, 1, 1);
		t.addVertexWithUV(-beamStartingEntry, -beamStartingEntry, 0, minimumU, mininumV);
		t.addVertexWithUV(-beamStartingEntry, beamStartingEntry, 0, minimumU, maximumV);
		t.addVertexWithUV(beamStartingEntry, beamStartingEntry, 0, maximumU, maximumV);
		t.addVertexWithUV(beamStartingEntry, -beamStartingEntry, 0, maximumU, mininumV);
		if (side == 5) {
			GL11.glRotatef(270, 0, 1, 0);
			GL11.glTranslatef(0, 0, -0.01f);
			t.draw();
			GL11.glTranslatef(0, 0, 0.01f);
			GL11.glRotatef(-270, 0, 1, 0);
	        	} else if (side == 4) {
	        		GL11.glRotatef(90, 0, 1, 0);
	        		GL11.glTranslatef(0, 0, -0.01f);
	        		t.draw();
	        		GL11.glTranslatef(0, 0, 0.01f);
	        		GL11.glRotatef(-90, 0, 1, 0);
	        } else if (side == 3) {
	            	GL11.glRotatef(180, 0, 1, 0);
	            	GL11.glTranslatef(0, 0, -0.01f);
	            	t.draw();
	            	GL11.glTranslatef(0, 0, 0.01f);
	            	GL11.glRotatef(-180, 0, 1, 0);
	        } else if (side == 2) {
	            	GL11.glTranslatef(0, 0, -0.01f);
	            	t.draw();
	            	GL11.glTranslatef(0, 0, 0.01f);
	        } else if (side == 0) {
	            	GL11.glRotatef(-90, 1, 0, 0);
	            	GL11.glTranslatef(0, 0, -0.01f);
	            	t.draw();
	            	GL11.glTranslatef(0, 0, 0.01f);
	            	GL11.glRotatef(90, 1, 0, 0);
	        } else if (side == 1) {
	            	GL11.glRotatef(90, 1, 0, 0);
	            	GL11.glTranslatef(0, 0, -0.01f);
	            	t.draw();
	            	GL11.glTranslatef(0, 0, 0.01f);
	            	GL11.glRotatef(-90, 1, 0, 0);
	        }
	    }

	private void renderBeam(double length, double yaw, double pitch, int frame) {
		double minimumU = 0;
		double minimumV = 16 / beamTextureHeight + 1 / beamTextureHeight * frame;
		double maximumU = minimumU + 20 / beamTextureWidth;
		double maximumV = minimumV + 1 / beamTextureHeight;
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.setBrightness(80);
		t.setColorRGBA_F(17, 5, 6, 4);
		t.addVertexWithUV(-beamAoE, 0, 0, minimumU, minimumV);
		t.addVertexWithUV(-beamAoE, length, 0, minimumU, maximumV);
		t.addVertexWithUV(beamAoE, length, 0, maximumU, maximumV);
		t.addVertexWithUV(beamAoE, 0, 0, maximumU, minimumV);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glRotatef((float) yaw, 1, 0, 0);
		GL11.glRotatef((float) pitch, 0, 0, 1);
		if (sightClear) {
			GL11.glRotatef(90, 0, 1, 0);
	        	} else {
	        		GL11.glRotatef(renderManager.playerViewX, 0, 1, 0);
	        }	
		t.draw();
		if (sightClear) {
	            GL11.glRotatef(-90, 0, 1, 0);
				} else {
					GL11.glRotatef(-renderManager.playerViewX, 0, 1, 0);
				}

		if (!sightClear) {
			t.startDrawingQuads();
			t.setBrightness(240);
			t.setColorRGBA_F(1, 1, 1, 1);
			t.addVertexWithUV(-beamAoE, 0, 0, minimumU, minimumV);
			t.addVertexWithUV(-beamAoE, length, 0, minimumU, maximumV);
			t.addVertexWithUV(beamAoE, length, 0, maximumU, maximumV);
			t.addVertexWithUV(beamAoE, 0, 0, maximumU, minimumV);
			GL11.glRotatef(-renderManager.playerViewX, 0, 1, 0);
			GL11.glRotatef(180, 0, 1, 0);
			t.draw();
			GL11.glRotatef(-180, 0, 1, 0);
			GL11.glRotatef(renderManager.playerViewX, 0, 1, 0);
	        	}
		GL11.glRotatef((float) -pitch, 0, 0, 1);
		GL11.glRotatef((float) -yaw, 1, 0, 0);
		GL11.glRotatef(90, 0, 0, 1);
	}
	
	private void setupGL() {
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
	}
		
	private void revertGL() {
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
	}

	    @Override
	    protected ResourceLocation getEntityTexture(Entity entity) {
	        return RenderBeam.TEXTURE;
	    }

}
