package mhfc.net.client.render.mob;

import mhfc.net.client.model.mob.boss.ModelRathalos;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRathalos extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation(
			MHFCReference.mob_rathalos_tex);

	public RenderRathalos(ModelRathalos par1ModelBase, float par2, float par3) {
		super(par1ModelBase, par2 * par3);
	}

	protected ResourceLocation func_110870_a(EntityRathalos par1) {
		return texture;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase,
			float partialTick) {
		((ModelRathalos) this.mainModel).setPartialTick(partialTick);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
		return this.func_110870_a((EntityRathalos) par1Entity);
	}

}
