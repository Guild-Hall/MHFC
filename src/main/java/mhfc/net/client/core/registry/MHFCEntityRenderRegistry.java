package mhfc.net.client.core.registry;

import mhfc.net.client.render.mob.RenderAnimatedModel;
import mhfc.net.client.render.projectile.RenderRathalosFireball;
import mhfc.net.client.render.projectile.RenderTigrexBlock;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityTigrexBlock;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import com.github.worldsender.mcanm.client.model.mhfcmodel.animation.IAnimatedObject;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class MHFCEntityRenderRegistry {

	public static void init() {
		renderMonster();
		renderBlockEntities();
	}

	private static void renderMonster() {
		registerAnimatedRenderer(EntityTigrex.class,
				MHFCReference.mob_tigrex_model, 1.0F);
	}

	private static void renderBlockEntities() {
		RenderingRegistry.registerEntityRenderingHandler(
				EntityTigrexBlock.class, new RenderTigrexBlock());
		RenderingRegistry.registerEntityRenderingHandler(
				EntityRathalosFireball.class, new RenderRathalosFireball());
	}

	private static <T extends Entity & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass, String resource, float shadow) {
		registerAnimatedRenderer(entityClass, new ResourceLocation(resource),
				shadow);
	}

	private static <T extends Entity & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass, ResourceLocation resLoc, float shadow) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass,
				RenderAnimatedModel.fromResLocation(resLoc, shadow));
	}
}
