package mhfc.net.client.render.entity;

import com.github.worldsender.mcanm.client.ClientLoader;
import com.github.worldsender.mcanm.client.model.ModelAnimated;
import com.github.worldsender.mcanm.client.renderer.entity.RenderAnimatedModel;
import com.github.worldsender.mcanm.common.CommonLoader;
import mhfc.net.client.core.registry.MHFCEntityRenderRegistry;
import mhfc.net.common.entity.creature.Kirin;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class RenderKirin extends RenderAnimatedModel<Kirin> {

	public RenderKirin(RenderManager manager) {
		super(manager,new ModelAnimated(ClientLoader.loadModel(new ResourceLocation(ResourceInterface.mob_kirin_model), CommonLoader.loadSkeleton(new ResourceLocation(ResourceInterface.mob_kirin_skeleton)))),
				MHFCEntityRenderRegistry.getAnimator(ResourceInterface.mob_kirin_textureDir),
				1F);
	}
	
	@Override
	public void doRender(Kirin entity, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(entity, x, y, z, yaw, partialTicks);
		
		renderElectricField((float)x, (float)y, (float)z, entity);
	}
	
	@Override
	protected void renderModel(Kirin entity, float _1, float _2, float _3, float _4, float _5, float _6) {
		super.renderModel(entity, _1, _2, _3, _4, _5, _6);
		
	}
	
	
	
		
protected void renderElectricField(float x, float y, float z, EntityLiving entityliving){
	GlStateManager.pushMatrix();
	GlStateManager.disableTexture2D();
	GlStateManager.disableLighting();;
	GlStateManager.enableBlend();;
	GlStateManager.blendFunc(770, 1);
	Tessellator tessellator = Tessellator.getInstance();
	BufferBuilder buffer = tessellator.getBuffer();
	Random rnd = new Random();
	rnd.setSeed(entityliving.getEntityId() + entityliving.ticksExisted/2);
	Random rnd2 = new Random();
	rnd2.setSeed(entityliving.getEntityId() + (entityliving.ticksExisted + 1)/30);
	Random rnd3 = new Random();
	rnd3.setSeed(entityliving.getEntityId() + (entityliving.ticksExisted + 5)/30);
	int steps = 16;
	GlStateManager.glLineWidth(2.2F);
		for (int a = 0; a < 3; a++)	{
			steps = rnd.nextInt(26) + 5;
			
			buffer.begin(3, DefaultVertexFormats.POSITION);
			
			buffer.color(0.5F, 0.64F, 1.0F, 0.6F);
			int startX = rnd.nextInt();
			int startY = rnd2.nextInt();
			int startZ = rnd3.nextInt();
				for (int i = 0; i <= steps; i++) {
					//float stepVariation = i / steps;
					double dist = entityliving.width - 1;
					double boltSize = 80D;
					double varX = Math.sin((i + startX) / boltSize * 3.141592653589793D * 2.0D) * dist + (rnd.nextDouble() - 0.5D) * 0.5D;
					double varZ = Math.cos((i + startZ) / boltSize * 3.141592653589793D * 2.0D) * dist + (rnd.nextDouble() - 0.5D) * 0.5D;
					double varY = Math.sin((entityliving.ticksExisted + i + startY) / boltSize * 3.141592653589793D) + rnd.nextDouble() + 1.8D;
					buffer.pos(x + varX, y + varY, z + varZ).endVertex();
					buffer.normal(x, y, z);
				}
				tessellator.draw();
	    			}
						GlStateManager.disableBlend();
						GlStateManager.enableTexture2D();
						GlStateManager.enableLighting();
						GlStateManager.popMatrix();
	  
	
}
}
