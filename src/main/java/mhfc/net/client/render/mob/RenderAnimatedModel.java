package mhfc.net.client.render.mob;

import mhfc.net.client.model.mob.ModelAnimated;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderAnimatedModel extends RenderLiving {

	protected ModelAnimated model;

	public RenderAnimatedModel(ModelAnimated model, float shadowSize) {
		super(model, shadowSize);
		this.model = model;
	}

	@Override
	public void doRender(EntityLiving p_76986_1_, double p_76986_2_,
			double p_76986_4_, double p_76986_6_, float p_76986_8_, float size) {
		super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_,
				p_76986_8_, size);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation("mhfc:textures/mobs/popo.png");
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entity, float partialRick) {
		this.model.setPartialTick(partialRick);
	}
	/**
	 * Convenience alternative to the constructor. A new {@link ModelAnimated}
	 * is instanciated from the {@link ResourceLocation} given (using the normal
	 * constructor).
	 *
	 * @param resLoc
	 * @param shadowSize
	 * @return
	 */
	public static RenderAnimatedModel fromResLocation(ResourceLocation resLoc,
			float shadowSize) {
		return new RenderAnimatedModel(new ModelAnimated(resLoc), shadowSize);
	}
}
