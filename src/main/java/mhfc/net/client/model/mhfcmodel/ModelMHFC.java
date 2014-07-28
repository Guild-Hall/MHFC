package mhfc.net.client.model.mhfcmodel;

import java.util.Random;

import mhfc.net.client.model.PartTickModelBase;
import mhfc.net.client.model.mhfcmodel.data.IRawData;
import mhfc.net.client.model.mhfcmodel.glcontext.GLHelper;
import mhfc.net.client.model.mhfcmodel.glcontext.GLHelper20;
import mhfc.net.client.model.mhfcmodel.glcontext.GLHelper40;
import mhfc.net.client.model.mhfcmodel.loader.VersionizedModelLoader;
import mhfc.net.common.entity.type.IMHFCAnimatedEntity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;

import org.lwjgl.opengl.GLContext;
/**
 *
 * @author WorldSEnder
 *
 */
public class ModelMHFC extends PartTickModelBase implements IModelCustom {
	protected static final boolean supportsPipelines = GLContext
			.getCapabilities().GL_ARB_separate_shader_objects;

	private static GLHelper getCorrectGLHelper() {
		if (supportsPipelines)
			return new GLHelper40();
		return new GLHelper20();
	}

	private GLHelper renderHelper;

	/**
	 * Creates a new ModelMHFC.
	 *
	 * @param resource
	 *            - The resource to load from. Refer to the wiki on
	 *            https://github.com/Heltrato/MHFC/wiki/API-overview to get
	 *            detail about the modelformat
	 * @throws ModelFormatException
	 *             - if the resource given cannot be read from or the file is
	 *             malformed
	 */
	public ModelMHFC(ResourceLocation resource) throws ModelFormatException {
		IRawData amd = VersionizedModelLoader.loadVersionized(resource);
		this.renderHelper = getCorrectGLHelper();
		renderHelper.loadInto(amd);
	}

	@Override
	public void render(Entity entity, float uLimbSwing,
			float interpolatedSwing, float uRotfloat, float headYaw,
			float interpolatedPitch, float size) {
		if (!(entity instanceof IMHFCAnimatedEntity))
			throw new IllegalArgumentException(
					String.format(
							"Entity rendered must be an IMHFCAnimatedEntity. EntityId %d",
							entity.getEntityId()));
		IMHFCAnimatedEntity animatedEntity = (IMHFCAnimatedEntity) entity;
		renderHelper.render(animatedEntity, this.getPartialTick());
	}

	@Override
	public ModelRenderer getRandomModelBox(Random r) {
		// FIXME For some reason we cannot return null here
		return null;
	}

	// Will not use this method
	@Override
	public TextureOffset getTextureOffset(String boxName) {
		return new TextureOffset(0, 0);
	}

	@Override
	public String getType() {
		return "MHFC";
	}

	// ---------------------- Never used ---------------------------
	@Override
	public void renderAll() {
	}
	@Override
	public void renderOnly(String... groupNames) {
	}
	@Override
	public void renderPart(String partName) {
	}
	@Override
	public void renderAllExcept(String... excludedGroupNames) {
	}
}
