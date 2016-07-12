package mhfc.net.client.render.projectile;

import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBeam extends Render {
	
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(MHFCReference.main_modid, "textures/projectile/beam.png");
    private static final double TEXTURE_WIDTH = 256;
    private static final double TEXTURE_HEIGHT = 32;
    private static final double START_RADIUS = 1.3;
    private static final double BEAM_RADIUS = 1;
    private boolean clearerView = false;
	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_,
			float p_76986_9_) {
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}

}
