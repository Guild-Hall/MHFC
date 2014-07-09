package mhfc.net.common.core.registry;

import mhfc.net.client.model.mob.ModelPopo;
import mhfc.net.client.model.mob.boss.ModelKirin;
import mhfc.net.client.model.mob.boss.ModelRathalos;
import mhfc.net.client.model.mob.boss.ModelTigrex;
import mhfc.net.client.render.mob.RenderKirin;
import mhfc.net.client.render.mob.RenderPopo;
import mhfc.net.client.render.mob.RenderRathalos;
import mhfc.net.client.render.mob.RenderTigrex;
import mhfc.net.client.render.projectile.RenderRathalosFireball;
import mhfc.net.client.render.projectile.RenderTigrexBlock;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.mob.EntityPopo;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityTigrexBlock;
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
		render.registerEntityRenderingHandler(EntityPopo.class, new RenderPopo(new ModelPopo(), 1f, 1.4f));
	}
	
	public static void renderBlockEntities() {
		render.registerEntityRenderingHandler(EntityTigrexBlock.class, new RenderTigrexBlock());
		render.registerEntityRenderingHandler(EntityRathalosFireball.class, new RenderRathalosFireball());
	}

	
}
