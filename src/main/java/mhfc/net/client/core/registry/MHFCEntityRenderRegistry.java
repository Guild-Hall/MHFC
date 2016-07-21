package mhfc.net.client.core.registry;

import com.github.worldsender.mcanm.client.ClientLoader;
import com.github.worldsender.mcanm.client.mcanmmodel.IModel;
import com.github.worldsender.mcanm.client.model.IEntityAnimator;
import com.github.worldsender.mcanm.client.renderer.IAnimatedObject;
import com.github.worldsender.mcanm.client.renderer.entity.RenderAnimatedModel;
import com.github.worldsender.mcanm.common.CommonLoader;
import com.github.worldsender.mcanm.common.skeleton.ISkeleton;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cpw.mods.fml.client.registry.RenderingRegistry;
import mhfc.net.client.render.entity.RenderNargacuga;
import mhfc.net.client.render.projectile.RenderBeam;
import mhfc.net.client.render.projectile.RenderBlockProjectile;
import mhfc.net.client.render.projectile.RenderBreathe;
import mhfc.net.client.render.projectile.RenderBullet;
import mhfc.net.client.render.projectile.RenderNargacugaSpike;
import mhfc.net.client.render.projectile.RenderPaintball;
import mhfc.net.client.render.projectile.RenderRathalosFireball;
import mhfc.net.client.render.projectile.RenderWyverniaArrow;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.entity.monster.EntityDelex;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.monster.EntityGargwa;
import mhfc.net.common.entity.monster.EntityGiaprey;
import mhfc.net.common.entity.monster.EntityGreatJaggi;
import mhfc.net.common.entity.monster.EntityKirin;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.entity.monster.EntityUkanlos;
import mhfc.net.common.entity.projectile.EntityBeam;
import mhfc.net.common.entity.projectile.EntityBreathe;
import mhfc.net.common.entity.projectile.EntityBullet;
import mhfc.net.common.entity.projectile.EntityPaintball;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.entity.projectile.NargacugaSpike;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class MHFCEntityRenderRegistry {

	public static void init() {
		renderMonster();
		renderBlockEntities();
	}

	private static void renderMonster() {
		//AdvanceRender
		advanceRenderer(EntityNargacuga.class, new RenderNargacuga());

		//BasicRender
		basicRenderer(
				EntityTigrex.class,
				MHFCReference.mob_tigrex_textureDir,
				MHFCReference.mob_tigrex_model,
				MHFCReference.mob_tigrex_skeleton,
				1.0F);
		basicRenderer(
				EntityDelex.class,
				MHFCReference.mob_delex_textureDir,
				MHFCReference.mob_delex_model,
				MHFCReference.mob_delex_skeleton,
				1.0F);
		basicRenderer(
				EntityGreatJaggi.class,
				MHFCReference.mob_greatjaggi_textureDir,
				MHFCReference.mob_greatjaggi_model,
				MHFCReference.mob_greatjaggi_skeleton,
				1.0F);
		basicRenderer(
				EntityLagiacrus.class,
				MHFCReference.mob_lagiacrus_textureDir,
				MHFCReference.mob_lagiacrus_model,
				MHFCReference.mob_lagiacrus_skeleton,
				1.0F);

		basicRenderer(
				EntityDeviljho.class,
				MHFCReference.mob_deviljho_textureDir,
				MHFCReference.mob_deviljho_model,
				MHFCReference.mob_deviljho_skeleton,
				1.0F);
		basicRenderer(
				EntityKirin.class,
				MHFCReference.mob_kirin_textureDir,
				MHFCReference.mob_kirin_model,
				MHFCReference.mob_kirin_skeleton,
				1.0F);
		basicRenderer(
				EntityGargwa.class,
				MHFCReference.mob_gagua_textureDir,
				MHFCReference.mob_gagua_model,
				MHFCReference.mob_gagua_skeleton,
				1.0F);
		basicRenderer(
				EntityRathalos.class,
				MHFCReference.mob_rathalos_textureDir,
				MHFCReference.mob_rathalos_model,
				MHFCReference.mob_rathalos_skeleton,
				1.0F);
		basicRenderer(
				EntityBarroth.class,
				MHFCReference.mob_barroth_textureDir,
				MHFCReference.mob_barroth_model,
				MHFCReference.mob_barroth_skeleton,
				1.0F);

		registerAnimatedRenderer(EntityGiaprey.class, MHFCReference.mob_giaprey_model, 1.0F);
		registerAnimatedRenderer(EntityUkanlos.class, MHFCReference.mob_ukanlos_model, 1.0F);

	}

	private static void renderBlockEntities() {
		RenderingRegistry.registerEntityRenderingHandler(EntityBeam.class, new RenderBeam());
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBlock.class, new RenderBlockProjectile());
		RenderingRegistry.registerEntityRenderingHandler(EntityRathalosFireball.class, new RenderRathalosFireball());
		RenderingRegistry.registerEntityRenderingHandler(EntityPaintball.class, new RenderPaintball());
		RenderingRegistry.registerEntityRenderingHandler(EntityWyverniaArrow.class, new RenderWyverniaArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, new RenderBullet());
		RenderingRegistry.registerEntityRenderingHandler(EntityBreathe.class, new RenderBreathe());
		RenderingRegistry.registerEntityRenderingHandler(NargacugaSpike.class, new RenderNargacugaSpike());
	}

	@Deprecated
	private static <T extends Entity & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass,
			String resource,
			float shadow) {
		registerAnimatedRenderer(entityClass, new ResourceLocation(resource), shadow);
	}

	@Deprecated
	private static <T extends Entity & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass,
			ResourceLocation modelLoc,
			float shadow) {
		ISkeleton skeleton = CommonLoader.loadLegacySkeleton(modelLoc);
		IModel model = ClientLoader.loadModel(modelLoc, skeleton);
		RenderingRegistry.registerEntityRenderingHandler(entityClass, getRender(model, shadow));
	}

	@Deprecated
	private static RenderAnimatedModel getRender(IModel model, float shadow) {
		return RenderAnimatedModel.fromModel(model, shadow);
	}

	private static <T extends Entity & IAnimatedObject> void basicRenderer(
			Class<T> entityClass,
			String textureDir,
			String modelResource,
			String sklResource,
			float shadow) {
		registerAnimatedRenderer(
				entityClass,
				textureDir,
				new ResourceLocation(modelResource),
				new ResourceLocation(sklResource),
				shadow);
	}

	private static <T extends Entity & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass,
			String textureDir,
			ResourceLocation modelLoc,
			ResourceLocation sklLoc,
			float shadow) {
		ISkeleton skeleton = CommonLoader.loadSkeleton(sklLoc);
		IModel model = ClientLoader.loadModel(modelLoc, skeleton);
		IEntityAnimator animator = getAnimator(textureDir);
		RenderAnimatedModel animatedModel = RenderAnimatedModel.fromModel(animator, model, shadow);

		advanceRenderer(entityClass, animatedModel);
	}

	private static void advanceRenderer(Class<? extends Entity> clazz, Render render) {
		RenderingRegistry.registerEntityRenderingHandler(clazz, render);
	}

	public static IEntityAnimator getAnimator(String textureDir) {
		LoadingCache<String, ResourceLocation> cachedResourceLoc = CacheBuilder.newBuilder().maximumSize(100)
				.build(new CacheLoader<String, ResourceLocation>() {
					@Override
					public ResourceLocation load(String key) {
						return new ResourceLocation(textureDir + key + ".png");
					}
				});
		IEntityAnimator animator = (entity, buffer, partialTick, _1, _2, _3, _4, _5) -> {
			return IAnimatedObject.class.cast(entity).preRenderCallback(partialTick, buffer)
					.setTextureTransform(cachedResourceLoc::getUnchecked);
		};
		return animator;
	}
}
