package mhfc.net.client.render.mob;

import mhfc.net.client.model.mob.ModelPopo;
import mhfc.net.common.entity.mob.EntityPopo;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPopo extends RenderLiving {
	private static final ResourceLocation TEXTURE = new ResourceLocation(
			MHFCReference.mob_popo_tex);

	public RenderPopo(ModelPopo par1ModelBase, float par2, float par3) {
		super(par1ModelBase, par2 * par3);
	}

	protected ResourceLocation func_110870_a(EntityPopo par1) {
		return TEXTURE;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase,
			float par2) {}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return this.func_110870_a((EntityPopo) par1Entity);
	}

}
