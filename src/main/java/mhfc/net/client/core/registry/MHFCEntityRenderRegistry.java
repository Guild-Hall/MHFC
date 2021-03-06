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
import mhfc.net.client.render.projectile.RenderNargacugaSpike;
import mhfc.net.client.render.projectile.RenderPaintball;
import mhfc.net.client.render.projectile.RenderWyverniaArrow;
import mhfc.net.common.entity.creature.Barroth;
import mhfc.net.common.entity.creature.Delex;
import mhfc.net.common.entity.creature.Deviljho;
import mhfc.net.common.entity.creature.Gargwa;
import mhfc.net.common.entity.creature.Kirin;
import mhfc.net.common.entity.creature.Lagiacrus;
import mhfc.net.common.entity.creature.Nargacuga;
import mhfc.net.common.entity.creature.Rathalos;
import mhfc.net.common.entity.creature.Tigrex;
import mhfc.net.common.entity.creature.incomplete.Giaprey;
import mhfc.net.common.entity.creature.incomplete.GreatJaggi;
import mhfc.net.common.entity.creature.incomplete.Ukanlos;
import mhfc.net.common.entity.fx.FXDeviljhoLaser;
import mhfc.net.common.entity.projectile.ProjectileBullet;
import mhfc.net.common.entity.projectile.ProjectilePaintball;
import mhfc.net.common.entity.projectile.ProjectileBlock;
import mhfc.net.common.entity.projectile.ProjectileArrow;
import mhfc.net.common.entity.projectile.ProjectileNargaSpike;
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
		advanceRenderer(Nargacuga.class, manager -> new RenderNargacuga(manager));
		advanceRenderer(Kirin.class, manager -> new RenderKirin(manager));

		//BasicRender
		basicRenderer(
				Tigrex.class,
				ResourceInterface.mob_tigrex_textureDir,
				ResourceInterface.mob_tigrex_model,
				ResourceInterface.mob_tigrex_skeleton,
				1.0F);
		basicRenderer(
				Delex.class,
				ResourceInterface.mob_delex_textureDir,
				ResourceInterface.mob_delex_model,
				ResourceInterface.mob_delex_skeleton,
				0F);
		basicRenderer(
				GreatJaggi.class,
				ResourceInterface.mob_greatjaggi_textureDir,
				ResourceInterface.mob_greatjaggi_model,
				ResourceInterface.mob_greatjaggi_skeleton,
				1.0F);
		basicRenderer(
				Lagiacrus.class,
				ResourceInterface.mob_lagiacrus_textureDir,
				ResourceInterface.mob_lagiacrus_model,
				ResourceInterface.mob_lagiacrus_skeleton,
				1.0F);

		basicRenderer(
				Deviljho.class,
				ResourceInterface.mob_deviljho_textureDir,
				ResourceInterface.mob_deviljho_model,
				ResourceInterface.mob_deviljho_skeleton,
				1.0F);
		basicRenderer(
				Gargwa.class,
				ResourceInterface.mob_gagua_textureDir,
				ResourceInterface.mob_gagua_model,
				ResourceInterface.mob_gagua_skeleton,
				1.0F);
		basicRenderer(
				Rathalos.class,
				ResourceInterface.mob_rathalos_textureDir,
				ResourceInterface.mob_rathalos_model,
				ResourceInterface.mob_rathalos_skeleton,
				1.0F);
		basicRenderer(
				Barroth.class,
				ResourceInterface.mob_barroth_textureDir,
				ResourceInterface.mob_barroth_model,
				ResourceInterface.mob_barroth_skeleton,
				1.0F);

		registerAnimatedRenderer(Giaprey.class, ResourceInterface.mob_giaprey_model, 1.0F);
		registerAnimatedRenderer(Ukanlos.class, ResourceInterface.mob_ukanlos_model, 1.0F);

	}

	private static void renderBlockEntities() {

		RenderingRegistry.registerEntityRenderingHandler(ProjectileBlock.class, RenderBlockProjectile::new);
		RenderingRegistry.registerEntityRenderingHandler(ProjectilePaintball.class, m -> {
			RenderItem itemRender = Minecraft.getMinecraft().getRenderItem();
			// late insertion
			Preconditions.checkState(itemRender != null, "where is my item render...");
			return new RenderPaintball(m, itemRender);
		});
		RenderingRegistry.registerEntityRenderingHandler(ProjectileArrow.class, RenderWyverniaArrow::new);
		RenderingRegistry.registerEntityRenderingHandler(ProjectileBullet.class, RenderBullet::new);
		RenderingRegistry.registerEntityRenderingHandler(FXDeviljhoLaser.class, RenderBreathe::new);
		RenderingRegistry.registerEntityRenderingHandler(ProjectileNargaSpike.class, RenderNargacugaSpike::new);
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
