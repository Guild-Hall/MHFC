package mhfc.net.client.render.mhfcmodel;

import mhfc.net.client.model.mhfcmodel.ModelMHFC;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderMHFCModel extends RenderLiving {

	protected ModelMHFC model;

	public RenderMHFCModel(ModelMHFC model, float shadowSize) {
		super(model, shadowSize);
		this.model = model;
	}

	@Override
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_,
			double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_,
				p_76986_8_, p_76986_9_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation("mhfc:textures/mobs/popo.png");
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entity, float partialRick) {
		this.model.setPartialTick(partialRick);
	}
}
