package mhfc.net.common.core.registry;

import mhfc.net.client.render.projectile.RenderRathalosFireball;
import mhfc.net.client.render.projectile.RenderTigrexBlock;
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
		
		
		
		
		
		
		
	}
	
	public static void renderBlockEntities() {
		render.registerEntityRenderingHandler(EntityTigrexBlock.class, new RenderTigrexBlock());
		render.registerEntityRenderingHandler(EntityRathalosFireball.class, new RenderRathalosFireball());
	}

	
}
