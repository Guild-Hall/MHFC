package mhfc.heltrato.client.render.block;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import mhfc.heltrato.client.model.block.ModelHunterBench;
import mhfc.heltrato.client.model.block.ModelStunTrap;
import mhfc.heltrato.common.entity.mob.EntityKirin;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderStunTrap extends TileEntitySpecialRenderer{

	private ModelStunTrap model;
	private static final ResourceLocation texture = new ResourceLocation("mhfc:textures/tile/stuntrap.png");

 public RenderStunTrap(){
	model = new ModelStunTrap();
	
 }

 public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
	 	GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float)y - -2F, (float)z + 0.5F);
		GL11.glRotatef(180F, 0F, 0F, 1F);
		bindTexture(texture);
		GL11.glPushMatrix();
		model.renderModel(0.0825F);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	
 }
 
}