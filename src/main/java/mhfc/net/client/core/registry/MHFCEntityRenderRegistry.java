package mhfc.net.client.core.registry;

import mhfc.net.client.model.mob.ModelPopo;
import mhfc.net.client.model.mob.boss.ModelKirin;
import mhfc.net.client.model.mob.boss.ModelRathalos;
import mhfc.net.client.render.mob.RenderAnimatedModel;
import mhfc.net.client.render.mob.RenderKirin;
import mhfc.net.client.render.mob.RenderPopo;
import mhfc.net.client.render.mob.RenderRathalos;
import mhfc.net.client.render.projectile.RenderRathalosFireball;
import mhfc.net.client.render.projectile.RenderTigrexBlock;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.mob.EntityPopo;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityTigrexBlock;
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
				"mhfc:models/Tigrex/Tigrex.mcmd", 1.0F);
		RenderingRegistry.registerEntityRenderingHandler(EntityKirin.class,
				new RenderKirin(new ModelKirin(), 1.0F, 1.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRathalos.class,
				new RenderRathalos(new ModelRathalos(), 1.0F, 2.1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPopo.class,
				new RenderPopo(new ModelPopo(), 1f, 1.4f));
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
