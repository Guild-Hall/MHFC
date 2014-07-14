package mhfc.heltrato.common.core.registry;

import mhfc.heltrato.client.model.mob.ModelAptonoth;
import mhfc.heltrato.client.model.mob.ModelPopo;
import mhfc.heltrato.client.model.mob.boss.ModelKirin;
import mhfc.heltrato.client.model.mob.boss.ModelRathalos;
import mhfc.heltrato.client.model.mob.boss.ModelTigrex;
import mhfc.heltrato.client.render.mob.RenderAptonoth;
import mhfc.heltrato.client.render.mob.RenderKirin;
import mhfc.heltrato.client.render.mob.RenderPopo;
import mhfc.heltrato.client.render.mob.RenderRathalos;
import mhfc.heltrato.client.render.mob.RenderTigrex;
import mhfc.heltrato.client.render.projectile.RenderRathalosFireball;
import mhfc.heltrato.client.render.projectile.RenderTigrexBlock;
import mhfc.heltrato.common.entity.mob.EntityAptonoth;
import mhfc.heltrato.common.entity.mob.EntityKirin;
import mhfc.heltrato.common.entity.mob.EntityPopo;
import mhfc.heltrato.common.entity.mob.EntityRathalos;
import mhfc.heltrato.common.entity.mob.EntityTigrex;
import mhfc.heltrato.common.entity.projectile.EntityRathalosFireball;
import mhfc.heltrato.common.entity.projectile.EntityTigrexBlock;
import cpw.mods.fml.client.registry.RenderingRegistry;


public class MHFCRegRenderEntity 
{
	private static RenderingRegistry render;
	
	public static void render() {
		renderMonster();
		renderBlockEntities();
		
	}
	
	public static void renderMonster() {
		
		render.registerEntityRenderingHandler(EntityTigrex.class, new RenderTigrex(new ModelTigrex(), 1.0F , 1.7F));
		render.registerEntityRenderingHandler(EntityKirin.class, new RenderKirin(new ModelKirin(), 1.0F, 1.8F));
		render.registerEntityRenderingHandler(EntityRathalos.class, new RenderRathalos(new ModelRathalos(), 1.0F, 2.1F));
		render.registerEntityRenderingHandler(EntityPopo.class, new RenderPopo(new ModelPopo(), 1f, 1.6f));
		render.registerEntityRenderingHandler(EntityAptonoth.class, new RenderAptonoth(new ModelAptonoth(), 1, 1.4f));
		
		
		
		
		
		
	}
	
	public static void renderBlockEntities() {
		render.registerEntityRenderingHandler(EntityTigrexBlock.class, new RenderTigrexBlock());
		render.registerEntityRenderingHandler(EntityRathalosFireball.class, new RenderRathalosFireball());
	}

	
}
