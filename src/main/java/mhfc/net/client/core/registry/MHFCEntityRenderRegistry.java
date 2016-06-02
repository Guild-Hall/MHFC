package mhfc.net.client.core.registry;

import com.github.worldsender.mcanm.client.ClientLoader;
import com.github.worldsender.mcanm.client.mcanmmodel.IModel;
import com.github.worldsender.mcanm.client.renderer.IAnimatedObject;
import com.github.worldsender.mcanm.client.renderer.entity.RenderAnimatedModel;
import com.github.worldsender.mcanm.common.CommonLoader;
import com.github.worldsender.mcanm.common.skeleton.ISkeleton;

import cpw.mods.fml.client.registry.RenderingRegistry;
import mhfc.net.client.render.projectile.RenderBlockProjectile;
import mhfc.net.client.render.projectile.RenderBreathe;
import mhfc.net.client.render.projectile.RenderBullet;
import mhfc.net.client.render.projectile.RenderNargacugaSpike;
import mhfc.net.client.render.projectile.RenderRathalosFireball;
import mhfc.net.client.render.projectile.RenderWyverniaArrow;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.entity.monster.EntityDelex;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.monster.EntityGiaprey;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.entity.monster.EntityUkanlos;
import mhfc.net.common.entity.projectile.EntityBreathe;
import mhfc.net.common.entity.projectile.EntityBullet;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.entity.projectile.NargacugaSpike;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class MHFCEntityRenderRegistry {

	public static void init() {
		renderMonster();
		renderBlockEntities();
	}

	private static void renderMonster() {
		registerAnimatedRenderer(EntityDelex.class, MHFCReference.mob_delex_model, 1.0F);
		registerAnimatedRenderer(EntityTigrex.class, MHFCReference.mob_tigrex_model, 1.0F);
		registerAnimatedRenderer(EntityRathalos.class, MHFCReference.mob_rathalos_model, 1.0F);
		registerAnimatedRenderer(EntityGreatJaggi.class, MHFCReference.mob_greatjaggi_model, 1.0F);
		registerAnimatedRenderer(EntityDeviljho.class, MHFCReference.mob_deviljho_model, 1.0F);
		registerAnimatedRenderer(EntityBarroth.class, MHFCReference.mob_barroth_model, 1.0F);
		registerAnimatedRenderer(EntityNargacuga.class, MHFCReference.mob_nargacuga_model, 1.0F);
		registerAnimatedRenderer(EntityGiaprey.class, MHFCReference.mob_giaprey_model, 1.0F);
		registerAnimatedRenderer(EntityUkanlos.class, MHFCReference.mob_ukanlos_model, 1.0F);
		registerAnimatedRenderer(EntityLagiacrus.class, MHFCReference.mob_lagiacrus_model, 1.0F);

	}

	private static void renderBlockEntities() {
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBlock.class, new RenderBlockProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityRathalosFireball.class, new RenderRathalosFireball());
		RenderingRegistry.registerEntityRenderingHandler(EntityWyverniaArrow.class, new RenderWyverniaArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
		RenderingRegistry.registerEntityRenderingHandler(EntityBreathe.class, new RenderBreathe());
		RenderingRegistry.registerEntityRenderingHandler(NargacugaSpike.class, new RenderNargacugaSpike());
	}

	private static <T extends Entity & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass,
			String resource,
			float shadow) {
		registerAnimatedRenderer(entityClass, new ResourceLocation(resource), shadow);
	}

	private static <T extends Entity & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass,
			ResourceLocation resLoc,
			float shadow) {
		ISkeleton skeleton = CommonLoader.loadLegacySkeleton(resLoc);
		IModel model = ClientLoader.loadModel(resLoc, skeleton);
		RenderingRegistry.registerEntityRenderingHandler(entityClass, RenderAnimatedModel.fromModel(model, shadow));
	}
}
