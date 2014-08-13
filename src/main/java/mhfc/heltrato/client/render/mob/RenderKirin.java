package mhfc.heltrato.client.render.mob;

import java.util.Random;

import mhfc.heltrato.client.model.mob.boss.ModelKirin;
import mhfc.heltrato.common.entity.mob.EntityKirin;
import mhfc.heltrato.common.util.lib.MHFCReference;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderKirin extends RenderLiving {
	private float scale;
	protected ModelKirin mainModel;

	public RenderKirin(ModelBase par1ModelBase, float par2, float par3) {
		super(par1ModelBase, par2 * par3);
		this.scale = par3;
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityKirin par1, float par2) {
		GL11.glScalef(this.scale, this.scale, this.scale);
	}

	protected ResourceLocation func_110870_a(EntityKirin par1) {
		return new ResourceLocation(MHFCReference.mob_kirin_tex);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase,
			float partialTickTime) {
		this.mainModel.setPartialTick(partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return this.func_110870_a((EntityKirin) par1Entity);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2,
			float f, float f1) {
		// EntityKirin e = (EntityKirin)entity;
		renderElectricField((float) d, (float) d1, (float) d2,
				(EntityLiving) entity);
		doRender((EntityLiving) entity, d, d1, d2, f, f1);
	}

	protected void renderElectricField(float x, float y, float z,
			EntityLiving entityliving) {
		GL11.glDisable(3553);
		GL11.glDisable(2896);
		Tessellator tessellator = Tessellator.instance;
		// int color = 5592575;
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 1);
		Random rnd = new Random();
		rnd.setSeed(entityliving.getEntityId() + entityliving.ticksExisted);
		int steps = 16;
		GL11.glLineWidth(2.0F);

		for (int a = 0; a < 5; a++) {
			steps = rnd.nextInt(2) + 5;
			tessellator.startDrawing(3);
			tessellator.setColorRGBA_F(0.5F, 0.64F, 1.0F, 0.6F);
			int startX = rnd.nextInt();
			int startY = rnd.nextInt();
			int startZ = rnd.nextInt();

			for (int i = 0; i <= steps; i++) {
				// float stepVariation = i / steps;
				double dist = entityliving.width;
				double boltSize = 400.0D;
				double varX = Math.sin((i + startX) / boltSize
						* 3.141592653589793D * 2.0D)
						* dist + (rnd.nextDouble() - 0.5D) * 0.5D;
				double varZ = Math.cos((i + startZ) / boltSize
						* 3.141592653589793D * 2.0D)
						* dist + (rnd.nextDouble() - 0.5D) * 0.5D;
				double varY = Math.sin((entityliving.ticksExisted + i + startY)
						/ boltSize * 3.141592653589793D)
						+ rnd.nextDouble() + 1.8D;

				tessellator.addVertex(x + varX, y + varY, z + varZ);
			}

			tessellator.draw();
		}

		GL11.glDisable(3042);
		GL11.glEnable(3553);
		GL11.glEnable(2896);
	}

}
