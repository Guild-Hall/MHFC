package mhfc.net.common.core.registry;

import mhfc.net.client.model.mhfcmodel.MHMDModelLoader;
import mhfc.net.client.model.mob.ModelPopo;
import mhfc.net.client.model.mob.boss.ModelKirin;
import mhfc.net.client.model.mob.boss.ModelRathalos;
import mhfc.net.client.model.mob.boss.ModelTigrex;
import mhfc.net.client.render.mhfcmodel.RenderMHFCModel;
import mhfc.net.client.render.mob.RenderKirin;
import mhfc.net.client.render.mob.RenderPopo;
import mhfc.net.client.render.mob.RenderRathalos;
import mhfc.net.client.render.mob.RenderTigrex;
import mhfc.net.client.render.projectile.RenderRathalosFireball;
import mhfc.net.client.render.projectile.RenderTigrexBlock;
import mhfc.net.common.entity.mob.EntityKirin;
import mhfc.net.common.entity.mob.EntityPopo;
import mhfc.net.common.entity.mob.EntityRathalos;
import mhfc.net.common.entity.mob.EntityTest;
import mhfc.net.common.entity.mob.EntityTigrex;
import mhfc.net.common.entity.projectile.EntityRathalosFireball;
import mhfc.net.common.entity.projectile.EntityTigrexBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class MHFCRegRenderEntity {

	public static void render() {
		AdvancedModelLoader.registerModelHandler(MHMDModelLoader.instance);
		renderMonster();
		renderBlockEntities();
	}

	public static void renderMonster() {
		RenderingRegistry.registerEntityRenderingHandler(EntityTigrex.class,
				new RenderTigrex(new ModelTigrex(), 1.0F, 1.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityKirin.class,
				new RenderKirin(new ModelKirin(), 1.0F, 1.8F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRathalos.class,
				new RenderRathalos(new ModelRathalos(), 1.0F, 2.1F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPopo.class,
				new RenderPopo(new ModelPopo(), 1f, 1.4f));
		RenderingRegistry.registerEntityRenderingHandler(
				EntityTest.class,
				new RenderMHFCModel(MHMDModelLoader
						.loadModel(new ResourceLocation(
								"mhfc:models/testcube.mhmd")), 1.0f));
	}

	public static void renderBlockEntities() {
		RenderingRegistry.registerEntityRenderingHandler(
				EntityTigrexBlock.class, new RenderTigrexBlock());
		RenderingRegistry.registerEntityRenderingHandler(
				EntityRathalosFireball.class, new RenderRathalosFireball());
	}

}
