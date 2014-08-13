package mhfc.net.client.model.mob;

import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.Random;

import mhfc.net.client.model.PartTickModelBase;
import mhfc.net.client.model.mhfcmodel.ModelMHMD;
import mhfc.net.client.model.mhfcmodel.ModelRegistry;
import mhfc.net.client.model.mhfcmodel.animation.IAnimatedObject;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * A general purpose model that should fulfill most of your needs. I uses a
 * {@link ModelMHMD} internally to render thus the registered entity class HAS
 * TO IMPLEMENT {@link IAnimatedObject}. This will throw an exception during
 * rendering otherwise.
 *
 * @author WorldSEnder
 *
 */
public class ModelAnimated extends PartTickModelBase {
	protected ModelMHMD model;
	/**
	 * Loads the model from the given ResourceLocation using the
	 * {@link ModelRegistry} thus this constructor is exception-free and will
	 * load the models from the Registry's chache if possible. You can give/load
	 * your own model with {@link #ModelAnimated(ModelMHMD)} to receive
	 * exceptions.
	 *
	 * @param resLoc
	 *            the {@link ResourceLocation} to load the model from
	 */
	public ModelAnimated(ResourceLocation resLoc) {
		this(ModelRegistry.loadFrom(resLoc));
	}
	/**
	 * This constructor just puts the model into itself. Nothing is checked
	 *
	 * @param model
	 *            the model to render
	 */
	public ModelAnimated(ModelMHMD model) {
		this.model = model;
	}
	/**
	 * Renders the underlying model.
	 */
	@Override
	public void render(Entity entity, float uLimbSwing,
			float interpolatedSwing, float uRotfloat, float headYaw,
			float interpolatedPitch, float size) {
		if (!(entity instanceof IAnimatedObject))
			throw new IllegalArgumentException(String.format(
					"Entity rendered must be an IAnimatedObject. EntityId %d",
					entity.getEntityId()));
		glPushMatrix();

		// Get our object into place
		glTranslatef(0, 0.0078125F, 0);
		glScalef(-size * 16, -size * 16, size * 16);
		glTranslatef(0, -1.5F, 0);
		// Actually render it
		IAnimatedObject animatedEntity = (IAnimatedObject) entity;
		this.model.render(animatedEntity, this.getPartialTick());

		glPopMatrix();
	}

	@Override
	public final ModelRenderer getRandomModelBox(Random r) {
		return this.getArrowModelBox(r);
	}

	// Will not use this method
	@Override
	public TextureOffset getTextureOffset(String boxName) {
		return new TextureOffset(0, 0);
	}
	/**
	 * This should return the box in which a specific arrow is being stuck in.
	 * Use the given random for RNG so that arrows stay in place (over multiple
	 * render calls).
	 *
	 * @param r
	 *            a {@link Random} to use for RNG
	 * @return a {@link ModelRenderer} to place arrows on. Not null
	 */
	protected ModelRenderer getArrowModelBox(Random r) {
		// FIXME For some reason we cannot return null here
		return null;
	}
}
