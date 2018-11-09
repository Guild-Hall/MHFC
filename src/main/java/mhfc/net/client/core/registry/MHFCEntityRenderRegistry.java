package mhfc.net.client.core.registry;

import com.github.worldsender.mcanm.client.ClientLoader;
import com.github.worldsender.mcanm.client.mcanmmodel.IModel;
import com.github.worldsender.mcanm.client.model.IEntityAnimator;
import com.github.worldsender.mcanm.client.renderer.IAnimatedObject;
import com.github.worldsender.mcanm.client.renderer.entity.RenderAnimatedModel;
import com.github.worldsender.mcanm.common.CommonLoader;
import com.github.worldsender.mcanm.common.skeleton.ISkeleton;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import mhfc.net.MHFCMain;
import mhfc.net.client.render.entity.RenderKirin;
import mhfc.net.client.render.entity.RenderNargacuga;
import mhfc.net.client.render.projectile.RenderBlockProjectile;
import mhfc.net.client.render.projectile.RenderBreathe;
import mhfc.net.client.render.projectile.RenderBullet;
import mhfc.net.client.render.projectile.RenderDeviljhoBeam1;
import mhfc.net.client.render.projectile.RenderNargacugaSpike;
import mhfc.net.client.render.projectile.RenderPaintball;
import mhfc.net.client.render.projectile.RenderRathalosFireball;
import mhfc.net.client.render.projectile.RenderWyverniaArrow;
import mhfc.net.common.entity.fx.EntityDeviljhoLaserBreath;
import mhfc.net.common.entity.monster.EntityBarroth;
import mhfc.net.common.entity.monster.EntityDelex;
import mhfc.net.common.entity.monster.EntityDeviljho;
import mhfc.net.common.entity.monster.EntityGargwa;
import mhfc.net.common.entity.monster.EntityKirin;
import mhfc.net.common.entity.monster.EntityLagiacrus;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.entity.monster.EntityRathalos;
import mhfc.net.common.entity.monster.EntityTigrex;
import mhfc.net.common.entity.monster.wip.EntityGiaprey;
import mhfc.net.common.entity.monster.wip.EntityGreatJaggi;
import mhfc.net.common.entity.monster.wip.EntityUkanlos;
import mhfc.net.common.entity.projectile.EntityBullet;
import mhfc.net.common.entity.projectile.EntityDeviljhoBeam1;
import mhfc.net.common.entity.projectile.EntityPaintball;
import mhfc.net.common.entity.projectile.EntityProjectileBlock;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityWyverniaArrow;
import mhfc.net.common.entity.projectile.NargacugaSpike;
import mhfc.net.common.index.ResourceInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class MHFCEntityRenderRegistry {
	public static void staticInit() {}

	static {
		MHFCMain.preInitPhase.registerEntryCallback(e -> preInit());
	}

	private static void preInit() {
		renderMonster();
		renderBlockEntities();
	}

	private static void renderMonster() {
		//AdvanceRender
		advanceRenderer(EntityNargacuga.class, manager -> new RenderNargacuga(manager));
		advanceRenderer(EntityKirin.class, manager -> new RenderKirin(manager));

		//BasicRender
		basicRenderer(
				EntityTigrex.class,
				ResourceInterface.mob_tigrex_textureDir,
				ResourceInterface.mob_tigrex_model,
				ResourceInterface.mob_tigrex_skeleton,
				1.0F);
		basicRenderer(
				EntityDelex.class,
				ResourceInterface.mob_delex_textureDir,
				ResourceInterface.mob_delex_model,
				ResourceInterface.mob_delex_skeleton,
				0F);
		basicRenderer(
				EntityGreatJaggi.class,
				ResourceInterface.mob_greatjaggi_textureDir,
				ResourceInterface.mob_greatjaggi_model,
				ResourceInterface.mob_greatjaggi_skeleton,
				1.0F);
		basicRenderer(
				EntityLagiacrus.class,
				ResourceInterface.mob_lagiacrus_textureDir,
				ResourceInterface.mob_lagiacrus_model,
				ResourceInterface.mob_lagiacrus_skeleton,
				1.0F);

		basicRenderer(
				EntityDeviljho.class,
				ResourceInterface.mob_deviljho_textureDir,
				ResourceInterface.mob_deviljho_model,
				ResourceInterface.mob_deviljho_skeleton,
				1.0F);
		basicRenderer(
				EntityGargwa.class,
				ResourceInterface.mob_gagua_textureDir,
				ResourceInterface.mob_gagua_model,
				ResourceInterface.mob_gagua_skeleton,
				1.0F);
		basicRenderer(
				EntityRathalos.class,
				ResourceInterface.mob_rathalos_textureDir,
				ResourceInterface.mob_rathalos_model,
				ResourceInterface.mob_rathalos_skeleton,
				1.0F);
		basicRenderer(
				EntityBarroth.class,
				ResourceInterface.mob_barroth_textureDir,
				ResourceInterface.mob_barroth_model,
				ResourceInterface.mob_barroth_skeleton,
				1.0F);

		registerAnimatedRenderer(EntityGiaprey.class, ResourceInterface.mob_giaprey_model, 1.0F);
		registerAnimatedRenderer(EntityUkanlos.class, ResourceInterface.mob_ukanlos_model, 1.0F);

	}

	private static void renderBlockEntities() {

		RenderingRegistry.registerEntityRenderingHandler(EntityDeviljhoBeam1.class, RenderDeviljhoBeam1::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileBlock.class, RenderBlockProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityRathalosFireball.class, RenderRathalosFireball::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityPaintball.class, m -> {
			RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
			// late insertion
			Preconditions.checkState(itemRender != null, "where is my item render...");
			return new RenderPaintball(m, itemRender);
		});
		RenderingRegistry.registerEntityRenderingHandler(EntityWyverniaArrow.class, RenderWyverniaArrow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityBullet.class, RenderBullet::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityDeviljhoLaserBreath.class, RenderBreathe::new);
		RenderingRegistry.registerEntityRenderingHandler(NargacugaSpike.class, RenderNargacugaSpike::new);
		//RenderingRegistry.registerEntityRenderingHandler(EntityFlashBomb.class, RenderSnowball::new);
	}

	@Deprecated
	private static <T extends EntityLiving & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass,
			String resource,
			float shadow) {
		registerAnimatedRenderer(entityClass, new ResourceLocation(resource), shadow);
	}

	@Deprecated
	private static <T extends EntityLiving & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass,
			ResourceLocation modelLoc,
			float shadow) {
		ISkeleton skeleton = CommonLoader.loadLegacySkeleton(modelLoc);
		IModel model = ClientLoader.loadModel(modelLoc, skeleton);
		RenderingRegistry.registerEntityRenderingHandler(entityClass, getRender(model, shadow));
	}

	@Deprecated
	private static <T extends EntityLiving & IAnimatedObject> IRenderFactory<T> getRender(IModel model, float shadow) {
		return RenderAnimatedModel.fromModel(model, shadow);
	}

	private static <T extends EntityLiving & IAnimatedObject> void basicRenderer(
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

	private static <T extends EntityLiving & IAnimatedObject> void registerAnimatedRenderer(
			Class<T> entityClass,
			String textureDir,
			ResourceLocation modelLoc,
			ResourceLocation sklLoc,
			float shadow) {
		ISkeleton skeleton = CommonLoader.loadSkeleton(sklLoc);
		IModel model = ClientLoader.loadModel(modelLoc, skeleton);
		IEntityAnimator<T> animator = getAnimator(textureDir);
		IRenderFactory<T> animatedModel = RenderAnimatedModel.fromModel(animator, model, shadow);

		advanceRenderer(entityClass, animatedModel);
	}

	private static <T extends EntityLiving & IAnimatedObject> void advanceRenderer(
			Class<T> clazz,
			IRenderFactory<T> render) {
		RenderingRegistry.registerEntityRenderingHandler(clazz, render);
	}

	public static <T extends EntityLiving> IEntityAnimator<T> getAnimator(String textureDir) {
		LoadingCache<String, ResourceLocation> cachedResourceLoc = CacheBuilder.newBuilder().maximumSize(100)
				.build(new CacheLoader<String, ResourceLocation>() {
					@Override
					public ResourceLocation load(String key) {
						return new ResourceLocation(textureDir + key + ".png");
					}
				});
		IEntityAnimator<T> animator = (entity, buffer, partialTick, _1, _2, _3, _4, _5) -> {
			return IAnimatedObject.class.cast(entity).preRenderCallback(partialTick, buffer)
					.setTextureTransform(cachedResourceLoc::getUnchecked);
		};
		return animator;
	}
}
