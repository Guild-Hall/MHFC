package mhfc.net.client.render.entity;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

import java.util.Queue;

import org.lwjgl.opengl.GL11;

import com.github.worldsender.mcanm.client.ClientLoader;
import com.github.worldsender.mcanm.client.model.ModelAnimated;
import com.github.worldsender.mcanm.client.renderer.entity.RenderAnimatedModel;
import com.github.worldsender.mcanm.common.CommonLoader;

import mhfc.net.client.core.registry.MHFCEntityRenderRegistry;
import mhfc.net.common.entity.monster.EntityNargacuga;
import mhfc.net.common.util.lib.MHFCReference;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderNargacuga extends RenderAnimatedModel {
	private static final String TEXTURE_DIR = MHFCReference.mob_nargacuga_textureDir;
	private static final ResourceLocation MODEL_LOC = new ResourceLocation(MHFCReference.mob_nargacuga_model);
	private static final ResourceLocation SKELETON_LOC = new ResourceLocation(MHFCReference.mob_nargacuga_skeleton);
	private static final float SHADOW_SIZE = 1.0F;

	public RenderNargacuga() {
		super(
				new ModelAnimated(ClientLoader.loadModel(MODEL_LOC, CommonLoader.loadSkeleton(SKELETON_LOC))),
				MHFCEntityRenderRegistry.getAnimator(TEXTURE_DIR),
				SHADOW_SIZE);
	}

	private void renderEyeFollow(EntityNargacuga nargacuga, Queue<Vec3> queue) {
		GL11.glDisable(GL_TEXTURE_2D);
		GL11.glColor3f(1.0f, 0, 0);
		GL11.glBegin(GL_LINE_STRIP);
		for (Vec3 v : queue) {
			GL11.glVertex3d(-(v.xCoord - nargacuga.posX), v.yCoord - nargacuga.posY, v.zCoord - nargacuga.posZ);
		}
		GL11.glEnd();
		GL11.glEnable(GL_TEXTURE_2D);
	}

	@Override
	public void doRender(EntityLiving entity, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(entity, x, y, z, yaw, partialTicks);
	}

	@Override
	protected void renderModel(EntityLivingBase entity, float _1, float _2, float _3, float _4, float _5, float _6) {
		super.renderModel(entity, _1, _2, _3, _4, _5, _6);

		EntityNargacuga nargacuga = EntityNargacuga.class.cast(entity);
		if (!nargacuga.isEnraged() || true) {
			// TODO: enable those
			return;
		}
		Queue<Vec3> leftEyePos = nargacuga.getEyesPositionsLeft();
		Queue<Vec3> rightEyePos = nargacuga.getEyesPositionsLeft();
		renderEyeFollow(nargacuga, rightEyePos);
		renderEyeFollow(nargacuga, leftEyePos);
	}
}
